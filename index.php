<?php

include_once 'control/Controllers.php';
include_once 'control/Presenter.php';

include_once 'service/AnnoncesChecking.php';
include_once 'service/UserChecking.php';
include_once 'service/UserCreation.php';

include_once 'gui/Layout.php';
include_once 'gui/ViewAnnonces.php';
include_once 'gui/ViewError.php';
include_once 'gui/ViewCreate.php';
include_once 'gui/ViewAccueil.php';
include_once 'gui/ViewPanier.php';

use gui\{ViewAccueil, ViewCreate, Layout, ViewPanier};
use control\{Controllers, Presenter};
use service\{AnnoncesChecking, UserChecking, UserCreation};

// initialisation du controller
$controller = new Controllers();

// intialisation du cas d'utilisation service\AnnoncesChecking
$annoncesCheck = new AnnoncesChecking();

// intialisation du cas d'utilisation service\UserChecking
$userCheck = new UserChecking();

// intialisation du cas d'utilisation service\UserCreation
$userCreation = new UserCreation();

// intialisation du presenter avec accès aux données de AnnoncesCheking
$presenter = new Presenter($annoncesCheck);

// chemin de l'URL demandée au navigateur
$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);

// définition d'une session d'une heure
ini_set('session.gc_maxlifetime', 3600);
session_set_cookie_params(3600);
session_start();

// route la requête en interne
if ('/' == $uri || '/index.php' == $uri) {
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $nom = $_POST['nom'] ?? '';
        $password = $_POST['password'] ?? '';

        $data = json_encode([
            'nom' => $nom,
            'password' => $password
        ]);

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

    $layout = new Layout("gui/layout.html");
    $vueAccueil = new ViewAccueil($layout);
    $vueAccueil->display();
}
elseif ('/index.php/create' == $uri) {
    var_dump($_POST);
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $userData = [
            'nom' => $_POST['firstName'],
            'email' => $_POST['email'],
            'password' => $_POST['password']
        ];

        // Appel de l'API
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


    // Affichage du formulaire de création de compte
    $layout = new Layout("gui/layout.html");
    $vueCreate = new ViewCreate($layout);
    $vueCreate->display();
}
elseif ('/index.php/paniers' == $uri) {
    // Récupération des paniers complets depuis l'API
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
else {
    header('Status: 404 Not Found');
    echo '<html><body><h1>My Page NotFound</h1></body></html>';
}
?>