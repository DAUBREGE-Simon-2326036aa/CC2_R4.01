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
        return false;
    }

}
