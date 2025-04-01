package fr.univamu.iut.apipanier2;

import fr.univamu.iut.apipanier2.models.LignePanier;
import fr.univamu.iut.apipanier2.models.Panier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Ressource associée aux paniers détaillés
 * (point d'accès de l'API REST)
 */
@Path("/paniers-detail")
@ApplicationScoped
public class DetailedPanierResource {

    /**
     * Service utilisé pour accéder aux données des paniers
     */
    @Inject
    private PanierService service;

    /**
     * Constructeur par défaut requis pour CDI
     */
    public DetailedPanierResource() {
        System.out.println("DetailedPanierResource default constructor called");
    }

    /**
     * Constructeur pour l'injection de dépendance
     * @param repository Repository pour l'accès aux données
     */
    @Inject
    public DetailedPanierResource(PanierRepository repository) {
        System.out.println("DetailedPanierResource constructor with repository injection called");
        this.service = new PanierService(repository);
    }

    /**
     * Endpoint permettant de récupérer tous les paniers
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPaniers() {
        if (service == null) {
            System.err.println("ERROR: service is null in getAllPaniers!");
            return Response.serverError().entity("Service unavailable").build();
        }
        
        String result = service.getAllPaniersJSON();
        return Response.ok(result).build();
    }

    /**
     * Endpoint permettant de récupérer un panier par son ID
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPanierById(@PathParam("id") int id) {
        String json = service.getPanierByIdJSON(id);
        if (json != null) {
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint permettant de récupérer les lignes d'un panier
     * @param panierId ID du panier
     * @return Liste des lignes au format JSON
     */
    @GET
    @Path("/{id}/lignes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLignesByPanierId(@PathParam("id") int panierId) {
        String json = service.getLignesByPanierIdJSON(panierId);
        if (json != null) {
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint permettant de créer un nouveau panier
     * @param panier Panier à créer
     * @return Réponse avec l'ID du panier créé
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPanier(Panier panier) {
        int id = service.createPanier(panier);
        if (id > 0) {
            return Response.status(Response.Status.CREATED).entity(String.valueOf(id)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint permettant d'ajouter une ligne à un panier
     * @param panierId ID du panier
     * @param ligne Ligne à ajouter
     * @return Réponse avec l'ID de la ligne créée
     */
    @POST
    @Path("/{id}/lignes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addLignePanier(@PathParam("id") int panierId, LignePanier ligne) {
        ligne.setPanierId(panierId);
        int id = service.addLignePanier(ligne);
        if (id > 0) {
            return Response.status(Response.Status.CREATED).entity(String.valueOf(id)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint permettant de mettre à jour un panier
     * @param id ID du panier
     * @param panier Panier avec les nouvelles informations
     * @return Réponse indiquant le résultat de la mise à jour
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePanier(@PathParam("id") int id, Panier panier) {
        if (service.updatePanierWithId(id, panier)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint permettant de mettre à jour une ligne de panier
     * @param panierId ID du panier
     * @param ligneId ID de la ligne
     * @param ligne Ligne avec les nouvelles informations
     * @return Réponse indiquant le résultat de la mise à jour
     */
    @PUT
    @Path("/{panierId}/lignes/{ligneId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLignePanier(
            @PathParam("panierId") int panierId,
            @PathParam("ligneId") int ligneId,
            LignePanier ligne) {
        if (service.updateLignePanier(ligneId, panierId, ligne)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint permettant de supprimer un panier
     * @param id ID du panier à supprimer
     * @return Réponse indiquant le résultat de la suppression
     */
    @DELETE
    @Path("/{id}")
    public Response deletePanier(@PathParam("id") int id) {
        if (service.deletePanier(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint permettant de supprimer une ligne de panier
     * @param ligneId ID de la ligne à supprimer
     * @return Réponse indiquant le résultat de la suppression
     */
    @DELETE
    @Path("/lignes/{ligneId}")
    public Response deleteLignePanier(@PathParam("ligneId") int ligneId) {
        if (service.deleteLignePanier(ligneId)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint permettant de valider un panier
     * @param id ID du panier à valider
     * @return Réponse indiquant le résultat de la validation
     */
    @PUT
    @Path("/{id}/validate")
    public Response validatePanier(@PathParam("id") int id) {
        if (service.validatePanier(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}