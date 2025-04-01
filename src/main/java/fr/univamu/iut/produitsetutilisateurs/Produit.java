package fr.univamu.iut.produitsetutilisateurs;

/**
 * Classe représentant un produit dans le système.
 */
public class Produit {

    protected int id;
    protected String nom;
    protected float prix;
    protected PrixCategorie prixCategorie;
    protected String typeProduit;

    /**
     * Constructeur par défaut de la classe Produit.
     */
    public Produit() {}

    /**
     * Constructeur avec paramètres de la classe Produit.
     *
     * @param id l'identifiant du produit
     * @param nom le nom du produit
     * @param prixKilo le prix au kilo du produit
     * @param prixCategorie la catégorie de prix du produit
     * @param typeProduit le type de produit
     */
    public Produit(int id, String nom, float prixKilo, PrixCategorie prixCategorie, String typeProduit) {
        this.id = id;
        this.nom = nom;
        this.prix = prixKilo;
        this.prixCategorie = prixCategorie;
        this.typeProduit = typeProduit;
    }

    /**
     * Retourne l'identifiant du produit.
     *
     * @return l'identifiant du produit
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du produit.
     *
     * @param id le nouvel identifiant du produit
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom du produit.
     *
     * @return le nom du produit
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom du produit.
     *
     * @param nom le nouveau nom du produit
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le prix du produit.
     *
     * @return le prix du produit
     */
    public float getPrix() {
        return prix;
    }

    /**
     * Modifie le prix du produit.
     *
     * @param prix le nouveau prix du produit
     */
    public void setPrix(float prix) {
        this.prix = prix;
    }

    /**
     * Retourne la catégorie de prix du produit.
     *
     * @return la catégorie de prix du produit
     */
    public PrixCategorie getPrixCategorie() {
        return prixCategorie;
    }

    /**
     * Modifie la catégorie de prix du produit.
     *
     * @param prixCategorie la nouvelle catégorie de prix du produit
     */
    public void setPrixCategorie(PrixCategorie prixCategorie) {
        this.prixCategorie = prixCategorie;
    }

    /**
     * Retourne le type de produit.
     *
     * @return le type de produit
     */
    public String getTypeProduit() {
        return typeProduit;
    }

    /**
     * Modifie le type de produit.
     *
     * @param typeProduit le nouveau type de produit
     */
    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }
}