package fr.univamu.iut.produitsetutilisateurs;

import java.util.ArrayList;

public interface UtilisateurRepositoryInterface {

    public void close();

    public Utilisateur getUtilisateurById(int id);

    public ArrayList<Utilisateur> getAllUtilisateurs();

    public boolean updateUtilisateur(int id, String nom, String password, String email);

    public boolean deleteUtilisateur(int id);

}
