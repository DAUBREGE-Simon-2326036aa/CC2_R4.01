package fr.univamu.iut.apipanier2;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class PanierApplication extends Application {

    // Configuration de la base de données
    private static final String DB_URL = "jdbc:mariadb://mysql-coopagricole.alwaysdata.net/coopagricole_bd";
    private static final String DB_USER = "406588_panier";
    private static final String DB_PASSWORD = "PanierA1";

    /**
     * Méthode appelée par l'API CDI pour injecter la connexion à la base de données
     * @return un objet implémentant PanierRepositoryInterface
     */
    @Produces
    @ApplicationScoped
    public PanierRepository openDbConnection() {
        try {
            System.out.println("Creating PanierRepository instance...");
            return new PanierRepositoryMariadb(DB_URL, DB_USER, DB_PASSWORD);
        }
        catch (Exception e) {
            System.err.println("Fatal error creating PanierRepository: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create database connection: " + e.getMessage(), e);
        }
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données
     * @param repository la connexion à la base de données
     */
    public void closeDbConnection(@Disposes PanierRepository repository) {
        if (repository != null) {
            System.out.println("Closing PanierRepository connection...");
            repository.close();
        }
    }
    
    // The producer method for ProduitService has been removed to avoid ambiguous dependencies
    // Since ProduitService is already annotated with @ApplicationScoped, CDI can manage it directly
}

