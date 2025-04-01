package fr.univamu.iut.produitsetutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;

/**
 * Service pour la gestion des opérations métier relatives aux utilisateurs.
 * Fournit des méthodes pour manipuler les données utilisateurs et les convertir en JSON.
 */
public class UtilisateurService {

    protected ProduitsEtUtilisateursRepositoryInterface repo;

    /**
     * Constructeur initialisant le service avec un repository.
     *
     * @param repo l'interface du repository à utiliser pour l'accès aux données
     */
    public UtilisateurService(ProduitsEtUtilisateursRepositoryInterface repo) {
        this.repo = repo;
    }

    /**
     * Récupère tous les utilisateurs au format JSON.
     *
     * @return une chaîne JSON représentant la liste des utilisateurs,
     *         ou une chaîne vide en cas d'erreur
     */
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

    /**
     * Récupère un utilisateur spécifique par son ID au format JSON.
     *
     * @param id l'identifiant de l'utilisateur à récupérer
     * @return une chaîne JSON représentant l'utilisateur,
     *         ou une chaîne vide si l'utilisateur n'existe pas ou en cas d'erreur
     */
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

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur à mettre à jour
     * @param utilisateur objet contenant les nouvelles informations de l'utilisateur
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateUtilisateur(int id, Utilisateur utilisateur) {
        return repo.updateUtilisateur(id, utilisateur.getNom(), utilisateur.getPassword(), utilisateur.getEmail());
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteUtilisateur(int id) {
        return repo.deleteUtilisateur(id);
    }

    /**
     * Authentifie un utilisateur avec son nom et mot de passe.
     *
     * @param nom le nom de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return une chaîne JSON contenant l'ID de l'utilisateur si l'authentification réussit,
     *         null en cas d'échec ou d'erreur
     */
    public String authentification(String nom, String password) {
        int id = repo.authentification(nom, password);

        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(id);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Crée un nouvel utilisateur.
     *
     * @param utilisateur objet contenant les informations du nouvel utilisateur
     * @return true si la création a réussi, false sinon
     */
    public boolean createUtilisateur(Utilisateur utilisateur) {
        return repo.createUtilisateur(utilisateur.getNom(), utilisateur.getPassword(), utilisateur.getEmail());
    }
}