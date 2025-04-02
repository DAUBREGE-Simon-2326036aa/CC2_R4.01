<?php

include_once 'control/Controllers.php';
include_once 'control/Presenter.php';

include_once 'gui/Layout.php';
include_once 'gui/ViewError.php';
include_once 'gui/ViewCreate.php';
include_once 'gui/ViewAccueil.php';
include_once 'gui/ViewPanier.php';
include_once 'gui/ViewProduits.php';

use gui\{ViewAccueil, ViewCreate, Layout, ViewPanier, ViewProduits};
use control\{Controllers, Presenter};

/**
 * Initialise le contrôleur principal
 * @var Controllers $controller
 */
$controller = new Controllers();

/**
 * Récupère le chemin de l'URL demandée
 * @var string $uri
 */
$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);

// Configuration de la session
ini_set('session.gc_maxlifetime', 3600);
session_set_cookie_params(3600);
session_start();

/**
 * Routeur principal de l'application
 * Gère les différentes routes et leurs traitements
 */
if ('/' == $uri || '/index.php' == $uri) {
    /**
     * Traitement de la soumission du formulaire de connexion
     */
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $nom = $_POST['nom'] ?? '';
        $password = $_POST['password'] ?? '';

        $data = json_encode([
            'nom' => $nom,
            'password' => $password
        ]);

        /**
         * Appel à l'API d'authentification
         */
        $ch = curl_init('http://localhost:8080/ProduitsEtUtilisateurs-1.0-SNAPSHOT/api/users/auth');
        curl_setopt_array($ch, [
            CURLOPT_POST => true,
            CURLOPT_POSTFIELDS => $data,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_HTTPHEADER => [
                'Content-Type: application/json',
                'Content-Length: ' . strlen($data)
            ]
        ]);

        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);

        if ($httpCode === 200) {
            $result = json_decode($response, true);
            $result = intval($result);
            if($result === -1){
                header('Location: /index.php');
            }
            $_SESSION['login'] = $result;
        }
    }

    /**
     * Affichage de la vue d'accueil
     */
    $layout = new Layout("gui/layout.html");
    $vueAccueil = new ViewAccueil($layout);
    $vueAccueil->display();
}
elseif ('/index.php/create' == $uri) {
    /**
     * Traitement de la création de compte utilisateur
     */
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $userData = [
            'nom' => $_POST['firstName'],
            'email' => $_POST['email'],
            'password' => $_POST['password']
        ];

        /**
         * Appel à l'API de création d'utilisateur
         */
        $apiUrl = 'http://localhost:8080/ProduitsEtUtilisateurs-1.0-SNAPSHOT/api/users/create';
        $ch = curl_init($apiUrl);
        curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($userData));
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);

        header('Location: /index.php');
    }

    /**
     * Affichage du formulaire de création de compte
     */
    $layout = new Layout("gui/layout.html");
    $vueCreate = new ViewCreate($layout);
    $vueCreate->display();
}
elseif ('/index.php/paniers' == $uri) {
    /**
     * Récupération et affichage des paniers
     */
    $ch = curl_init('http://localhost:6140/API-Panier-1.0-SNAPSHOT/api/paniers');
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $response = curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    $paniers = [];
    if ($httpCode === 200) {
        $paniers = json_decode($response, true);
    }

    $layout = new Layout("gui/layout.html");
    $vuePaniers = new ViewPanier($layout, $paniers);
    $vuePaniers->display();
}
elseif ('/index.php/produits' == $uri) {
    /**
     * Récupération et affichage des produits
     */
    $ch = curl_init('http://localhost:8080/ProduitsEtUtilisateurs-1.0-SNAPSHOT/api/products');
    curl_setopt_array($ch, [
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_TIMEOUT => 3,
        CURLOPT_HTTPHEADER => ['Accept: application/json']
    ]);

    $response = curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    $error = curl_error($ch);
    curl_close($ch);

    $produits = [];
    if ($httpCode === 200) {
        $produits = json_decode($response, true) ?: [];
    }

    $layout = new Layout("gui/layout.html");
    $vueProduits = new ViewProduits($layout, $produits);
    $vueProduits->display();
}
else {
    /**
     * Gestion des routes non trouvées
     */
    header('Status: 404 Not Found');
    echo '<html><body><h1>My Page NotFound</h1></body></html>';
}
?>