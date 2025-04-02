package com.example.commandes;

import java.util.ArrayList;

public class CommandeRepositoryInterface {

    public void close();

    public Commande getCommandeById(int id);

    public ArrayList<Commande> getAllCommande();

    public boolean CreateCommande(int id):

    public boolean UpdateCommande(int id);

    public boolean DeleteCommande(int id);
}
