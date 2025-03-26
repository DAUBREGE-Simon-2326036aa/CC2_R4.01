package fr.univamu.iut.produitsetutilisateurs;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class ProduitsEtUtilisateursRepositoryMariadb implements UtilisateurRepositoryInterface, Closeable {

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
        return null;
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
                String email = result.getString("email");
                String password = result.getString("password");

                Utilisateur utilisateur = new Utilisateur(id, nom, email, password);
                utilisateurs.add(utilisateur);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return utilisateurs;
    }

    @Override
    public boolean updateUtilisateur(int id, String nom, String password, String email) {
        return false;
    }

    @Override
    public boolean deleteUtilisateur(int id) {
        return false;
    }

}
