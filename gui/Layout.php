<?php
namespace gui;

/**
 * Classe Layout - Gère le template de base des pages
 *
 * Cette classe permet de :
 * - Charger un fichier template HTML
 * - Remplacer les placeholders par le contenu dynamique
 * - Afficher la page complète
 */
class Layout
{
    /**
     * Chemin vers le fichier template
     * @var string
     */
    protected $templateFile;

    /**
     * Constructeur de la classe Layout
     *
     * @param string $templateFile Chemin vers le fichier template à utiliser
     */
    public function __construct($templateFile)
    {
        $this->templateFile = $templateFile;
    }

    /**
     * Génère et affiche la page complète
     *
     * Remplace les placeholders dans le template par :
     * - %title% → Le titre de la page
     * - %connexion% → Le statut de connexion (non utilisé dans cette implémentation)
     * - %content% → Le contenu principal de la page
     *
     * @param string $title Titre de la page
     * @param string $connexion Statut de connexion (non utilisé)
     * @param string $content Contenu HTML principal
     */
    public function display($title, $connexion, $content)
    {
        // Charge le template depuis le fichier
        $page = file_get_contents($this->templateFile);

        // Remplace les placeholders par le contenu dynamique
        $page = str_replace(
            ['%title%', '%connexion%', '%content%'],
            [$title, $connexion, $content],
            $page
        );

        // Affiche la page générée
        echo $page;
    }
}