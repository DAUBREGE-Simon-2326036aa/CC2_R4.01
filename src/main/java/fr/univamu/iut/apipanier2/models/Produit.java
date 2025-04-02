package fr.univamu.iut.apipanier2.models;

import java.math.BigDecimal;

/**
 * Classe représentant un produit du catalogue
 */
public class Produit {

    /**
     * Identifiant du produit
     */
    private int id;

    /**
     * Nom du produit
     */
    private String nom;

    /**
     * Prix du produit
     */
    private BigDecimal prix;

    /**
     * Catégorie de prix du produit
     * (A_L_UNITE, AU_KILO, A_LA_DOUZAINE)
     */
    private String prixCategorie;

    /**
     * Type du produit
     * (LAITERIE, OEUFS, FRUIT, LEGUME, EPICERIE, BOISSON, BOULANGERIE)
     */
    private String typeProduit;

    /**
     * Constructeur par défaut
     */
    public Produit() {
    }

    /**
     * Constructeur avec tous les paramètres
     */
    public Produit(int id, String nom, BigDecimal prix, String prixCategorie, String typeProduit) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixCategorie = prixCategorie;
        this.typeProduit = typeProduit;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public String getPrixCategorie() {
        return prixCategorie;
    }

    public void setPrixCategorie(String prixCategorie) {
        this.prixCategorie = prixCategorie;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", prixCategorie='" + prixCategorie + '\'' +
                ", typeProduit='" + typeProduit + '\'' +
                '}';
    }
}
