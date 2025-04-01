package fr.univamu.iut.apipanier2.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant un panier d'achats (fusion des versions 1 et 2)
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
     * Constructeur avec paramètres principaux
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
     * Constructeur complet
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public boolean isEstValide() {
        return estValide;
    }

    public void setEstValide(boolean estValide) {
        this.estValide = estValide;
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

    /**
     * Setter pour les lignes du panier, avec recalcul automatique du prix total
     * @param lignes Liste des lignes à associer au panier
     */
    public void setLignes(List<LignePanier> lignes) {
        this.lignes = lignes;
        calculerPrixTotal();
    }

    /**
     * Ajoute une ligne au panier et met à jour le prix total
     * @param ligne Ligne à ajouter
     */
    public void ajouterLigne(LignePanier ligne) {
        this.lignes.add(ligne);
        if (ligne.getSousTotal() != null) {
            if (this.prixTotal == null) {
                this.prixTotal = BigDecimal.ZERO;
            }
            this.prixTotal = this.prixTotal.add(ligne.getSousTotal());
        }
    }

    /**
     * Calcule le prix total du panier en fonction des lignes
     */
    public void calculerPrixTotal() {
        this.prixTotal = BigDecimal.ZERO;
        if (this.lignes != null) {
            for (LignePanier ligne : this.lignes) {
                if (ligne.getSousTotal() != null) {
                    this.prixTotal = this.prixTotal.add(ligne.getSousTotal());
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panier panier = (Panier) o;
        return id == panier.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", client='" + client + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prixTotal=" + prixTotal +
                ", status=" + status +
                ", estValide=" + estValide +
                ", lignes=" + (lignes != null ? lignes.size() : 0) +
                '}';
    }
}