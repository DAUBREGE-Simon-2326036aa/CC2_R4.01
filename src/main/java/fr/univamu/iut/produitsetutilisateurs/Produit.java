package fr.univamu.iut.produitsetutilisateurs;

public class Produit {

    private int id;
    private String nom;
    private float prixKilo;
    private String typeProduit;

    public Produit(int id, String nom, float prixKilo, String typeProduit) {
        this.id = id;
        this.nom = nom;
        this.prixKilo = prixKilo;
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

    public float getPrixKilo() {
        return prixKilo;
    }

    public void setPrixKilo(float prixKilo) {
        this.prixKilo = prixKilo;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }
}
