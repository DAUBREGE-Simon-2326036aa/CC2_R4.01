<?php
namespace gui;

include_once "View.php";

/**
 * Classe ViewCreate - Gère l'affichage du formulaire de création de compte
 *
 * Cette classe étend la classe View et génère l'interface de création de compte utilisateur
 * avec un formulaire comprenant les champs : prénom, email et mot de passe.
 */
class ViewCreate extends View
{
    /**
     * Constructeur de la classe ViewCreate
     *
     * Initialise la vue avec le layout fourni et construit le formulaire de création de compte
     *
     * @param Layout $layout Le layout utilisé pour le rendu de la page
     */
    public function __construct($layout)
    {
        parent::__construct($layout);

        // Titre de la page
        $this->title = 'Coopérative agricole : Création du compte';

        // Contenu HTML du formulaire de création de compte
        $this->content = '
        <!-- Section hero avec titre et sous-titre -->
        <div class="hero-section">
            <h1>Création de compte</h1>
            <p>Rejoignez notre communauté en créant votre compte</p>
        </div>
        
        <!-- Conteneur principal du formulaire -->
        <div class="login-container">
            <!-- Formulaire de création de compte -->
            <form method="post" action="/index.php/create" class="login-form">
                <h3>Créer un nouveau compte</h3>
                
                <!-- Champ Prénom -->
                <div class="form-group">
                    <label for="firstName">Votre Prénom</label>
                    <input type="text" name="firstName" id="firstName" 
                           placeholder="Prénom" maxlength="20" required />
                </div>
                
                <!-- Champ Email -->
                <div class="form-group">
                    <label for="email">Votre Email</label>
                    <input type="email" name="email" id="email" 
                           placeholder="email@exemple.com" required />
                </div>
                
                <!-- Champ Mot de passe -->
                <div class="form-group">
                    <label for="password">Votre mot de passe</label>
                    <input type="password" name="password" id="password" 
                           placeholder="Mot de passe (min. 12 caractères)" 
                           minlength="12" required />
                </div>
                
                <!-- Bouton de soumission -->
                <div style="display: flex; justify-content: space-between; margin: 20px auto 5px auto;">
                    <button type="submit" class="btn">Créer le compte</button>
                </div>
                
                <!-- Lien de retour à l\'accueil -->
                <div class="register-link">
                    <a href="/index.php">Retour à l\'accueil</a>
                </div>
            </form>
        </div>';
    }
}