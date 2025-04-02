<?php

namespace gui;

include_once "View.php";

class ViewAccueil extends View
{
    public function __construct($layout)
    {
        parent::__construct($layout);

        $this->title = 'Coopérative Agricole - Accueil';

        $isLoggedIn = isset($_SESSION['login']);

        $this->content = '
            <section class="hero-section">
                <h1>Bienvenue sur le site de la coopérative agricole</h1>
                <p>Découvrez nos paniers de produits locaux et de saison, préparés avec soin par nos producteurs</p>
            </section>';

        if ($isLoggedIn) {
            $this->content .= '
            <div class="content-blocks">
                <div class="content-block" id="paniers">
                    <div class="block-bg"></div>
                    <div class="block-content">
                        <h2>Nos Paniers</h2>
                        <p>Consultez les paniers disponibles cette semaine, leurs compositions et les quantités restantes</p>
                        <a href="/index.php/paniers" class="btn">Voir les paniers</a>
                    </div>
                </div>
                
                <div class="content-block" id="produits">
                    <div class="block-bg"></div>
                    <div class="block-content">
                        <h2>Nos produits</h2>
                        <p>Consulter une sélection de produits frais et locaux issus de la coopérative agricole, mettant en valeur leurs qualités artisanales et leur engagement pour une agriculture durable.</p>
                        <a href="/index.php/produits" class="btn">Voir nos produits</a>
                    </div>
                </div>
                
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

        if (!$isLoggedIn) {
            $this->content .= '
            <div class="login-section">
                <div class="login-container">
                    <form method="POST" action="/index.php" class="login-form">
                        <h3>Connexion à votre compte</h3>
                        <div class="form-group">
                            <label for="nom">Nom :</label>
                            <input type="text" id="nom" name="nom" required placeholder="Votre nom">
                        </div>
                        <div class="form-group">
                            <label for="password">Mot de passe :</label>
                            <input type="password" id="password" name="password" required placeholder="Votre mot de passe">
                        </div>
                        <button type="submit" class="btn login-btn">Se connecter</button>
                        <p class="register-link">Pas encore inscrit ? <a href="/index.php/create">Créer un compte</a></p>
                    </form>
                </div>
            </div>';
        }
    }
}