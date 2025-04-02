package fr.univamu.iut.apipanier2;

import java.util.List;

import fr.univamu.iut.apipanier2.models.Produit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Ressource pour exposer les produits via l'API REST
 */
@Path("/produits")
@ApplicationScoped
public class ProduitResource {

    /**
     * Service de gestion des produits
     */
    @Inject
    private ProduitService produitService;
    
    /**
     * Constructeur par défaut
     */
    public ProduitResource() {
        System.out.println("ProduitResource default constructor called");
    }
    
    /**
     * Constructeur avec injection du service
     */
    @Inject
    public ProduitResource(ProduitService produitService) {
        System.out.println("ProduitResource constructor with service injection called");
        this.produitService = produitService;
    }
    
    /**
     * Renvoie tous les produits
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProduits() {
        if (produitService == null) {
            System.err.println("ERROR: produitService is null in getAllProduits!");
            return Response.serverError().entity("Service unavailable").build();
        }
        
        List<Produit> produits = produitService.getAllProduits();
        return Response.ok(produitService.convertToJson(produits)).build();
    }
    
    /**
     * Renvoie un produit par son ID
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduitById(@PathParam("id") int id) {
        Produit produit = produitService.getProduitById(id);
        
        if (produit != null) {
            return Response.ok(produitService.convertToJson(produit)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    /**
     * Renvoie les produits par type
     */
    @GET
    @Path("/type/{typeProduit}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduitsByType(@PathParam("typeProduit") String typeProduit) {
        List<Produit> produits = produitService.getProduitsByType(typeProduit);
        return Response.ok(produitService.convertToJson(produits)).build();
    }
    
    /**
     * Recherche de produits avec plusieurs critères
     */
    @GET
    @Path("/recherche")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rechercherProduits(
            @QueryParam("type") String type,
            @QueryParam("prixCategorie") String prixCategorie) {
        
        List<Produit> produits = produitService.getAllProduits();
        
        // Filtrage par type si spécifié
        if (type != null && !type.isEmpty()) {
            produits.removeIf(p -> !p.getTypeProduit().equalsIgnoreCase(type));
        }
        
        // Filtrage par catégorie de prix si spécifiée
        if (prixCategorie != null && !prixCategorie.isEmpty()) {
            produits.removeIf(p -> !p.getPrixCategorie().equalsIgnoreCase(prixCategorie));
        }
        
        return Response.ok(produitService.convertToJson(produits)).build();
    }
}
