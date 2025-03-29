package fr.univamu.iut.produitsetutilisateurs;

public class Produit {

    protected int id;
    protected String nom;
    protected float prix;
    protected PrixCategorie prixCategorie;
    protected String typeProduit;

    public Produit() {}

    public Produit(int id, String nom, float prixKilo, PrixCategorie prixCategorie, String typeProduit) {
        this.id = id;
        this.nom = nom;
        this.prix = prixKilo;
        this.prixCategorie = prixCategorie;
        this.typeProduit = typeProduit;
    }

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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public PrixCategorie getPrixCategorie() {
        return prixCategorie;
    }

    public void setPrixCategorie(PrixCategorie prixCategorie) {
        this.prixCategorie = prixCategorie;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }
}
