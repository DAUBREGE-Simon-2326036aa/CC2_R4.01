package fr.univamu.iut.apipanier2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.univamu.iut.apipanier2.models.LignePanier;

/**
 * Classe représentant un panier d'achats
 */
public class Panier {

    /**
     * Identifiant du panier
     */
    protected int id;

    /**
     * Référence du panier (pour la compatibilité avec l'ancien modèle)
     */
    protected String reference;

    /**
     * Client propriétaire du panier
     */
    protected String client;

    /**
     * Articles du panier (pour la compatibilité avec l'ancien modèle)
     */
    protected String articles;

    /**
     * Nom du panier
     */
    protected String nom;
    
    /**
     * Description du panier
     */
    protected String description;
    
    /**
     * Identifiant du gestionnaire qui a créé le panier
     */
    protected int gestionnaireCreationId;
    
    /**
     * Prix total du panier
     */
    protected BigDecimal prixTotal;
    
    /**
     * Statut du panier
     * ('v' pour vide, 'e' pour en cours, et 'f' pour finalisé)
     */
    protected char status;
    
    /**
     * Indicateur si le panier est validé
     */
    protected boolean estValide;
    
    /**
     * Date de dernière modification
     */
    protected LocalDateTime derniereMaj;
    
    /**
     * Liste des lignes de panier
     */
    protected List<LignePanier> lignes;

    /**
     * Constructeur par défaut
     */
    public Panier() {
        this.lignes = new ArrayList<>();
        this.prixTotal = BigDecimal.ZERO;
        this.status = 'v';
    }

    /**
     * Constructeur de panier (compatibilité avec l'ancien modèle)
     * @param reference référence du panier
     * @param client client propriétaire du panier
     * @param articles articles du panier
     */
    public Panier(String reference, String client, String articles) {
        this.reference = reference;
        this.client = client;
        this.articles = articles;
        this.status = 'v';
        this.lignes = new ArrayList<>();
        this.prixTotal = BigDecimal.ZERO;
    }

    /**
     * Constructeur avec paramètres principaux pour le nouveau modèle
     * @param id Identifiant du panier
     * @param nom Nom du panier
     * @param description Description du panier
     * @param gestionnaireCreationId Identifiant du gestionnaire
     */
    public Panier(int id, String nom, String description, int gestionnaireCreationId) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.gestionnaireCreationId = gestionnaireCreationId;
        this.prixTotal = BigDecimal.ZERO;
        this.estValide = false;
        this.derniereMaj = LocalDateTime.now();
        this.lignes = new ArrayList<>();
        this.status = 'v';
    }

    /**
     * Constructeur complet pour le nouveau modèle
     */
    public Panier(int id, String nom, String description, int gestionnaireCreationId, 
                    BigDecimal prixTotal, boolean estValide, LocalDateTime derniereMaj) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.gestionnaireCreationId = gestionnaireCreationId;
        this.prixTotal = prixTotal;
        this.estValide = estValide;
        this.derniereMaj = derniereMaj;
        this.lignes = new ArrayList<>();
        this.status = estValide ? 'f' : 'e';
    }

    // Méthodes de l'ancien modèle
    
    /**
     * Méthode permettant d'accéder à la réference du panier
     * @return un chaîne de caractères avec la référence du panier
     */
    public String getReference() {
        return reference;
    }

    /**
     * Méthode permettant d'accéder au client du panier
     * @return un chaîne de caractères avec le client du panier
     */
    public String getClient() {
        return client;
    }

    /**
     * Méthode permettant d'accéder aux articles du panier
     * @return un chaîne de caractères avec la liste des articles
     */
    public String getArticles() {
        return articles;
    }

    /**
     * Méthode permettant d'accéder au statut du panier
     * @return un caractère indiquant le statut du panier ('v' pour vide, 'e' pour en cours, et 'f' pour finalisé)
     */
    public char getStatus() {
        return status;
    }

    /**
     * Méthode permettant de modifier la référence du panier
     * @param reference une chaîne de caractères avec la référence à utiliser
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Méthode permettant de modifier le client du panier
     * @param client une chaîne de caractères avec le client à utiliser
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * Méthode permettant de modifier les articles du panier
     * @param articles une chaîne de caractères avec la liste des articles
     */
    public void setArticles(String articles) {
        this.articles = articles;
    }

    /**
     * Méthode permettant de modifier le statut du panier
     * @param status le caractère 'v' pour vide, 'e' pour en cours, ou 'f' pour finalisé
     */
    public void setStatus(char status) {
        this.status = status;
        this.estValide = (status == 'f');
    }
    
    // Méthodes du nouveau modèle
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGestionnaireCreationId() {
        return gestionnaireCreationId;
    }

    public void setGestionnaireCreationId(int gestionnaireCreationId) {
        this.gestionnaireCreationId = gestionnaireCreationId;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public boolean isEstValide() {
        return estValide;
    }

    public void setEstValide(boolean estValide) {
        this.estValide = estValide;
        this.status = estValide ? 'f' : 'e';
    }

    public LocalDateTime getDerniereMaj() {
        return derniereMaj;
    }

    public void setDerniereMaj(LocalDateTime derniereMaj) {
        this.derniereMaj = derniereMaj;
    }

    public List<LignePanier> getLignes() {
        return lignes;
    }

    public void setLignes(List<LignePanier> lignes) {
        this.lignes = lignes;
        
        // Recalcule le prix total à partir des lignes
        this.prixTotal = BigDecimal.ZERO;
        for (LignePanier ligne : lignes) {
            if (ligne.getSousTotal() != null) {
                this.prixTotal = this.prixTotal.add(ligne.getSousTotal());
            }
        }
    }
    
    /**
     * Ajoute une ligne au panier et met à jour le prix total
     * @param ligne Ligne à ajouter
     */
    public void ajouterLigne(LignePanier ligne) {
        this.lignes.add(ligne);
        if (ligne.getSousTotal() != null) {
            this.prixTotal = this.prixTotal.add(ligne.getSousTotal());
        }
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", client='" + client + '\'' +
                ", articles='" + articles + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", gestionnaireCreationId=" + gestionnaireCreationId +
                ", prixTotal=" + prixTotal +
                ", status=" + status +
                ", estValide=" + estValide +
                ", derniereMaj=" + derniereMaj +
                ", lignes=" + lignes +
                '}';
    }
}
