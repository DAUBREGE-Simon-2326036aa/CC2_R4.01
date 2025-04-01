package fr.univamu.iut.produitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

/**
 * Ressource REST pour la gestion des produits.
 * <p>
 * Fournit des endpoints CRUD pour les opérations sur les produits :
 * création, lecture, mise à jour et suppression.
 * Toutes les réponses sont au format JSON.
 * </p>
 */
@Path("/products")
@ApplicationScoped
public class ProduitResource {

    private ProduitService service;

    /**
     * Constructeur par défaut nécessaire pour CDI.
     */
    public ProduitResource() {}

    /**
     * Constructeur avec injection de dépendance pour le repository.
     *
     * @param repo l'interface du repository à injecter pour l'accès aux données
     */
    @Inject
    public ProduitResource(ProduitsEtUtilisateursRepositoryInterface repo) {
        this.service = new ProduitService(repo);
    }

    /**
     * Récupère la liste de tous les produits.
     *
     * @return une représentation JSON de tous les produits
     */
    @GET
    @Produces("application/json")
    public String getAllProduits() {
        return service.getAllProduitsJSON();
    }

    /**
     * Récupère un produit spécifique par son identifiant.
     *
     * @param id l'identifiant du produit à récupérer
     * @return une représentation JSON du produit demandé
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getProduitById(@PathParam("id") int id) {
        return service.getProduitByIdJSON(id);
    }

    /**
     * Met à jour les informations d'un produit existant.
     *
     * @param id l'identifiant du produit à mettre à jour
     * @param produit l'objet produit contenant les nouvelles informations
     * @return true si la mise à jour a réussi, false sinon
     */
    @PUT
    @Path("/{id}/update")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean updateProduit(@PathParam("id") int id, Produit produit) {
        return service.updateProduit(id, produit);
    }

    /**
     * Supprime un produit existant.
     *
     * @param id l'identifiant du produit à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @DELETE
    @Path("/{id}/delete")
    @Produces("application/json")
    public boolean deleteProduit(@PathParam("id") int id) {
        return service.deleteProduit(id);
    }

    /**
     * Crée un nouveau produit.
     *
     * @param produit l'objet produit contenant les informations du nouveau produit
     * @return true si la création a réussi, false sinon
     */
    @POST
    @Path("/create")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean createProduit(Produit produit) {
        return service.createProduit(produit.getNom(), produit.getPrix(), produit.getPrixCategorie(), produit.getTypeProduit());
    }
}