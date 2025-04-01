package fr.univamu.iut.produitsetutilisateurs;

/**
 * Classe représentant une requête d'authentification d'utilisateur.
 * Contient les informations nécessaires pour l'authentification.
 */
public class UtilisateurAuthRequest {
    private String nom;
    private String password;

    /**
     * Constructeur par défaut de UtilisateurAuthRequest.
     */
    public UtilisateurAuthRequest() {}

    /**
     * Récupère le nom d'utilisateur pour l'authentification.
     *
     * @return le nom d'utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom d'utilisateur pour l'authentification.
     *
     * @param nom le nom d'utilisateur à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Récupère le mot de passe pour l'authentification.
     *
     * @return le mot de passe
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe pour l'authentification.
     *
     * @param password le mot de passe à définir
     */
    public void setPassword(String password) {
        this.password = password;
    }
}