package fr.univamu.iut.produitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

@Path("/products")
@ApplicationScoped
public class ProduitResource {

    private ProduitService service;

    public ProduitResource() {}

    @Inject
    public ProduitResource(ProduitsEtUtilisateursRepositoryInterface repo ){
        this.service = new ProduitService(repo) ;
    }

    @GET
    @Produces("application/json")
    public String getAllProduits() {
        return service.getAllProduitsJSON();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getProduitById(@PathParam("id") int id) {
        return service.getProduitByIdJSON(id);
    }

    @PUT
    @Path("/{id}/update")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean updateProduit(@PathParam("id") int id, Produit produit) {
        return service.updateProduit(id, produit);
    }

    @DELETE
    @Path("/{id}/delete")
    @Produces("application/json")
    public boolean deleteProduit(@PathParam("id") int id) {
        return service.deleteProduit(id);
    }

    @POST
    @Path("/create")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean createProduit(Produit produit) {
        return service.createProduit(produit.getNom(), produit.getPrix(), produit.getPrixCategorie(), produit.getTypeProduit());
    }

}
