package fr.univamu.iut.produitsetutilisateurs;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implémentation concrète du repository pour MariaDB gérant la persistance des données
 * pour les utilisateurs et produits.
 * <p>
 * Cette classe fournit une implémentation complète de l'interface {@link ProduitsEtUtilisateursRepositoryInterface}
 * en utilisant une connexion JDBC à une base de données MariaDB. Elle gère toutes les opérations CRUD
 * (Create, Read, Update, Delete) pour les entités Utilisateur et Produit.
 * </p>
 *
 * <h3>Fonctionnalités principales :</h3>
 * <ul>
 *   <li>Gestion du cycle de vie des connexions à la base de données</li>
 *   <li>Opérations CRUD complètes pour les utilisateurs</li>
 *   <li>Opérations CRUD complètes pour les produits</li>
 *   <li>Système d'authentification des utilisateurs</li>
 * </ul>
 *
 * <h3>Exemple d'utilisation :</h3>
 * <pre>
 * try (ProduitsEtUtilisateursRepositoryMariadb repo =
 *         new ProduitsEtUtilisateursRepositoryMariadb(url, user, password)) {
 *     // Utilisation du repository
 *     List&lt;Utilisateur&gt; users = repo.getAllUtilisateurs();
 * } catch (Exception e) {
 *     // Gestion des erreurs
 * }
 * </pre>
 *
 * @see ProduitsEtUtilisateursRepositoryInterface
 * @see java.sql.DriverManager
 * @see java.sql.PreparedStatement
 */
public class ProduitsEtUtilisateursRepositoryMariadb implements ProduitsEtUtilisateursRepositoryInterface, Closeable {

    /**
     * Connexion JDBC à la base de données MariaDB.
     * <p>
     * Cette connexion est établie lors de la construction de l'objet et doit être fermée
     * en appelant la méthode {@link #close()} ou via try-with-resources.
     * </p>
     */
    protected Connection dbConnection;

    /**
     * Constructeur établissant la connexion à la base de données MariaDB.
     *
     * @param infoConnection URL de connexion JDBC (format: "jdbc:mariadb://host:port/database")
     * @param user Nom d'utilisateur pour l'authentification
     * @param pwd Mot de passe pour l'authentification
     * @throws SQLException si la connexion à la base de données échoue
     * @throws ClassNotFoundException si le driver JDBC MariaDB n'est pas trouvé
     * @see DriverManager#getConnection(String, String, String)
     */
    public ProduitsEtUtilisateursRepositoryMariadb(String infoConnection, String user, String pwd)
            throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    /**
     * Ferme proprement la connexion à la base de données.
     * <p>
     * Cette méthode est appelée automatiquement lorsque l'objet est utilisé dans un bloc
     * try-with-resources grâce à l'implémentation de {@link Closeable}.
     * </p>
     *
     * @implNote Les erreurs éventuelles sont loggées sur {@link System#err} mais ne sont pas propagées
     */
    @Override
    public void close() {
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch(SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
        }
    }

    /* Méthodes pour la gestion des utilisateurs */

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de l'exécution de la requête
     * @implNote Utilise un {@link PreparedStatement} avec paramètre pour éviter les injections SQL
     */
    @Override
    public Utilisateur getUtilisateurById(int id) {
        Utilisateur selectedUtilisateur = null;
        String query = "SELECT * FROM Utilisateur WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                selectedUtilisateur = new Utilisateur(
                        id,
                        result.getString("nom"),
                        result.getString("password"),
                        result.getString("email")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur " + id, e);
        }

        return selectedUtilisateur;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de l'exécution de la requête
     */
    @Override
    public ArrayList<Utilisateur> getAllUtilisateurs() {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM Utilisateur";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                utilisateurs.add(new Utilisateur(
                        result.getInt("id"),
                        result.getString("nom"),
                        result.getString("password"),
                        result.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs", e);
        }

        return utilisateurs;
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @param nom {@inheritDoc}
     * @param password {@inheritDoc}
     * @param email {@inheritDoc}
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de la mise à jour
     */
    @Override
    public boolean updateUtilisateur(int id, String nom, String password, String email) {
        String query = "UPDATE Utilisateur SET nom=?, password=?, email=? WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setInt(4, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de la suppression
     */
    @Override
    public boolean deleteUtilisateur(int id) {
        String query = "DELETE FROM Utilisateur WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param nom {@inheritDoc}
     * @param password {@inheritDoc}
     * @param email {@inheritDoc}
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de la création
     */
    @Override
    public boolean createUtilisateur(String nom, String password, String email) {
        String query = "INSERT INTO Utilisateur (nom, password, email) VALUES (?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, password);
            ps.setString(3, email);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création de l'utilisateur", e);
        }
    }

    /* Méthodes pour la gestion des produits */

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de la récupération
     * @throws IllegalArgumentException si la catégorie de prix n'est pas valide
     */
    @Override
    public Produit getProduitById(int id) {
        Produit selectedProduit = null;
        String query = "SELECT * FROM Produit WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                selectedProduit = new Produit(
                        id,
                        result.getString("nom"),
                        result.getFloat("prix"),
                        PrixCategorie.valueOf(result.getString("prixCategorie")),
                        result.getString("typeProduit")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du produit " + id, e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Catégorie de prix invalide en base de données", e);
        }

        return selectedProduit;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de la récupération
     * @throws IllegalArgumentException si une catégorie de prix n'est pas valide
     */
    @Override
    public ArrayList<Produit> getAllProduits() {
        ArrayList<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM Produit";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                produits.add(new Produit(
                        result.getInt("id"),
                        result.getString("nom"),
                        result.getFloat("prix"),
                        PrixCategorie.valueOf(result.getString("prixCategorie")),
                        result.getString("typeProduit")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des produits", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Catégorie de prix invalide en base de données", e);
        }

        return produits;
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @param nom {@inheritDoc}
     * @param prix {@inheritDoc}
     * @param prixCategorie {@inheritDoc}
     * @param typeProduit {@inheritDoc}
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de la mise à jour
     */
    @Override
    public boolean updateProduit(int id, String nom, float prix, PrixCategorie prixCategorie, String typeProduit) {
        String query = "UPDATE Produit SET nom=?, prix=?, prixCategorie=?, typeProduit=? WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setFloat(2, prix);
            ps.setString(3, prixCategorie.toString());
            ps.setString(4, typeProduit);
            ps.setInt(5, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du produit " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de la suppression
     */
    @Override
    public boolean deleteProduit(int id) {
        String query = "DELETE FROM Produit WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du produit " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param nom {@inheritDoc}
     * @param prix {@inheritDoc}
     * @param prixCategorie {@inheritDoc}
     * @param typeProduit {@inheritDoc}
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de la création
     */
    @Override
    public boolean createProduit(String nom, float prix, PrixCategorie prixCategorie, String typeProduit) {
        String query = "INSERT INTO Produit (nom, prix, prixCategorie, typeProduit) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setFloat(2, prix);
            ps.setString(3, prixCategorie.toString());
            ps.setString(4, typeProduit);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création du produit", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param nom {@inheritDoc}
     * @param password {@inheritDoc}
     * @return {@inheritDoc}
     * @throws RuntimeException si une erreur SQL survient lors de l'authentification
     * @implNote En production, le mot de passe devrait être stocké de manière sécurisée (hash + salt)
     */
    @Override
    public int authentification(String nom, String password) {
        String query = "SELECT id FROM Utilisateur WHERE nom=? AND password=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, password);

            ResultSet result = ps.executeQuery();
            return result.next() ? result.getInt("id") : -1;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'authentification", e);
        }
    }
}