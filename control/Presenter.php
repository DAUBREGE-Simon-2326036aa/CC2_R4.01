<?php
namespace control;

/**
 * Classe Presenter - Gère la présentation des annonces
 *
 * Cette classe transforme les données brutes des annonces en HTML
 * pour l'affichage dans les vues.
 */
class Presenter
{
    /**
     * Service de vérification/récupération des annonces
     * @var mixed
     */
    protected $annoncesCheck;

    /**
     * Constructeur de la classe Presenter
     *
     * @param mixed $annoncesCheck Service fournissant les données des annonces
     */
    public function __construct($annoncesCheck)
    {
        $this->annoncesCheck = $annoncesCheck;
    }

    /**
     * Génère le HTML pour la liste complète des annonces
     *
     * @return string|null HTML de la liste des annonces ou null si aucune annonce
     */
    public function getAllAnnoncesHTML()
    {
        $content = null;
        if ($this->annoncesCheck->getAnnoncesTxt() != null) {
            $content = '<h1>List of Posts</h1><ul>';
            foreach ($this->annoncesCheck->getAnnoncesTxt() as $post) {
                $content .= '<li>';
                $content .= '<a href="/index.php/post?id=' . $post['id'] . '">'
                    . htmlspecialchars($post['title']) . '</a>';
                $content .= '</li>';
            }
            $content .= '</ul>';
        }
        return $content;
    }

    /**
     * Génère le HTML pour l'affichage d'une annonce unique
     *
     * @return string|null HTML de l'annonce détaillée ou null si aucune annonce
     */
    public function getCurrentPostHTML()
    {
        $content = null;
        if ($this->annoncesCheck->getAnnoncesTxt() != null) {
            $post = $this->annoncesCheck->getAnnoncesTxt()[0];

            $content = '<h1>' . htmlspecialchars($post['title']) . '</h1>';
            $content .= '<div class="date">' . htmlspecialchars($post['date']) . '</div>';
            $content .= '<div class="body">' . htmlspecialchars($post['body']) . '</div>';
        }
        return $content;
    }
}