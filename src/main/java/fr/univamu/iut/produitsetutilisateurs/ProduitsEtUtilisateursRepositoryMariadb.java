package fr.univamu.iut.produitsetutilisateurs;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class ProduitsEtUtilisateursRepositoryMariadb implements ProduitsEtUtilisateursRepositoryInterface, Closeable {

    protected Connection dbConnection;

    public ProduitsEtUtilisateursRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Utilisateur getUtilisateurById(int id) {
        Utilisateur selectedUtilisateur = null;
        String query = "SELECT * FROM Utilisateur WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                String nom = result.getString("nom");
                String password = result.getString("password");
                String email = result.getString("email");

                selectedUtilisateur = new Utilisateur(id, nom, password, email);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectedUtilisateur;
    }

    @Override
    public ArrayList<Utilisateur> getAllUtilisateurs() {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM Utilisateur";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String nom = result.getString("nom");
                String password = result.getString("password");
                String email = result.getString("email");

                Utilisateur utilisateur = new Utilisateur(id, nom, password, email);
                utilisateurs.add(utilisateur);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return utilisateurs;
    }

    @Override
    public boolean updateUtilisateur(int id, String nom, String password, String email) {
        String query = "UPDATE Utilisateur SET nom=?, password=?, email=? WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setInt(4, id);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteUtilisateur(int id) {
        String query = "DELETE FROM Utilisateur WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Produit getProduitById(int id) {
        Produit selectedProduit = null;
        String query = "SELECT * FROM Produit WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                String nom = result.getString("nom");
                float prix = result.getFloat("prix");
                PrixCategorie prixCategorie = PrixCategorie.valueOf(result.getString("prixCategorie"));
                String typeProduit = result.getString("typeProduit");

                selectedProduit = new Produit(id, nom, prix, prixCategorie, typeProduit);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectedProduit;
    }

    @Override
    public ArrayList<Produit> getAllProduits() {
        ArrayList<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM Produit";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String nom = result.getString("nom");
                float prix = result.getFloat("prix");
                PrixCategorie prixCategorie = PrixCategorie.valueOf(result.getString("prixCategorie"));
                String typeProduit = result.getString("typeProduit");

                Produit produit = new Produit(id, nom, prix, prixCategorie, typeProduit);
                produits.add(produit);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return produits;
    }

    @Override
    public boolean updateProduit(int id, String nom, float prix, PrixCategorie prixCategorie, String typeProduit) {
        String query = "UPDATE Produit SET nom=?, prix=?, prixCategorie=?, typeProduit=? WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setFloat(2, prix);
            ps.setString(3, prixCategorie.toString());
            ps.setString(4, typeProduit);
            ps.setInt(5, id);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteProduit(int id) {
        String query = "DELETE FROM Produit WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int authentification(String nom, String password) {
        int id = -1;
        String query = "SELECT id FROM Utilisateur WHERE nom=? AND password=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, password);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                id = result.getInt("id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    @Override
    public boolean createUtilisateur(String nom, String password, String email) {
        String query = "INSERT INTO Utilisateur (nom, password, email) VALUES (?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, password);
            ps.setString(3, email);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean createProduit(String nom, float prix, PrixCategorie prixCategorie, String typeProduit) {
        String query = "INSERT INTO Produit (nom, prix, prixCategorie, typeProduit) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setFloat(2, prix);
            ps.setString(3, prixCategorie.toString());
            ps.setString(4, typeProduit);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
