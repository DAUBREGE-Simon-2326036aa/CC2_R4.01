package fr.univamu.iut.produitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * Ressource REST simple pour tester le bon fonctionnement de l'API.
 * <p>
 * Fournit un endpoint basique qui retourne un message "Hello, World!".
 * Cette classe est principalement utilisée pour vérifier que :
 * - L'application est correctement déployée
 * - Les routes REST sont bien configurées
 * - Le serveur répond aux requêtes HTTP
 * </p>
 */
@Path("/hello-world")
@ApplicationScoped
public class HelloResource {

    /**
     * Endpoint GET de test qui retourne un message simple.
     *
     * @return la chaîne "Hello, World!" en texte brut
     * @apiNote Méthode utilisée pour les tests de connectivité de base
     * @example <pre>GET /hello-world → "Hello, World!"</pre>
     */
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}