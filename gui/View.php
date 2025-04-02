<?php
namespace gui;

/**
 * Classe abstraite View - Classe de base pour toutes les vues de l'application
 *
 * Cette classe fournit une structure commune pour toutes les vues :
 * - Gestion du titre de la page
 * - Gestion du contenu principal
 * - Intégration avec le layout
 * - Méthode d'affichage standardisée
 */
abstract class View
{
    /**
     * @var string $title Le titre de la page
     */
    protected $title = '';

    /**
     * @var string $content Le contenu HTML principal de la page
     */
    protected $content = '';

    /**
     * @var Layout $layout L'objet layout utilisé pour le rendu
     */
    protected $layout;

    /**
     * Constructeur de la classe View
     *
     * @param Layout $layout L'objet layout à utiliser pour le rendu
     */
    public function __construct($layout)
    {
        $this->layout = $layout;
    }

    /**
     * Affiche la vue en utilisant le layout
     *
     * Cette méthode utilise le layout pour afficher la page complète
     * en lui passant le titre et le contenu préparés
     */
    public function display()
    {
        $this->layout->display($this->title, "", $this->content);
    }
}