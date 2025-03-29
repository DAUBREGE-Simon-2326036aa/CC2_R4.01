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
}
