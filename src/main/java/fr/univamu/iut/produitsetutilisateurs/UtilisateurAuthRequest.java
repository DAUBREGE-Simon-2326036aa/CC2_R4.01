package fr.univamu.iut.produitsetutilisateurs;

public class UtilisateurAuthRequest {
    private String nom;
    private String password;

    public UtilisateurAuthRequest() {}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}