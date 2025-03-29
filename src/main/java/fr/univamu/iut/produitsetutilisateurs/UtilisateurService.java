package fr.univamu.iut.produitsetutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class UtilisateurService {

    protected ProduitsEtUtilisateursRepositoryInterface repo;

    public UtilisateurService(ProduitsEtUtilisateursRepositoryInterface repo) {
        this.repo = repo;
    }

    public String getAllUtilisateursJSON() {
        ArrayList<Utilisateur> allUsers = repo.getAllUtilisateurs();

        String result = "";
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allUsers);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    public String getUtilisateurByIdJSON(int id) {
        Utilisateur user = repo.getUtilisateurById(id);

        String result = "";
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(user);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    public boolean updateUtilisateur(int id, Utilisateur utilisateur) {
        return repo.updateUtilisateur(id, utilisateur.getNom(), utilisateur.getPassword(), utilisateur.getEmail());
    }

    public boolean deleteUtilisateur(int id) {
        return repo.deleteUtilisateur(id);
    }
}
