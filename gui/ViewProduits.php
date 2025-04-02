<?php
namespace gui;

include_once "View.php";

/**
 * Classe ViewProduits - Affiche la vue des produits de la coopérative agricole
 *
 * Cette classe étend la classe View et génère l'affichage des produits groupés par catégorie.
 */
class ViewProduits extends View
{
    /**
     * Constructeur de la classe ViewProduits
     *
     * @param Layout $layout Le layout utilisé pour le rendu
     * @param array $produits Tableau des produits à afficher (optionnel)
     */
    public function __construct($layout, $produits = [])
    {
        parent::__construct($layout);

        $this->title = 'Coopérative agricole : Nos produits';

        // Section hero de la page
        $content = '
        <div class="hero-section">
            <h1>Nos produits frais</h1>
            <p>Découvrez notre sélection de produits locaux et de saison</p>
        </div>';

        if (!empty($produits)) {
            // Groupement des produits par type
            $groupedProducts = [];
            foreach ($produits as $product) {
                $groupedProducts[$product['typeProduit']][] = $product;
            }

            // Génération du contenu pour chaque catégorie de produits
            foreach ($groupedProducts as $type => $produitsGroupe) {
                $content .= '
                <div class="category-block ">
                    <div class="category-overlay category-' . strtolower($type) . '">
                        <h2>' . [
                        'LAITERIE' => 'Produits Laitiers',
                        'OEUFS' => 'Œufs Frais',
                        'FRUIT' => 'Fruits de Saison',
                        'LEGUME' => 'Légumes du Potager',
                        'EPICERIE' => 'Épicerie Paysanne',
                        'BOISSON' => 'Boissons Naturelles',
                        'BOULANGERIE' => 'Pain Artisanal'
                    ][$type] . '</h2>
                        <div class="products-grid">';

                // Affichage de chaque produit dans la catégorie
                foreach ($produitsGroupe as $product) {
                    $unite = [
                        'AU_KILO' => 'kg',
                        'A_L_UNITE' => 'unité',
                        'A_LA_DOUZAINE' => 'douzaine'
                    ][$product['prixCategorie']] ?? '';

                    $content .= '
                            <div class="product-card">
                                <h3>' . htmlspecialchars($product['nom']) . '</h3>
                                <div class="product-price">
                                    ' . number_format($product['prix'], 2, ',', ' ') . '€<span>/' . $unite . '</span>
                                </div>
                            </div>';
                }

                $content .= '
                        </div>
                    </div>
                </div>';
            }
        } else {
            // Message affiché lorsqu'aucun produit n'est disponible
            $content .= '<p class="no-products">Aucun produit disponible actuellement</p>';
        }

        $this->content = $content;
    }
}