<?php
namespace gui;

include_once "View.php";

class ViewPanier extends View
{
    public function __construct($layout)
    {
        parent::__construct($layout);

        $this->title = 'Coopérative agricole : Nos paniers';

        $this->content = '
        <div class="hero-section">
            <h1>Nos paniers de saison</h1>
            <p>Découvrez nos sélections de fruits et légumes frais</p>
        </div>
        
        <div class="content-blocks">
            <!-- Panier 1 -->
            <div id="paniers" class="content-block">
                <div class="block-bg"></div>
                <div class="block-content">
                    <h2>Panier Petit</h2>
                    <p>Idéal pour 1-2 personnes - Légumes de saison et fruits frais</p>
                    <p class="price">15€</p>
                    <a href="#" class="btn">Commander</a>
                </div>
            </div>
            
            <!-- Panier 2 -->
            <div id="paniers" class="content-block">
                <div class="block-bg"></div>
                <div class="block-content">
                    <h2>Panier Moyen</h2>
                    <p>Parfait pour une famille - Grande variété de produits</p>
                    <p class="price">25€</p>
                    <a href="#" class="btn">Commander</a>
                </div>
            </div>
            
            <!-- Panier 3 -->
            <div id="paniers" class="content-block">
                <div class="block-bg"></div>
                <div class="block-content">
                    <h2>Panier Gourmet</h2>
                    <p>Produits premium et spécialités locales</p>
                    <p class="price">40€</p>
                    <a href="#" class="btn">Commander</a>
                </div>
            </div>
        </div>';
    }
}