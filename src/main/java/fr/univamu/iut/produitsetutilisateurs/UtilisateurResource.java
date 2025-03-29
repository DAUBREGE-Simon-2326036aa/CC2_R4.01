package fr.univamu.iut.produitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

@Path("/users")
@ApplicationScoped
public class UtilisateurResource {

    private UtilisateurService service;

    public UtilisateurResource() {}

    @Inject
    public UtilisateurResource(ProduitsEtUtilisateursRepositoryInterface repo ){
        this.service = new UtilisateurService(repo) ;
    }

    public UtilisateurResource(UtilisateurService service) {
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllUtilisateurs() {
        return service.getAllUtilisateursJSON();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getUtilisateurById(@PathParam("id") int id) {
        return service.getUtilisateurByIdJSON(id);
    }

    @PUT
    @Path("/{id}/update")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean updateUtilisateur(@PathParam("id") int id, Utilisateur utilisateur) {
        return service.updateUtilisateur(id, utilisateur);
    }

    @DELETE
    @Path("/{id}/delete")
    @Produces("application/json")
    public boolean deleteUtilisateur(@PathParam("id") int id) {
        return service.deleteUtilisateur(id);
    }

    @POST
    @Path("/auth")
    @Consumes("application/json")
    @Produces("application/json")
    public String authentification(UtilisateurAuthRequest utilisateurAuthRequest) {
        return service.authentification(utilisateurAuthRequest.getNom(), utilisateurAuthRequest.getPassword());
    }
}
