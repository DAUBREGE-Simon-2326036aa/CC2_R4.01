package fr.univamu.iut.produitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class ProduitsEtUtilisateursApplication extends Application {

    @Produces
    private UtilisateurRepositoryInterface openDbConnection(){
        ProduitsEtUtilisateursRepositoryMariadb db = null;

        try{
            db = new ProduitsEtUtilisateursRepositoryMariadb("jdbc:mariadb://mysql-r401-lacombe.alwaysdata.net/r401-lacombe_produits_et_utilisateurs_db", "395420_user", "root@69");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes UtilisateurRepositoryInterface userRepo) {
        userRepo.close();
    }
}