package fr.univamu.iut.apipanier2.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Classe représentant une ligne de panier
 */
public class LignePanier {

    /**
     * Identifiant de la ligne de panier
     */
    private int id;

    /**
     * Identifiant du panier associé
     */
    private int panierId;

    /**
     * Identifiant du produit
     */
    private int produitId;

    /**
     * Quantité du produit
     */
    private BigDecimal quantite;

    /**
     * Prix unitaire du produit
     */
    private BigDecimal prixUnitaire;

    /**
     * Sous-total calculé (quantité * prix unitaire)
     */
    private BigDecimal sousTotal;

    /**
     * Constructeur par défaut
     */
    public LignePanier() {
        this.quantite = BigDecimal.ONE;
        this.prixUnitaire = BigDecimal.ZERO;
        this.sousTotal = BigDecimal.ZERO;
    }

    /**
     * Constructeur avec paramètres
     * @param id Identifiant de la ligne
     * @param panierId Identifiant du panier associé
     * @param produitId Identifiant du produit
     * @param quantite Quantité du produit
     * @param prixUnitaire Prix unitaire du produit
     */
    public LignePanier(int id, int panierId, int produitId, BigDecimal quantite, BigDecimal prixUnitaire) {
        this.id = id;
        this.panierId = panierId;
        this.produitId = produitId;
        this.quantite = quantite != null ? quantite : BigDecimal.ONE;
        this.prixUnitaire = prixUnitaire != null ? prixUnitaire : BigDecimal.ZERO;
        this.calculerSousTotal();
    }
    
    /**
     * Recalcule le sous-total en fonction de la quantité et du prix unitaire
     */
    private void calculerSousTotal() {
        if (this.quantite != null && this.prixUnitaire != null) {
            this.sousTotal = this.quantite.multiply(this.prixUnitaire)
                            .setScale(2, RoundingMode.HALF_UP);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPanierId() {
        return panierId;
    }

    public void setPanierId(int panierId) {
        this.panierId = panierId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
        this.calculerSousTotal();
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        this.calculerSousTotal();
    }

    public BigDecimal getSousTotal() {
        return sousTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LignePanier that = (LignePanier) o;
        return id == that.id &&
               panierId == that.panierId &&
               produitId == that.produitId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, panierId, produitId);
    }

    @Override
    public String toString() {
        return "LignePanier{" +
                "id=" + id +
                ", panierId=" + panierId +
                ", produitId=" + produitId +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                ", sousTotal=" + sousTotal +
                '}';
    }
}
