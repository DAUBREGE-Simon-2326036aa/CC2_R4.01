package fr.univamu.iut.apipanier2;

import fr.univamu.iut.apipanier2.models.LignePanier;
import fr.univamu.iut.apipanier2.models.Panier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Ressource associée aux paniers
 * (point d'accès de l'API REST)
 */
@Path("/paniers")
@ApplicationScoped
public class PanierResource {

    /**
     * Service utilisé pour accéder aux données des paniers
     */
    @Inject
    private PanierService service;

    /**
     * Constructeur par défaut requis pour CDI
     */
    public PanierResource() {
        System.out.println("PanierResource default constructor called");
    }

    // Méthodes de compatibilité avec l'ancienne API

    /**
     * Endpoint permettant de publier tous les paniers enregistrés
     */
    @GET
    @Produces("application/json")
    public Response getAllPaniers() {
        if (service == null) {
            System.err.println("ERROR: service is null in getAllPaniers!");
            return Response.serverError().entity("Service unavailable").build();
        }
        
        String result = service.getAllPaniersJSON();
        return Response.ok(result).build();
    }

    /**
     * Endpoint permettant de publier les informations d'un panier par référence
     */
    @GET
    @Path("{reference}")
    @Produces("application/json")
    public String getPanier(@PathParam("reference") String reference) {
        String result = service.getPanierJSON(reference);

        if (result == null)
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de mettre à jour le statut d'un panier uniquement
     */
    @PUT
    @Path("{reference}")
    @Consumes("application/json")
    public Response updatePanier(@PathParam("reference") String reference, Panier panier) {
        if (!service.updatePanier(reference, panier))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    // Méthodes pour la version 2 de l'API (unifiées)

    /**
     * Endpoint permettant de récupérer tous les paniers avec leurs lignes
     */
    @GET
    @Path("/complet")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPaniersWithLines() {
        String json = service.getAllPaniersWithLinesJSON();
        if (json != null) {
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint permettant de récupérer un panier par son ID
     */
    @GET
    @Path("/id/{id}")
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
    @Path("/id/{id}/lignes")
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
    @Path("/create")
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
    @Path("/id/{id}/lignes")
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
    @Path("/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePanierById(@PathParam("id") int id, Panier panier) {
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
    @Path("/id/{panierId}/lignes/{ligneId}")
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
    @Path("/id/{id}")
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
    @Path("/id/{id}/validate")
    public Response validatePanier(@PathParam("id") int id) {
        if (service.validatePanier(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}