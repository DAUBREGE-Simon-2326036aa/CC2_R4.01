<?php
namespace gui;

include_once "View.php";

class ViewPanier extends View
{
    public function __construct($layout, $paniers = [])
    {
        parent::__construct($layout);

        $this->title = 'Coopérative agricole : Nos paniers';

        $content = '
        <div class="hero-section">
            <h1>Nos paniers de saison</h1>
            <p>Découvrez nos sélections de fruits et légumes frais</p>
        </div>
        <div class="content-blocks">';

        if (!empty($paniers)) {
            foreach ($paniers as $panier) {
                $panierClass = strtolower(str_replace(' ', '-', htmlspecialchars($panier['nom'])));
                $content .= '
                <div class="content-block panier-block ' . $panierClass . '">
                    <div class="block-bg"></div>
                    <div class="block-content">
                        <h2>' . htmlspecialchars($panier['nom']) . '</h2>
                        <p class="price">' . htmlspecialchars($panier['prixTotal']) . '€</p>
                        <p>' . htmlspecialchars($panier['description']) . '</p>
                        
                        <button type="button" 
                                class="btn" 
                                onclick="toggleDetails(' . $panier['id'] . ')"
                                data-panier-id="' . $panier['id'] . '">
                            Afficher les détails ▶
                        </button>

                        <div id="details-' . $panier['id'] . '" class="panier-details">
                            <div class="panier-info-group">
                                <p><strong>Client :</strong> ' . htmlspecialchars($panier['client']) . '</p>
                                <p><strong>Statut :</strong> ' .
                    ($panier['status'] === 'e' ? '🟡 En cours' : '🟢 Finalisé') .
                    ($panier['estValide'] ? ' (✅ Validé)' : ' (❌ Non validé)') . '
                                </p>
                            </div>
                            
                            <div class="panier-articles">
                                <h3>Contenu du panier :</h3>
                                <ul>';

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
            $content .= '<p class="no-paniers">Aucun panier disponible pour le moment.</p>';
        }

        $content .= '</div>';

        $content .= '<script>
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