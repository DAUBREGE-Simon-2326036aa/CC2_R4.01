package fr.univamu.iut.produitsetutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class UtilisateurService {

    protected UtilisateurRepositoryInterface userRepo;

    public UtilisateurService(UtilisateurRepositoryInterface userRepo) {
        this.userRepo = userRepo;
    }

    public String getAllUtilisateursJSON() {
        ArrayList<Utilisateur> allUsers = userRepo.getAllUtilisateurs();

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
