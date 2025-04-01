package fr.univamu.iut.produitsetutilisateurs;

/**
 * Énumération représentant les différentes catégories de prix possibles pour un produit.
 * <p>
 * Chaque constante d'énumération correspond à un mode de tarification spécifique
 * et possède un libellé d'affichage pour l'interface utilisateur.
 * </p>
 */
public enum PrixCategorie {
    /**
     * Prix calculé au kilogramme
     */
    AU_KILO("Au kilo"),

    /**
     * Prix à l'unité
     */
    A_L_UNITE("À l'unité"),

    /**
     * Prix à la douzaine
     */
    A_LA_DOUZAINE("À la douzaine");

    private final String nomAffichage;

    /**
     * Constructeur de l'énumération PrixCategorie.
     *
     * @param nomAffichage le libellé à afficher pour cette catégorie de prix
     */
    PrixCategorie(String nomAffichage) {
        this.nomAffichage = nomAffichage;
    }

    /**
     * Retourne le libellé d'affichage de la catégorie de prix.
     *
     * @return le nom à afficher pour cette catégorie
     */
    public String getNomAffichage() {
        return nomAffichage;
    }
}