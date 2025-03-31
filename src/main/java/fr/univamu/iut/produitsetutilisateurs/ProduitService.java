package fr.univamu.iut.produitsetutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class ProduitService {

    protected ProduitsEtUtilisateursRepositoryInterface repo;

    public ProduitService(ProduitsEtUtilisateursRepositoryInterface repo) {
        this.repo = repo;
    }

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

    public boolean updateProduit(int id, Produit produit) {
        return repo.updateProduit(id, produit.getNom(), produit.getPrix(), produit.getPrixCategorie(), produit.getTypeProduit());
    }

    public boolean deleteProduit(int id) {
        return repo.deleteProduit(id);
    }

    public boolean createProduit(String nom, float prix, PrixCategorie prixCategorie, String typeProduit) {
        return repo.createProduit(nom, prix, prixCategorie, typeProduit);
    }
}
