package fr.univamu.iut.apipanier2;

import java.util.ArrayList;
import java.util.List;

import fr.univamu.iut.apipanier2.models.LignePanier;

/**
 * Interface d'accès aux données des paniers
 */
public interface PanierRepositoryInterface {

    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les paniers
     */
    public void close();

    // Méthodes de l'ancien modèle
    
    /**
     * Méthode retournant le panier dont la référence est passée en paramètre
     * @param reference identifiant du panier recherché
     * @return un objet Panier représentant le panier recherché
     */
    public Panier getPanier(String reference);

    /**
     * Méthode retournant la liste des paniers
     * @return une liste d'objets paniers
     */
    public ArrayList<Panier> getAllPaniers();

    /**
     * Méthode permettant de mettre à jours un panier enregistré
     * @param reference identifiant du panier à mettre à jours
     * @param client nouveau client du panier
     * @param articles nouvelle liste d'articles
     * @param status nouveau status du panier
     * @return true si le panier existe et la mise à jours a été faite, false sinon
     */
    public boolean updatePanier(String reference, String client, String articles, char status);
    
    // Méthodes pour le nouveau modèle
    
    /**
     * Méthode retournant le panier dont l'identifiant est passé en paramètre
     * @param id identifiant du panier recherché
     * @return un objet Panier représentant le panier recherché
     */
    public Panier getPanierById(int id);

    /**
     * Méthode retournant les lignes d'un panier
     * @param panierId identifiant du panier
     * @return liste des lignes du panier
     */
    public List<LignePanier> getLignesByPanierId(int panierId);

    /**
     * Méthode permettant de créer un nouveau panier
     * @param panier le panier à créer
     * @return l'identifiant du panier créé, -1 en cas d'erreur
     */
    public int createPanier(Panier panier);

    /**
     * Méthode permettant d'ajouter une ligne à un panier
     * @param ligne la ligne à ajouter
     * @return l'identifiant de la ligne créée, -1 en cas d'erreur
     */
    public int addLignePanier(LignePanier ligne);

    /**
     * Méthode permettant de mettre à jour un panier
     * @param panier le panier avec ses nouvelles informations
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updatePanier(Panier panier);

    /**
     * Méthode permettant de mettre à jour une ligne de panier
     * @param ligne la ligne avec ses nouvelles informations
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateLignePanier(LignePanier ligne);

    /**
     * Méthode permettant de supprimer un panier
     * @param id identifiant du panier à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deletePanier(int id);

    /**
     * Méthode permettant de supprimer une ligne de panier
     * @param id identifiant de la ligne à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteLignePanier(int id);
    
    /**
     * Méthode permettant de valider un panier
     * @param id identifiant du panier à valider
     * @return true si la validation a réussi, false sinon
     */
    public boolean validatePanier(int id);
}

