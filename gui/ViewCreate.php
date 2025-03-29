<?php
namespace gui;

include_once "View.php";

class ViewCreate extends View
{
    public function __construct($layout)
    {
        parent::__construct($layout);

        $this->title = 'Exemple Annonces Basic PHP: Création du compte';

        $this->content = '
            <form method="post" action="/index.php/annonces">
                <label for="login"> Votre identifiant </label> :
                <input type="text" name="login" id="login" placeholder="defaut" maxlength="12" required />
                <br />
                <label for="password"> Votre mot de passe </label> :
                <input type="password" name="password" id="password" minlength="12" required />
                <br />
                <label for="name"> Votre Nom </label> :
                <input type="text" name="name" id="name" placeholder="defaut" maxlength="20" required />
                <br />
                <label for="firstName"> Votre Prénom </label> :
                <input type="text" name="firstName" id="firstName" placeholder="defaut" maxlength="20" required />
                <br />
                <input type="submit" value="Envoyer">
                <input type="reset"> 
            </form>
            <a href="/index.php">retour</a>';
    }
}