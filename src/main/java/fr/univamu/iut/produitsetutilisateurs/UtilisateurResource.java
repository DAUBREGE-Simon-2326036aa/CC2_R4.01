package fr.univamu.iut.produitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

/**
 * Ressource REST pour la gestion des utilisateurs.
 * Fournit des endpoints pour les opérations CRUD sur les utilisateurs,
 * ainsi que pour l'authentification.
 */
@Path("/users")
@ApplicationScoped
public class UtilisateurResource {

    private UtilisateurService service;

    /**
     * Constructeur par défaut.
     */
    public UtilisateurResource() {}

    /**
     * Constructeur avec injection de dépendance pour le repository.
     *
     * @param repo l'interface du repository à injecter
     */
    @Inject
    public UtilisateurResource(ProduitsEtUtilisateursRepositoryInterface repo) {
        this.service = new UtilisateurService(repo);
    }

    /**
     * Constructeur avec service utilisateur.
     *
     * @param service le service utilisateur à utiliser
     */
    public UtilisateurResource(UtilisateurService service) {
        this.service = service;
    }

    /**
     * Récupère tous les utilisateurs au format JSON.
     *
     * @return une chaîne JSON représentant tous les utilisateurs
     */
    @GET
    @Produces("application/json")
    public String getAllUtilisateurs() {
        return service.getAllUtilisateursJSON();
    }

    /**
     * Récupère un utilisateur spécifique par son ID au format JSON.
     *
     * @param id l'identifiant de l'utilisateur
     * @return une chaîne JSON représentant l'utilisateur demandé
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getUtilisateurById(@PathParam("id") int id) {
        return service.getUtilisateurByIdJSON(id);
    }

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur à mettre à jour
     * @param utilisateur les nouvelles informations de l'utilisateur
     * @return true si la mise à jour a réussi, false sinon
     */
    @PUT
    @Path("/{id}/update")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean updateUtilisateur(@PathParam("id") int id, Utilisateur utilisateur) {
        return service.updateUtilisateur(id, utilisateur);
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @DELETE
    @Path("/{id}/delete")
    @Produces("application/json")
    public boolean deleteUtilisateur(@PathParam("id") int id) {
        return service.deleteUtilisateur(id);
    }

    /**
     * Authentifie un utilisateur.
     *
     * @param utilisateurAuthRequest la requête d'authentification contenant nom et mot de passe
     * @return une chaîne JSON indiquant le résultat de l'authentification
     */
    @POST
    @Path("/auth")
    @Consumes("application/json")
    @Produces("application/json")
    public String authentification(UtilisateurAuthRequest utilisateurAuthRequest) {
        return service.authentification(utilisateurAuthRequest.getNom(), utilisateurAuthRequest.getPassword());
    }

    /**
     * Crée un nouvel utilisateur.
     *
     * @param utilisateur l'utilisateur à créer
     * @return true si la création a réussi, false sinon
     */
    @POST
    @Path("/create")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean createUtilisateur(Utilisateur utilisateur) {
        return service.createUtilisateur(utilisateur);
    }
}