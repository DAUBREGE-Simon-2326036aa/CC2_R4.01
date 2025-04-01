package fr.univamu.iut.produitsetutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;

/**
 * Service métier pour la gestion des produits.
 * <p>
 * Cette classe fournit les opérations métier pour manipuler les produits
 * et effectue la conversion des objets Produit en format JSON.
 * Elle fait le lien entre les ressources REST et le repository de données.
 * </p>
 */
public class ProduitService {

    protected ProduitsEtUtilisateursRepositoryInterface repo;

    /**
     * Constructeur initialisant le service avec un repository.
     *
     * @param repo l'interface du repository à utiliser pour l'accès aux données
     */
    public ProduitService(ProduitsEtUtilisateursRepositoryInterface repo) {
        this.repo = repo;
    }

    /**
     * Récupère tous les produits au format JSON.
     *
     * @return une chaîne JSON contenant la liste de tous les produits,
     *         ou une chaîne vide en cas d'erreur de sérialisation
     * @see Produit
     */
    public String getAllProduitsJSON() {
        ArrayList<Produit> allProducts = repo.getAllProduits();

        String result = "";
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allProducts);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Récupère un produit spécifique par son ID au format JSON.
     *
     * @param id l'identifiant du produit à récupérer
     * @return une chaîne JSON représentant le produit demandé,
     *         ou une chaîne vide si le produit n'existe pas ou en cas d'erreur
     */
    public String getProduitByIdJSON(int id) {
        Produit product = repo.getProduitById(id);

        String result = "";
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(product);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Met à jour les informations d'un produit existant.
     *
     * @param id l'identifiant du produit à modifier
     * @param produit objet contenant les nouvelles informations du produit
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateProduit(int id, Produit produit) {
        return repo.updateProduit(id, produit.getNom(), produit.getPrix(),
                produit.getPrixCategorie(), produit.getTypeProduit());
    }

    /**
     * Supprime un produit existant.
     *
     * @param id l'identifiant du produit à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteProduit(int id) {
        return repo.deleteProduit(id);
    }

    /**
     * Crée un nouveau produit.
     *
     * @param nom le nom du nouveau produit
     * @param prix le prix du nouveau produit
     * @param prixCategorie la catégorie de prix du produit
     * @param typeProduit le type de produit
     * @return true si la création a réussi, false sinon
     */
    public boolean createProduit(String nom, float prix,
                                 PrixCategorie prixCategorie, String typeProduit) {
        return repo.createProduit(nom, prix, prixCategorie, typeProduit);
    }
}