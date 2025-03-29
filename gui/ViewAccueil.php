<?php
namespace gui;

include_once "View.php";

class ViewAccueil extends View
{
    public function __construct($layout)
    {
        parent::__construct($layout);

        $this->title = 'Exemple Annonces Basic PHP: Connexion';

        $this->content = '
            <h1>Bienvenue sur le site de la coopérative agricole</h1>
            
            ';
    }
}