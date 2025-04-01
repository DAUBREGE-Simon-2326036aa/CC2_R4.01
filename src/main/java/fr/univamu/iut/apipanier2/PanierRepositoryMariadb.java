package fr.univamu.iut.apipanier2;

import java.io.Closeable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.univamu.iut.apipanier2.models.LignePanier;
import fr.univamu.iut.apipanier2.models.Panier;

/**
 * Implémentation MariaDB de l'interface PanierRepository
 */
public class PanierRepositoryMariadb implements PanierRepository, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     * @param infoConnection URL de connexion à la base de données
     * @param user Identifiant de connexion
     * @param pwd Mot de passe
     */
    public PanierRepositoryMariadb(String infoConnection, String user, String pwd) 
            throws SQLException, ClassNotFoundException {
        
        if (infoConnection == null || infoConnection.isEmpty()) {
            throw new IllegalArgumentException("Connection string cannot be null or empty");
        }
        if (user == null || user.isEmpty()) {
            throw new IllegalArgumentException("Database user cannot be null or empty");
        }
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            dbConnection = DriverManager.getConnection(infoConnection + "?connectTimeout=5000", user, pwd);
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MariaDB JDBC driver: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void close() {
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch(SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    @Override
    public Panier getPanierByReference(String reference) {
        Panier selectedPanier = null;
        int id;

        try {
            id = Integer.parseInt(reference);
        } catch (NumberFormatException e) {
            System.err.println("Reference is not numeric: " + reference);
            return null;
        }

        selectedPanier = getPanierById(id);
        
        if (selectedPanier != null) {
            selectedPanier.setReference(reference);
            
            // Compatibilité avec l'ancienne API
            if (selectedPanier.getClient() == null) {
                selectedPanier.setClient("Client " + id);
            }
            
            if (selectedPanier.getArticles() == null) {
                StringBuilder articlesBuilder = new StringBuilder();
                if (selectedPanier.getLignes() != null && !selectedPanier.getLignes().isEmpty()) {
                    for (LignePanier ligne : selectedPanier.getLignes()) {
                        articlesBuilder.append("Produit ").append(ligne.getProduitId())
                                      .append(" (").append(ligne.getQuantite()).append(" x ")
                                      .append(ligne.getPrixUnitaire()).append("), ");
                    }
                    if (articlesBuilder.length() > 2) {
                        articlesBuilder.delete(articlesBuilder.length() - 2, articlesBuilder.length());
                    }
                }
                selectedPanier.setArticles(articlesBuilder.toString());
            }
        }
        return selectedPanier;
    }

    @Override
    public List<Panier> getAllPaniers() {
        List<Panier> listPaniers = new ArrayList<>();
        String query = "SELECT * FROM paniers";

        try (PreparedStatement ps = dbConnection.prepareStatement(query);
             ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                Panier panier = extractPanierFromResultSet(result);
                
                // Compatibilité avec l'ancienne API
                String reference = String.valueOf(panier.getId());
                panier.setReference(reference);
                panier.setClient("Client " + panier.getGestionnaireCreationId());
                panier.setStatus(panier.isEstValide() ? 'f' : 'e');
                
                // Chargement des lignes associées
                List<LignePanier> lignes = getLignesByPanierId(panier.getId());
                panier.setLignes(lignes);
                
                // Génération du descriptif des articles pour compatibilité
                StringBuilder articlesBuilder = new StringBuilder();
                if (lignes != null && !lignes.isEmpty()) {
                    for (LignePanier ligne : lignes) {
                        articlesBuilder.append("Produit ").append(ligne.getProduitId())
                                      .append(" (").append(ligne.getQuantite()).append(" x ")
                                      .append(ligne.getPrixUnitaire()).append("), ");
                    }
                    if (articlesBuilder.length() > 2) {
                        articlesBuilder.delete(articlesBuilder.length() - 2, articlesBuilder.length());
                    }
                }
                panier.setArticles(articlesBuilder.toString());
                
                listPaniers.add(panier);
            }
        } catch (SQLException e) {
            System.err.println("Error in getAllPaniers: " + e.getMessage());
            throw new RuntimeException("Database error when retrieving all baskets", e);
        }
        
        return listPaniers;
    }

    @Override
    public boolean updatePanierStatus(String reference, String client, String articles, char status) {
        int id;

        try {
            id = Integer.parseInt(reference);
        } catch (NumberFormatException e) {
            System.err.println("Reference is not numeric: " + reference);
            return false;
        }
        
        Panier existingPanier = getPanierById(id);
        if (existingPanier == null) {
            return false;
        }
        
        existingPanier.setClient(client);
        existingPanier.setArticles(articles);
        existingPanier.setStatus(status);
        existingPanier.setEstValide(status == 'f');
        
        return updatePanier(existingPanier);
    }

    @Override
    public Panier getPanierById(int id) {
        Panier panier = null;
        String query = "SELECT * FROM paniers WHERE id = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    panier = extractPanierFromResultSet(result);
                    panier.setLignes(getLignesByPanierId(id));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in getPanierById: " + e.getMessage());
            throw new RuntimeException("Database error when retrieving basket by ID", e);
        }

        return panier;
    }

    @Override
    public List<LignePanier> getLignesByPanierId(int panierId) {
        List<LignePanier> lignes = new ArrayList<>();
        String query = "SELECT * FROM lignes_panier WHERE panier_id = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, panierId);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    LignePanier ligne = extractLignePanierFromResultSet(result);
                    lignes.add(ligne);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting basket lines: " + e.getMessage());
            throw new RuntimeException("Database error when retrieving basket lines", e);
        }

        return lignes;
    }

    @Override
    public int createPanier(Panier panier) {
        String query = "INSERT INTO paniers (nom, description, gestionnaire_crea_id, prix_total, est_valide) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, panier.getNom());
            ps.setString(2, panier.getDescription());
            ps.setInt(3, panier.getGestionnaireCreationId());
            ps.setBigDecimal(4, panier.getPrixTotal() != null ? panier.getPrixTotal() : BigDecimal.ZERO);
            ps.setBoolean(5, panier.isEstValide());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                    
                    // Ajout des lignes de panier si présentes
                    if (panier.getLignes() != null && !panier.getLignes().isEmpty()) {
                        for (LignePanier ligne : panier.getLignes()) {
                            ligne.setPanierId(generatedId);
                            addLignePanier(ligne);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating basket: " + e.getMessage());
            throw new RuntimeException("Database error when creating basket", e);
        }

        return generatedId;
    }

    @Override
    public int addLignePanier(LignePanier ligne) {
        String query = "INSERT INTO lignes_panier (panier_id, produit_id, quantite, prix_unitaire) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ligne.getPanierId());
            ps.setInt(2, ligne.getProduitId());
            ps.setBigDecimal(3, ligne.getQuantite());
            ps.setBigDecimal(4, ligne.getPrixUnitaire());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                    updatePanierPriceAfterLineChange(ligne.getPanierId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding basket line: " + e.getMessage());
            throw new RuntimeException("Database error when adding basket line", e);
        }

        return generatedId;
    }

    @Override
    public boolean updatePanier(Panier panier) {
        String query = "UPDATE paniers SET nom = ?, description = ?, gestionnaire_crea_id = ?, prix_total = ?, est_valide = ? WHERE id = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, panier.getNom());
            ps.setString(2, panier.getDescription());
            ps.setInt(3, panier.getGestionnaireCreationId());
            ps.setBigDecimal(4, panier.getPrixTotal() != null ? panier.getPrixTotal() : BigDecimal.ZERO);
            ps.setBoolean(5, panier.isEstValide());
            ps.setInt(6, panier.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating basket: " + e.getMessage());
            throw new RuntimeException("Database error when updating basket", e);
        }
    }

    @Override
    public boolean updateLignePanier(LignePanier ligne) {
        String query = "UPDATE lignes_panier SET produit_id = ?, quantite = ?, prix_unitaire = ? WHERE id = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, ligne.getProduitId());
            ps.setBigDecimal(2, ligne.getQuantite());
            ps.setBigDecimal(3, ligne.getPrixUnitaire());
            ps.setInt(4, ligne.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updatePanierPriceAfterLineChange(ligne.getPanierId());
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error updating basket line: " + e.getMessage());
            throw new RuntimeException("Database error when updating basket line", e);
        }
    }

    @Override
    public boolean deletePanier(int id) {
        String query = "DELETE FROM paniers WHERE id = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting basket: " + e.getMessage());
            throw new RuntimeException("Database error when deleting basket", e);
        }
    }

    @Override
    public boolean deleteLignePanier(int id) {
        // Récupération de l'ID du panier pour mise à jour du prix total
        int panierId = -1;
        try (PreparedStatement ps = dbConnection.prepareStatement("SELECT panier_id FROM lignes_panier WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    panierId = rs.getInt("panier_id");
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error when querying basket line", e);
        }
        
        // Suppression de la ligne
        String query = "DELETE FROM lignes_panier WHERE id = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0 && panierId > 0) {
                updatePanierPriceAfterLineChange(panierId);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error deleting basket line: " + e.getMessage());
            throw new RuntimeException("Database error when deleting basket line", e);
        }
    }
    
    @Override
    public boolean validatePanier(int id) {
        String query = "UPDATE paniers SET est_valide = true WHERE id = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error validating basket: " + e.getMessage());
            throw new RuntimeException("Database error when validating basket", e);
        }
    }
    
    /**
     * Extrait un objet Panier à partir d'un ResultSet
     */
    private Panier extractPanierFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        String description = rs.getString("description");
        int gestionnaireCreationId = rs.getInt("gestionnaire_crea_id");
        BigDecimal prixTotal = rs.getBigDecimal("prix_total");
        boolean estValide = rs.getBoolean("est_valide");
        Timestamp derniereMajTs = rs.getTimestamp("date_derniere_modification");
        LocalDateTime derniereMaj = derniereMajTs != null ? derniereMajTs.toLocalDateTime() : LocalDateTime.now();

        return new Panier(id, nom, description, gestionnaireCreationId, prixTotal, estValide, derniereMaj);
    }

    /**
     * Extrait un objet LignePanier à partir d'un ResultSet
     */
    private LignePanier extractLignePanierFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int panierId = rs.getInt("panier_id");
        int produitId = rs.getInt("produit_id");
        BigDecimal quantite = rs.getBigDecimal("quantite");
        BigDecimal prixUnitaire = rs.getBigDecimal("prix_unitaire");

        return new LignePanier(id, panierId, produitId, quantite, prixUnitaire);
    }
    
    /**
     * Met à jour le prix total d'un panier après modification d'une ligne
     */
    private void updatePanierPriceAfterLineChange(int panierId) {
        // Calcul du total à partir des lignes
        String calcQuery = "SELECT COALESCE(SUM(quantite * prix_unitaire), 0) as total FROM lignes_panier WHERE panier_id = ?";
        try (PreparedStatement calcPs = dbConnection.prepareStatement(calcQuery)) {
            calcPs.setInt(1, panierId);
            try (ResultSet calcRs = calcPs.executeQuery()) {
                if (calcRs.next()) {
                    BigDecimal total = calcRs.getBigDecimal("total");
                    
                    // Mise à jour du prix total du panier
                    String updateQuery = "UPDATE paniers SET prix_total = ? WHERE id = ?";
                    try (PreparedStatement updatePs = dbConnection.prepareStatement(updateQuery)) {
                        updatePs.setBigDecimal(1, total);
                        updatePs.setInt(2, panierId);
                        updatePs.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error updating basket price: " + e.getMessage());
            throw new RuntimeException("Database error when updating basket price", e);
        }
    }
}
