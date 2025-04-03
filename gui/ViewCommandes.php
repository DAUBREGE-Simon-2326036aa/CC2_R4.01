<?php
namespace gui;

include_once "View.php";

/**
 * Classe ViewCommandes - Affiche la vue des commandes d'un client
 *
 * Cette classe étend la classe View et génère l'affichage des commandes avec leurs détails.
 * Elle inclut également un système d'affichage/masquage des détails via JavaScript.
 */
class ViewCommandes extends View
{
    /**
     * Constructeur de la classe ViewCommandes
     *
     * @param Layout $layout Le layout utilisé pour le rendu
     * @param array $commandes Tableau des commandes à afficher (optionnel)
     */
    public function __construct($layout, $commandes = [])
    {
        parent::__construct($layout);

        $this->title = 'Vos commandes';

        // Section hero et conteneur principal
        $content = '
        <div class="hero-section">
            <h1>Vos commandes</h1>
            <p>Retrouvez ici l\'historique de vos achats</p>
        </div>
        <div class="content-blocks">';

        // Affichage des commandes s'il y en a
        if (!empty($commandes)) {
            foreach ($commandes as $commande) {
                // Préparation des classes CSS pour la commande
                $commandeClass = 'commande-' . htmlspecialchars($commande['id']);
                $content .= '
                <div class="content-block commande-block ' . $commandeClass . '">
                    <div class="block-bg"></div>
                    <div class="block-content">
                        <h2>Commande #' . htmlspecialchars($commande['id']) . '</h2>
                        <p class="date">Passée le ' . htmlspecialchars($commande['date_creation']) . '</p>
                        <p class="price">Total : ' . htmlspecialchars($commande['montant_total']) . '€</p>
                        
                        <!-- Bouton pour afficher/masquer les détails -->
                        <button type="button" 
                                class="btn" 
                                onclick="toggleDetails(' . $commande['id'] . ')"
                                data-commande-id="' . $commande['id'] . '">
                            Afficher les détails ▶
                        </button>

                        <!-- Section des détails de la commande (masquée par défaut) -->
                        <div id="details-' . $commande['id'] . '" class="commande-details">
                            <div class="commande-info-group">
                                <p><strong>Adresse de livraison :</strong> ' . htmlspecialchars($commande['adresse_livraison']) . '</p>
                                <p><strong>Méthode de paiement :</strong> ' . htmlspecialchars($commande['methode_paiement']) . '</p>
                            </div>
                            
                            <!-- Liste des articles de la commande -->
                            <div class="commande-articles">
                                <h3>Articles commandés :</h3>
                                <ul>';

                // Affichage de chaque article de la commande
                foreach ($commande['articles'] as $article) {
                    $content .= '
                                    <li>
                                        ' . htmlspecialchars($article['quantite']) . ' x ' .
                        htmlspecialchars($article['nom_produit']) . '
                                        <span class="sous-total">(' .
                        htmlspecialchars($article['prix_unitaire']) . '€/unité) → ' .
                        htmlspecialchars($article['sous_total']) . '€</span>
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
            // Message affiché lorsqu'aucune commande n'est disponible
            $content .= '<p class="no-commandes">Vous n\'avez pas encore passé de commande.</p>';
        }

        $content .= '</div>';

        // Script JavaScript pour gérer l'affichage des détails
        $content .= '<script>
                /**
                 * Fonction pour afficher/masquer les détails d\'une commande
                 * @param {number} commandeId - L\'identifiant de la commande
                 */
                function toggleDetails(commandeId) {
                    const button = document.querySelector(`[data-commande-id="${commandeId}"]`);
                    const commandeBlock = button.closest(".commande-block");
    
                    commandeBlock.classList.toggle("expanded");
    
                    if (commandeBlock.classList.contains("expanded")) {
                        button.textContent = "Masquer les détails ▼";
                    } else {
                        button.textContent = "Afficher les détails ▶";
                    }
                }
            </script>';

        $this->content = $content;
    }
}