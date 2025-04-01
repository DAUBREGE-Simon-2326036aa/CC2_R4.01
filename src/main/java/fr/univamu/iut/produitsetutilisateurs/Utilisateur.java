package fr.univamu.iut.produitsetutilisateurs;

/**
 * Classe représentant un utilisateur dans le système.
 */
public class Utilisateur {

    private int id;
    private String nom;
    private String password;
    private String email;

    /**
     * Constructeur par défaut de la classe Utilisateur.
     */
    public Utilisateur() {}

    /**
     * Constructeur avec paramètres de la classe Utilisateur.
     *
     * @param id l'identifiant de l'utilisateur
     * @param nom le nom de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @param email l'email de l'utilisateur
     */
    public Utilisateur(int id, String nom, String password, String email) {
        this.id = id;
        this.nom = nom;
        this.password = password;
        this.email = email;
    }

    /**
     * Retourne le nom de l'utilisateur.
     *
     * @return le nom de l'utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de l'utilisateur.
     *
     * @param nom le nouveau nom de l'utilisateur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le mot de passe de l'utilisateur.
     *
     * @return le mot de passe de l'utilisateur
     */
    public String getPassword() {
        return password;
    }

    /**
     * Modifie le mot de passe de l'utilisateur.
     *
     * @param password le nouveau mot de passe de l'utilisateur
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retourne l'email de l'utilisateur.
     *
     * @return l'email de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifie l'email de l'utilisateur.
     *
     * @param email le nouvel email de l'utilisateur
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne l'identifiant de l'utilisateur.
     *
     * @return l'identifiant de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de l'utilisateur.
     *
     * @param id le nouvel identifiant de l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }
}