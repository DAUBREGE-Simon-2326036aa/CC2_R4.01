package fr.univamu.iut.produitsetutilisateurs;

public class Utilisateur {

    private int id;
    private String nom;
    private String password;
    private String email;

    public Utilisateur(int id, String nom, String password, String email) {
        this.nom = nom;
        this.password = password;
        this.email = email;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
