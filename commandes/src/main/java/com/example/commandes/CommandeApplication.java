package com.example.commandes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped

public class CommandeApplication extends Application {
    @Produces
    public CommandeRepositoryInterface openDbConnexion() {
        CommandeRepositoryMariadb db = null;
        try {
            db = new CommandeRepositoryMariadb(
                    "jdbc:mariadb://mysql-r401-lacombe.alwaysdata.net/archi-logi_commandes",
                    "395439_commandes",
                    "test!123");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Erreur de connexion à la base de donnée: " , e);
        }
        return db;
    }

    public void closeDbConnection(@Disposes CommandeRepositoryInterface userRepo) {
        userRepo.close();
    }

}
