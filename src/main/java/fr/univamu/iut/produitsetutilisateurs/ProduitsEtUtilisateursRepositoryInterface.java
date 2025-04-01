package fr.univamu.iut.produitsetutilisateurs;

import java.util.ArrayList;

/**
 * Interface définissant le contrat d'accès aux données pour les produits et utilisateurs.
 * <p>
 * Cette interface spécifie toutes les opérations CRUD (Create, Read, Update, Delete)
 * pour la gestion des utilisateurs et des produits dans le système.
 * </p>
 */
public interface ProduitsEtUtilisateursRepositoryInterface {

    /**
     * Ferme les ressources utilisées par le repository (connexion DB, etc.).
     */
    public void close();

    /* Méthodes pour la gestion des utilisateurs */

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à rechercher
     * @return l'utilisateur correspondant, ou null si non trouvé
     */
    public Utilisateur getUtilisateurById(int id);

    /**
     * Récupère la liste complète des utilisateurs.
     *
     * @return une ArrayList contenant tous les utilisateurs
     */
    public ArrayList<Utilisateur> getAllUtilisateurs();

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur à mettre à jour
     * @param nom le nouveau nom de l'utilisateur
     * @param password le nouveau mot de passe
     * @param email le nouvel email
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateUtilisateur(int id, String nom, String password, String email);

    /**
     * Supprime un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteUtilisateur(int id);

    /**
     * Crée un nouvel utilisateur.
     *
     * @param nom le nom du nouvel utilisateur
     * @param password le mot de passe
     * @param email l'email
     * @return true si la création a réussi, false sinon
     */
    public boolean createUtilisateur(String nom, String password, String email);

    /* Méthodes pour la gestion des produits */

    /**
     * Récupère un produit par son identifiant.
     *
     * @param id l'identifiant du produit à rechercher
     * @return le produit correspondant, ou null si non trouvé
     */
    public Produit getProduitById(int id);

    /**
     * Récupère la liste complète des produits.
     *
     * @return une ArrayList contenant tous les produits
     */
    public ArrayList<Produit> getAllProduits();

    /**
     * Met à jour les informations d'un produit.
     *
     * @param id l'identifiant du produit à mettre à jour
     * @param nom le nouveau nom du produit
     * @param prix le nouveau prix
     * @param prixCategorie la nouvelle catégorie de prix
     * @param typeProduit le nouveau type de produit
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateProduit(int id, String nom, float prix,
                                 PrixCategorie prixCategorie, String typeProduit);

    /**
     * Supprime un produit.
     *
     * @param id l'identifiant du produit à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteProduit(int id);

    /**
     * Crée un nouveau produit.
     *
     * @param nom le nom du produit
     * @param prix le prix du produit
     * @param prixCategorie la catégorie de prix
     * @param typeProduit le type de produit
     * @return true si la création a réussi, false sinon
     */
    public boolean createProduit(String nom, float prix,
                                 PrixCategorie prixCategorie, String typeProduit);

    /**
     * Authentifie un utilisateur.
     *
     * @param nom le nom de l'utilisateur
     * @param password le mot de passe
     * @return l'identifiant de l'utilisateur si authentification réussie, -1 sinon
     */
    public int authentification(String nom, String password);
}