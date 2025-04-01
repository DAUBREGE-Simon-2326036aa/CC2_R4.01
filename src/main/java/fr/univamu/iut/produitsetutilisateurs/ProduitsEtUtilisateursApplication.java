package fr.univamu.iut.produitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Classe principale de configuration de l'application JAX-RS.
 * <p>
 * Cette classe sert de point d'entrée pour l'application REST et configure :
 * - Le chemin de base ("/api") pour tous les endpoints
 * - La gestion du cycle de vie de la connexion à la base de données
 * </p>
 *
 * @implNote Utilise CDI pour la gestion des dépendances et le cycle de vie
 */
@ApplicationPath("/api")
@ApplicationScoped
public class ProduitsEtUtilisateursApplication extends Application {

    /**
     * Crée et retourne une nouvelle connexion à la base de données MariaDB.
     * <p>
     * Cette méthode est annotée avec {@code @Produces} pour permettre à CDI
     * d'injecter la connexion là où elle est nécessaire.
     * </p>
     *
     * @return une instance de {@link ProduitsEtUtilisateursRepositoryMariadb} connectée à la base
     * @throws RuntimeException si la connexion à la base de données échoue
     * @see ProduitsEtUtilisateursRepositoryMariadb
     */
    @Produces
    public ProduitsEtUtilisateursRepositoryInterface openDbConnection() {
        ProduitsEtUtilisateursRepositoryMariadb db = null;

        try {
            db = new ProduitsEtUtilisateursRepositoryMariadb(
                    "jdbc:mariadb://mysql-r401-lacombe.alwaysdata.net/r401-lacombe_produits_et_utilisateurs_db",
                    "395420_user",
                    "root@69");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Erreur lors de l'ouverture de la connexion à la base de données", e);
        }
        return db;
    }

    /**
     * Ferme proprement la connexion à la base de données.
     * <p>
     * Cette méthode est appelée automatiquement par CDI lorsque le contexte est détruit.
     * Elle est annotée avec {@code @Disposes} pour indiquer qu'elle gère la destruction
     * de la ressource produite par {@code openDbConnection()}.
     * </p>
     *
     * @param userRepo l'instance du repository à fermer
     */
    public void closeDbConnection(@Disposes ProduitsEtUtilisateursRepositoryInterface userRepo) {
        userRepo.close();
    }
}