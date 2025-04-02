package fr.univamu.iut.apipanier2;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.univamu.iut.apipanier2.models.Produit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Service pour accéder à l'API des produits
 */
@ApplicationScoped
public class ProduitService {

    /**
     * URL de l'API des produits
     */
    private static final String PRODUCTS_API_URL = "http://localhost:9170/ProduitsEtUtilisateurs-1.0-SNAPSHOT/api/products";
    
    /**
     * Délai de rafraîchissement du cache en minutes
     */
    private static final int CACHE_REFRESH_MINUTES = 15;
    
    /**
     * Cache des produits par ID
     */
    private final Map<Integer, Produit> produitsCache = new ConcurrentHashMap<>();
    
    /**
     * Client HTTP pour les requêtes REST
     */
    private final Client client;
    
    /**
     * Constructeur par défaut
     */
    public ProduitService() {
        client = ClientBuilder.newClient();
        
        // Initialiser le cache
        refreshCache();
        
        // Configurer le rafraîchissement périodique du cache
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::refreshCache, CACHE_REFRESH_MINUTES, CACHE_REFRESH_MINUTES, TimeUnit.MINUTES);
    }
    
    /**
     * Récupère tous les produits 
     * @return Liste de tous les produits
     */
    public List<Produit> getAllProduits() {
        if (produitsCache.isEmpty()) {
            refreshCache();
        }
        return new ArrayList<>(produitsCache.values());
    }
    
    /**
     * Récupère un produit par son ID
     * @param id ID du produit
     * @return Le produit ou null si non trouvé
     */
    public Produit getProduitById(int id) {
        if (produitsCache.isEmpty()) {
            refreshCache();
        }
        return produitsCache.get(id);
    }
    
    /**
     * Récupère les produits par type
     * @param type Type de produit
     * @return Liste des produits du type spécifié
     */
    public List<Produit> getProduitsByType(String type) {
        if (produitsCache.isEmpty()) {
            refreshCache();
        }
        
        List<Produit> result = new ArrayList<>();
        for (Produit produit : produitsCache.values()) {
            if (produit.getTypeProduit().equalsIgnoreCase(type)) {
                result.add(produit);
            }
        }
        return result;
    }
    
    /**
     * Rafraîchit le cache des produits
     */
    private void refreshCache() {
        try {
            Response response = client.target(PRODUCTS_API_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            
            if (response.getStatus() == 200) {
                String jsonResponse = response.readEntity(String.class);
                List<Produit> produits = parseProductsJson(jsonResponse);
                
                // Mettre à jour le cache
                produitsCache.clear();
                for (Produit produit : produits) {
                    produitsCache.put(produit.getId(), produit);
                }
                
                System.out.println("Cache des produits rafraîchi avec succès: " + produitsCache.size() + " produits.");
            } else {
                System.err.println("Erreur lors de la récupération des produits: " + response.getStatus());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des produits: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Parse la réponse JSON de l'API des produits
     * @param jsonString Réponse JSON
     * @return Liste des produits
     */
    private List<Produit> parseProductsJson(String jsonString) {
        List<Produit> produits = new ArrayList<>();
        
        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            JsonArray jsonArray = reader.readArray();
            
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.getJsonObject(i);
                
                int id = jsonObject.getInt("id");
                String nom = jsonObject.getString("nom", "").replace("\"", "");
                
                // Gestion des valeurs numériques potentiellement mal formatées
                String prixStr = jsonObject.get("prix").toString().replace("\"", "");
                BigDecimal prix;
                try {
                    prix = new BigDecimal(prixStr);
                } catch (NumberFormatException e) {
                    prix = BigDecimal.ZERO;
                }
                
                String prixCategorie = jsonObject.getString("prixCategorie", "").replace("\"", "");
                String typeProduit = jsonObject.getString("typeProduit", "").replace("\"", "");
                
                Produit produit = new Produit(id, nom, prix, prixCategorie, typeProduit);
                produits.add(produit);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du parsing JSON des produits: " + e.getMessage());
            e.printStackTrace();
        }
        
        return produits;
    }
    
    /**
     * Convertit un objet en JSON
     */
    public String convertToJson(Object object) {
        if (object == null) {
            return null;
        }

        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(object);
        } catch (Exception e) {
            System.err.println("Erreur lors de la sérialisation JSON: " + e.getMessage());
        }
        return result;
    }
}
