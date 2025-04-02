package fr.univamu.iut.apipanier2.models;

/**
 * Classe représentant une ligne de panier avec les détails du produit associé
 */
public class LignePanierDetailed extends LignePanier {
    
    /**
     * Produit associé à la ligne
     */
    private Produit produit;
    
    /**
     * Constructeur par défaut
     */
    public LignePanierDetailed() {
        super();
    }
    
    /**
     * Constructeur à partir d'une ligne existante et d'un produit
     */
    public LignePanierDetailed(LignePanier ligne, Produit produit) {
        super(ligne.getId(), ligne.getPanierId(), ligne.getProduitId(), ligne.getQuantite(), ligne.getPrixUnitaire());
        this.produit = produit;
    }
    
    public Produit getProduit() {
        return produit;
    }
    
    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    /**
     * Crée une ligne détaillée à partir d'une ligne simple et d'un produit
     */
    public static LignePanierDetailed fromLignePanier(LignePanier ligne, Produit produit) {
        return new LignePanierDetailed(ligne, produit);
    }
    
    @Override
    public String toString() {
        return "LignePanierDetailed{" +
                "id=" + getId() +
                ", panierId=" + getPanierId() +
                ", produitId=" + getProduitId() +
                ", quantite=" + getQuantite() +
                ", prixUnitaire=" + getPrixUnitaire() +
                ", sousTotal=" + getSousTotal() +
                ", produit=" + (produit != null ? produit.getNom() : "null") +
                '}';
    }
}
