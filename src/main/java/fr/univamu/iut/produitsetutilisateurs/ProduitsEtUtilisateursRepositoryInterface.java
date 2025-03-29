package fr.univamu.iut.produitsetutilisateurs;

import java.util.ArrayList;

public interface ProduitsEtUtilisateursRepositoryInterface {

    public void close();

    public Utilisateur getUtilisateurById(int id);

    public ArrayList<Utilisateur> getAllUtilisateurs();

    public boolean updateUtilisateur(int id, String nom, String password, String email);

    public boolean deleteUtilisateur(int id);

    public Produit getProduitById(int id);

    public ArrayList<Produit> getAllProduits();

    public boolean updateProduit(int id, String nom, float prix, PrixCategorie prixCategorie, String typeProduit);

    public boolean deleteProduit(int id);

    public int authentification(String nom, String password);

}
