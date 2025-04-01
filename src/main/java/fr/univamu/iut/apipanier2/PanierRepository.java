package fr.univamu.iut.apipanier2;

import java.util.List;

import fr.univamu.iut.apipanier2.models.LignePanier;
import fr.univamu.iut.apipanier2.models.Panier;

/**
 * Interface unifiée pour l'accès aux données des paniers
 */
public interface PanierRepository {
    /**
     * Ferme la connexion à la base de données
     */
    void close();
    
    // Méthodes de compatibilité avec l'ancienne API
    Panier getPanierByReference(String reference);
    List<Panier> getAllPaniers();
    boolean updatePanierStatus(String reference, String client, String articles, char status);
    
    // Méthodes de base pour la gestion des paniers
    Panier getPanierById(int id);
    List<LignePanier> getLignesByPanierId(int panierId);
    int createPanier(Panier panier);
    boolean updatePanier(Panier panier);
    boolean deletePanier(int id);
    
    // Méthodes pour la gestion des lignes
    int addLignePanier(LignePanier ligne);
    boolean updateLignePanier(LignePanier ligne);
    boolean deleteLignePanier(int id);
    
    // Méthodes spécifiques
    boolean validatePanier(int id);
}
