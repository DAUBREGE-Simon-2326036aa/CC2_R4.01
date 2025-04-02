package com.example.commandes;

public class CommandeRepositoryInterface {

    public void close();

    public Commande getCommandeById(int id);

    public ArrayList<Commande> getAllCommande();


}
