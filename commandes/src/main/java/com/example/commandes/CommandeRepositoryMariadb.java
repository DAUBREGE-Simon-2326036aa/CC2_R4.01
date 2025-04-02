package com.example.commandes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CommandeRepositoryMariadb implements CommandeRepositoryInterface {

    private Connection dbConnection;

    public CommandeRepositoryMariadb(String infoConnection, String user, String pwd)
            throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
    public void close() {
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Commande getCommandeById(int id) {
        Commande commande = null;
        String query = "SELECT * FROM commandes WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setTailleCommande(rs.getInt("taille_commande"));
                commande.setIdClient(rs.getInt("id_client"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }

    @Override
    public ArrayList<Commande> getAllCommande() {
        ArrayList<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commandes";
        try (Statement stmt = dbConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setTailleCommande(rs.getInt("taille_commande"));
                commande.setIdClient(rs.getInt("id_client"));
                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }

    @Override
    public boolean CreateCommande(int id) {
        String query = "INSERT INTO commandes (id) VALUES (?)";
        try (PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean UpdateCommande(int id) {
        String query = "UPDATE commandes SET taille_commande = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            stmt.setInt(1, 100); // Example value, consider parameterizing this properly
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteCommande(int id) {
        String query = "DELETE FROM commandes WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

