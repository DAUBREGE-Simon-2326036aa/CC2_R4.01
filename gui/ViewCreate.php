<?php
namespace gui;

include_once "View.php";

class ViewCreate extends View
{
    public function __construct($layout)
    {
        parent::__construct($layout);

        $this->title = 'Coopérative agricole : Création du compte';

        $this->content = '
        <div class="hero-section">
            <h1>Création de compte</h1>
            <p>Rejoignez notre communauté en créant votre compte</p>
        </div>
        
        <div class="login-container">
            <form method="post" action="/index.php/create" class="login-form">
                <h3>Créer un nouveau compte</h3>
                
                <div class="form-group">
                    <label for="firstName">Votre Prénom</label>
                    <input type="text" name="firstName" id="firstName" placeholder="Prénom" maxlength="20" required />
                </div>
                
                <div class="form-group">
                    <label for="email">Votre Email</label>
                    <input type="email" name="email" id="email" placeholder="email@exemple.com" required />
                </div>
                
                <div class="form-group">
                    <label for="password">Votre mot de passe</label>
                    <input type="password" name="password" id="password" placeholder="Mot de passe (min. 12 caractères)" minlength="12" required />
                </div>
                
                <div style="display: flex; justify-content: space-between; margin: 20px auto 5px auto;">
                    <button type="submit" class="btn">Créer le compte</button>
                </div>
                
                <div class="register-link">
                    <a href="/index.php">Retour à l\'accueil</a>
                </div>
            </form>
        </div>';
    }
}