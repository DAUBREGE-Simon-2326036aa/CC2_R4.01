package fr.univamu.iut.apipanier2;

import java.util.List;

import fr.univamu.iut.apipanier2.models.LignePanier;
import fr.univamu.iut.apipanier2.models.Panier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

/**
 * Service pour la gestion des paniers
 */
@ApplicationScoped
public class PanierService {

    /**
     * Repository pour l'accès aux données
     */
    protected PanierRepository repository;

    /**
     * Constructeur par défaut pour CDI
     */
    public PanierService() {
    }
    
    /**
     * Constructeur avec injection du repository
     * @param repository Repository pour l'accès aux données
     */
    @Inject
    public PanierService(PanierRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    // Méthodes de compatibilité avec l'ancienne API

    /**
     * Récupère tous les paniers au format JSON (compatibilité ancienne API)
     */
    public String getAllPaniersJSON() {
        List<Panier> paniers = repository.getAllPaniers();
        return convertToJson(paniers);
    }

    /**
     * Récupère un panier par sa référence au format JSON (compatibilité ancienne API)
     */
    public String getPanierJSON(String reference) {
        Panier panier = repository.getPanierByReference(reference);
        return convertToJson(panier);
    }

    /**
     * Met à jour un panier (compatibilité ancienne API)
     */
    public boolean updatePanier(String reference, Panier panier) {
        return repository.updatePanierStatus(reference, panier.getClient(), panier.getArticles(), panier.getStatus());
    }

    // Méthodes communes

    /**
     * Récupère tous les paniers avec leurs lignes au format JSON
     */
    public String getAllPaniersWithLinesJSON() {
        List<Panier> paniers = repository.getAllPaniers();
        return convertToJson(paniers);
    }

    /**
     * Récupère un panier par son ID au format JSON
     */
    public String getPanierByIdJSON(int id) {
        Panier panier = repository.getPanierById(id);
        return convertToJson(panier);
    }

    /**
     * Récupère les lignes d'un panier au format JSON
     */
    public String getLignesByPanierIdJSON(int panierId) {
        List<LignePanier> lignes = repository.getLignesByPanierId(panierId);
        return convertToJson(lignes);
    }

    /**
     * Récupère les lignes d'un panier
     * @param panierId ID du panier
     * @return Liste des lignes du panier
     */
    public List<LignePanier> getLignesByPanierId(int panierId) {
        return repository.getLignesByPanierId(panierId);
    }

    /**
     * Crée un nouveau panier
     */
    public int createPanier(Panier panier) {
        return repository.createPanier(panier);
    }

    /**
     * Ajoute une ligne à un panier
     */
    public int addLignePanier(LignePanier ligne) {
        return repository.addLignePanier(ligne);
    }

    /**
     * Met à jour un panier
     */
    public boolean updatePanierWithId(int id, Panier panier) {
        panier.setId(id);
        return repository.updatePanier(panier);
    }

    /**
     * Met à jour une ligne de panier
     */
    public boolean updateLignePanier(int ligneId, int panierId, LignePanier ligne) {
        ligne.setId(ligneId);
        ligne.setPanierId(panierId);
        return repository.updateLignePanier(ligne);
    }

    /**
     * Supprime un panier
     */
    public boolean deletePanier(int id) {
        return repository.deletePanier(id);
    }

    /**
     * Supprime une ligne de panier
     */
    public boolean deleteLignePanier(int id) {
        return repository.deleteLignePanier(id);
    }

    /**
     * Valide un panier
     */
    public boolean validatePanier(int id) {
        return repository.validatePanier(id);
    }

    /**
     * Convertit un objet en JSON
     */
    private String convertToJson(Object object) {
        if (object == null) {
            return null;
        }

        String result = null;
        // Utilisation de try-with-resources pour assurer la fermeture du JsonbBuilder
        try (Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true))) {
            result = jsonb.toJson(object);
        } catch (Exception e) {
            System.err.println("Error during JSON serialization: " + e.getMessage());
        }
        return result;
    }
}
