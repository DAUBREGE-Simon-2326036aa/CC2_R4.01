<?php
namespace gui;

include_once "View.php";

/**
 * Classe ViewPanier - Affiche la vue des paniers de la coopérative agricole
 *
 * Cette classe étend la classe View et génère l'affichage des paniers avec leurs détails.
 * Elle inclut également un système d'affichage/masquage des détails via JavaScript.
 */
class ViewPanier extends View
{
    /**
     * Constructeur de la classe ViewPanier
     *
     * @param Layout $layout Le layout utilisé pour le rendu
     * @param array $paniers Tableau des paniers à afficher (optionnel)
     */
    public function __construct($layout, $paniers = [])
    {
        parent::__construct($layout);

        $this->title = 'Coopérative agricole : Nos paniers';

        // Section hero et conteneur principal
        $content = '
        <div class="hero-section">
            <h1>Nos paniers de saison</h1>
            <p>Découvrez nos sélections de fruits et légumes frais</p>
        </div>
        <div class="content-blocks">';

        // Affichage des paniers s'il y en a
        if (!empty($paniers)) {
            foreach ($paniers as $panier) {
                // Préparation des classes CSS pour le panier
                $panierClass = strtolower(str_replace(' ', '-', htmlspecialchars($panier['nom'])));
                $content .= '
                <div class="content-block panier-block ' . $panierClass . '">
                    <div class="block-bg"></div>
                    <div class="block-content">
                        <h2>' . htmlspecialchars($panier['nom']) . '</h2>
                        <p class="price">' . htmlspecialchars($panier['prixTotal']) . '€</p>
                        <p>' . htmlspecialchars($panier['description']) . '</p>
                        
                        <!-- Bouton pour afficher/masquer les détails -->
                        <button type="button" 
                                class="btn" 
                                onclick="toggleDetails(' . $panier['id'] . ')"
                                data-panier-id="' . $panier['id'] . '">
                            Afficher les détails ▶
                        </button>

                        <!-- Section des détails du panier (masquée par défaut) -->
                        <div id="details-' . $panier['id'] . '" class="panier-details">
                            <div class="panier-info-group">
                                <p><strong>Statut :</strong> ' .
                    ($panier['status'] === 'e' ? '🟡 En cours' : '🟢 Finalisé') .
                    ($panier['estValide'] ? ' (✅ Validé)' : ' (❌ Non validé)') . '
                                </p>
                            </div>
                            
                            <!-- Liste des articles du panier -->
                            <div class="panier-articles">
                                <h3>Contenu du panier :</h3>
                                <ul>';

                // Affichage de chaque ligne du panier
                foreach ($panier['lignes'] as $ligne) {
                    $content .= '
                                    <li>
                                        ' . htmlspecialchars($ligne['quantite']) . ' x Produit #' .
                        htmlspecialchars($ligne['produitId']) . '
                                        <span class="sous-total">(' .
                        htmlspecialchars($ligne['prixUnitaire']) . '€/unité) → ' .
                        htmlspecialchars($ligne['sousTotal']) . '€</span>
                                    </li>';
                }

                $content .= '
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>';
            }
        } else {
            // Message affiché lorsqu'aucun panier n'est disponible
            $content .= '<p class="no-paniers">Aucun panier disponible pour le moment.</p>';
        }

        $content .= '</div>';

        // Script JavaScript pour gérer l'affichage des détails
        $content .= '<script>
                /**
                 * Fonction pour afficher/masquer les détails d\'un panier
    * @param {number} panierId - L\'identifiant du panier
                 */
                function toggleDetails(panierId) {
                    const button = document.querySelector(`[data-panier-id="${panierId}"]`);
                    const panierBlock = button.closest(".panier-block");
    
                    panierBlock.classList.toggle("expanded");
    
                    if (panierBlock.classList.contains("expanded")) {
                        button.textContent = "Masquer les détails ▼";
                    } else {
                        button.textContent = "Afficher les détails ▶";
                    }
                }
            </script>';

        $this->content = $content;

    }
}