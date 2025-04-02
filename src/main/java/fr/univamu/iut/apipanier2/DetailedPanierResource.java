package fr.univamu.iut.apipanier2;

import java.util.ArrayList;
import java.util.List;

import fr.univamu.iut.apipanier2.models.LignePanier;
import fr.univamu.iut.apipanier2.models.LignePanierDetailed;
import fr.univamu.iut.apipanier2.models.Panier;
import fr.univamu.iut.apipanier2.models.Produit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
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
     * Service utilisé pour accéder aux données des produits
     */
    @Inject
    private ProduitService produitService;

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
    public DetailedPanierResource(PanierRepository repository, ProduitService produitService) {
        System.out.println("DetailedPanierResource constructor with repository injection called");
        this.service = new PanierService(repository);
        this.produitService = produitService;
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
     * Endpoint permettant de récupérer les lignes d'un panier avec les détails des produits
     * @param panierId ID du panier
     * @return Liste des lignes détaillées au format JSON
     */
    @GET
    @Path("/{id}/lignes-detaillees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLignesDetailedByPanierId(@PathParam("id") int panierId) {
        List<LignePanier> lignes = service.getLignesByPanierId(panierId);
        
        if (lignes != null) {
            List<LignePanierDetailed> lignesDetaillees = new ArrayList<>();
            
            for (LignePanier ligne : lignes) {
                Produit produit = produitService.getProduitById(ligne.getProduitId());
                LignePanierDetailed ligneDetaillee = LignePanierDetailed.fromLignePanier(ligne, produit);
                lignesDetaillees.add(ligneDetaillee);
            }
            
            return Response.ok(convertToJson(lignesDetaillees)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    /**
     * Endpoint permettant de récupérer un panier avec tous les détails des produits
     * @param id ID du panier
     * @return Panier avec détails au format JSON
     */
    @GET
    @Path("/{id}/complet")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPanierComplet(@PathParam("id") int id) {
        String panierJson = service.getPanierByIdJSON(id);
        
        if (panierJson != null) {
            Panier panier = null;
            try (Jsonb jsonb = JsonbBuilder.create()) {
                panier = jsonb.fromJson(panierJson, Panier.class);
            } catch (Exception e) {
                System.err.println("Error deserializing panier: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            List<LignePanier> lignes = panier.getLignes();
            List<LignePanierDetailed> lignesDetaillees = new ArrayList<>();
            
            if (lignes != null) {
                for (LignePanier ligne : lignes) {
                    Produit produit = produitService.getProduitById(ligne.getProduitId());
                    LignePanierDetailed ligneDetaillee = LignePanierDetailed.fromLignePanier(ligne, produit);
                    lignesDetaillees.add(ligneDetaillee);
                }
                
                // Remplacer les lignes standards par les lignes détaillées
                panier.setLignes(null); // Nécessaire pour éviter des erreurs de casting
            }
            
            // Créer un objet personnalisé pour contenir les données
            PanierComplet panierComplet = new PanierComplet(panier, lignesDetaillees);
            return Response.ok(convertToJson(panierComplet)).build();
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
     * Endpoint permettant d'ajouter un produit directement au panier
     * @param panierId ID du panier
     * @param produitId ID du produit
     * @param requestBody Contenu optionnel avec la quantité
     * @return Réponse avec l'ID de la ligne créée
     */
    @POST
    @Path("/{id}/ajouter-produit/{produitId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response ajouterProduitAuPanier(
            @PathParam("id") int panierId,
            @PathParam("produitId") int produitId,
            AjouterProduitRequest requestBody) {
        
        // Vérifier que le produit existe
        Produit produit = produitService.getProduitById(produitId);
        if (produit == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Produit non trouvé avec l'ID: " + produitId)
                    .build();
        }
        
        // Créer une nouvelle ligne de panier
        LignePanier ligne = new LignePanier();
        ligne.setPanierId(panierId);
        ligne.setProduitId(produitId);
        ligne.setPrixUnitaire(produit.getPrix());
        
        // Utiliser la quantité fournie ou 1 par défaut
        if (requestBody != null && requestBody.getQuantite() != null) {
            ligne.setQuantite(requestBody.getQuantite());
        }
        
        // Ajouter la ligne au panier
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

    /**
     * Convertit un objet en JSON
     */
    private String convertToJson(Object object) {
        if (object == null) {
            return null;
        }

        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true))) {
            result = jsonb.toJson(object);
        } catch (Exception e) {
            System.err.println("Error during JSON serialization: " + e.getMessage());
        }
        return result;
    }
    
    /**
     * Classe interne pour représenter un panier complet avec lignes détaillées
     */
    private static class PanierComplet {
        private Panier panier;
        private List<LignePanierDetailed> lignesDetaillees;
        
        public PanierComplet(Panier panier, List<LignePanierDetailed> lignesDetaillees) {
            this.panier = panier;
            this.lignesDetaillees = lignesDetaillees;
        }
        
        public Panier getPanier() {
            return panier;
        }
        
        public List<LignePanierDetailed> getLignesDetaillees() {
            return lignesDetaillees;
        }
    }
    
    /**
     * Classe pour gérer les requêtes d'ajout de produit
     */
    public static class AjouterProduitRequest {
        private java.math.BigDecimal quantite;
        
        public java.math.BigDecimal getQuantite() {
            return quantite;
        }
        
        public void setQuantite(java.math.BigDecimal quantite) {
            this.quantite = quantite;
        }
    }
}