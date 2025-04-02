<?php

namespace gui;

include_once "View.php";

/**
 * Classe ViewAccueil - Gère l'affichage de la page d'accueil
 *
 * Cette classe étend la classe View et génère l'interface d'accueil différente selon
 * si l'utilisateur est connecté ou non. Elle affiche :
 * - Pour les utilisateurs connectés : les sections paniers, produits et commandes
 * - Pour les visiteurs non connectés : un formulaire de connexion
 */
class ViewAccueil extends View
{
    /**
     * Constructeur de la classe ViewAccueil
     *
     * Initialise la vue avec le layout fourni et construit le contenu adapté
     * en fonction de l'état de connexion de l'utilisateur
     *
     * @param Layout $layout Le layout utilisé pour le rendu de la page
     */
    public function __construct($layout)
    {
        parent::__construct($layout);

        // Titre de la page
        $this->title = 'Coopérative Agricole - Accueil';

        // Vérifie si l'utilisateur est connecté
        $isLoggedIn = isset($_SESSION['login']);

        // Section hero commune à tous les utilisateurs
        $this->content = '
            <!-- Section hero avec le titre principal -->
            <section class="hero-section">
                <h1>Bienvenue sur le site de la coopérative agricole</h1>
                <p>Découvrez nos paniers de produits locaux et de saison, préparés avec soin par nos producteurs</p>
            </section>';

        // Contenu pour les utilisateurs connectés
        if ($isLoggedIn) {
            $this->content .= '
            <!-- Blocs de contenu pour utilisateurs connectés -->
            <div class="content-blocks">
                <!-- Bloc Paniers -->
                <div class="content-block" id="paniers">
                    <div class="block-bg"></div>
                    <div class="block-content">
                        <h2>Nos Paniers</h2>
                        <p>Consultez les paniers disponibles cette semaine, leurs compositions et les quantités restantes</p>
                        <a href="/index.php/paniers" class="btn">Voir les paniers</a>
                    </div>
                </div>
                
                <!-- Bloc Produits -->
                <div class="content-block" id="produits">
                    <div class="block-bg"></div>
                    <div class="block-content">
                        <h2>Nos produits</h2>
                        <p>Consulter une sélection de produits frais et locaux issus de la coopérative agricole, mettant en valeur leurs qualités artisanales et leur engagement pour une agriculture durable.</p>
                        <a href="/index.php/produits" class="btn">Voir nos produits</a>
                    </div>
                </div>
                
                <!-- Bloc Commandes -->
                <div class="content-block" id="commandes">
                    <div class="block-bg"></div>
                    <div class="block-content">
                        <h2>Mes Commandes</h2>
                        <p>Suivez l\'état de vos réservations et consultez vos commandes passées</p>
                        <a href="/index.php/commandes" class="btn">Accéder à mes commandes</a>
                    </div>
                </div>
            </div>';
        }

        // Contenu pour les visiteurs non connectés
        if (!$isLoggedIn) {
            $this->content .= '
            <!-- Section de connexion pour visiteurs non connectés -->
            <div class="login-section">
                <div class="login-container">
                    <!-- Formulaire de connexion -->
                    <form method="POST" action="/index.php" class="login-form">
                        <h3>Connexion à votre compte</h3>
                        <!-- Champ Nom -->
                        <div class="form-group">
                            <label for="nom">Nom :</label>
                            <input type="text" id="nom" name="nom" required placeholder="Votre nom">
                        </div>
                        <!-- Champ Mot de passe -->
                        <div class="form-group">
                            <label for="password">Mot de passe :</label>
                            <input type="password" id="password" name="password" required placeholder="Votre mot de passe">
                        </div>
                        <!-- Bouton de soumission -->
                        <button type="submit" class="btn login-btn">Se connecter</button>
                        <!-- Lien vers la création de compte -->
                        <p class="register-link">Pas encore inscrit ? <a href="/index.php/create">Créer un compte</a></p>
                    </form>
                </div>
            </div>';
        }
    }
}