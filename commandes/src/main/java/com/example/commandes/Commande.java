package com.example.commandes;

public class Commande {
    private int id;
    private int tailleCommande;
    private int idClient;

    public Commande() {
    }
    public int getId() {
        return id;
    }
    public Commande(int id) {
        this.id = id;
    }
    public int getTailleCommande() {
        return tailleCommande;
    }
    public void setTailleCommande(int tailleCommande) {
        this.tailleCommande = tailleCommande;
    }
}
