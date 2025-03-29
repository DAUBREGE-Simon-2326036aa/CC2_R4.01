package fr.univamu.iut.produitsetutilisateurs;

public enum PrixCategorie {
    AU_KILO("Au kilo"),
    A_L_UNITE("À l'unité"),
    A_LA_DOUZAINE("À la douzaine");

    private final String nomAffichage;

    PrixCategorie(String nomAffichage) {
        this.nomAffichage = nomAffichage;
    }

    public String getNomAffichage() {
        return nomAffichage;
    }
}