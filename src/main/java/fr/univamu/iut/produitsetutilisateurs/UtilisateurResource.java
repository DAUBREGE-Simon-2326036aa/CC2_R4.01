package fr.univamu.iut.produitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/users")
@ApplicationScoped
public class UtilisateurResource {

    private UtilisateurService service;

    public UtilisateurResource() {}

    public @Inject UtilisateurResource(UtilisateurRepositoryInterface userRepo ){
        this.service = new UtilisateurService(userRepo) ;
    }

    public UtilisateurResource(UtilisateurService service) {
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllUtilisateurs() {
        return service.getAllUtilisateursJSON();
    }


}
