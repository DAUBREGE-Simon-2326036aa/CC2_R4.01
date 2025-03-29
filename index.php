<?php

// charge et initialise les bibliothèques globales
include_once 'data/AnnonceSqlAccess.php';
include_once 'data/UserSqlAccess.php';

include_once 'control/Controllers.php';
include_once 'control/Presenter.php';

include_once 'service/AnnoncesChecking.php';
include_once 'service/UserChecking.php';
include_once 'service/UserCreation.php';

include_once 'gui/Layout.php';
include_once 'gui/ViewLogin.php';
include_once 'gui/ViewAnnonces.php';
include_once 'gui/ViewPost.php';
include_once 'gui/ViewError.php';
include_once 'gui/ViewCreate.php';
include_once 'gui/ViewAccueil.php';

use gui\{ViewAccueil, ViewLogin, ViewAnnonces, ViewPost, ViewError, ViewCreate, Layout};
use control\{Controllers, Presenter};
use data\{AnnonceSqlAccess, UserSqlAccess};
use service\{AnnoncesChecking, UserChecking, UserCreation};


// initialisation du controller
$controller = new Controllers();

// intialisation du cas d'utilisation service\AnnoncesChecking
$annoncesCheck = new AnnoncesChecking() ;

// intialisation du cas d'utilisation service\UserChecking
$userCheck = new UserChecking() ;

// intialisation du cas d'utilisation service\UserCreation
$userCreation = new UserCreation() ;

// intialisation du presenter avec accès aux données de AnnoncesCheking
$presenter = new Presenter($annoncesCheck);

// chemin de l'URL demandée au navigateur
// (p.ex. /index.php)
$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);

// définition d'une session d'une heure
ini_set('session.gc_maxlifetime', 3600);
session_set_cookie_params(3600);
session_start();

// Authentification et création du compte (sauf pour le formulaire de connexion et de création de compte)
if ( '/' != $uri and '/index.php' != $uri and '/index.php/logout' != $uri  and '/index.php/create' != $uri){

    $error = $controller->authenticateAction($userCreation, $userCheck, $dataUsers);

    if( $error != null )
    {
        $uri='/index.php/error' ;
        if( $error == 'bad login or pwd' or $error == 'not connected')
            $redirect = '/index.php';

        if( $error == 'creation impossible')
            $redirect = '/index.php/create';
    }
}

// route la requête en interne
// i.e. lance le bon contrôleur en fonction de la requête effectuée
if ( '/' == $uri || '/index.php' == $uri || '/index.php/logout' == $uri) {
    // affichage de la page de connexion

    session_destroy();
    $layout = new Layout("gui/layout.html" );
    $vueAccueil = new ViewAccueil( $layout );

    $vueAccueil->display();
}
elseif ( '/index.php/create' == $uri ) {
    // Affichage du fromulaire de création de compte

    $layout = new Layout("gui/layout.html" );
    $vueCreate = new ViewCreate( $layout );

    $vueCreate->display();
}
elseif ( '/index.php/annonces' == $uri ){
    // affichage de toutes les annonces

    $controller->annoncesAction($dataAnnonces, $annoncesCheck);

    $layout = new Layout("gui/layout.html" );
    $vueAnnonces= new ViewAnnonces( $layout,  $_SESSION['login'], $presenter);

    $vueAnnonces->display();
}
elseif ( '/index.php/post' == $uri
            && isset($_GET['id'])) {
    // Affichage d'une annonce

    $controller->postAction($_GET['id'], $dataAnnonces, $annoncesCheck);

    $layout = new Layout("gui/layout.html" );
    $vuePost= new ViewPost( $layout,  $_SESSION['login'], $presenter );

    $vuePost->display();
}
elseif ( '/index.php/error' == $uri ){
    // Affichage d'un message d'erreur

    $layout = new Layout("gui/layout.html" );
    $vueError = new ViewError( $layout, $error, $redirect );

    $vueError->display();
}
else {
    header('Status: 404 Not Found');
    echo '<html><body><h1>My Page NotFound</h1></body></html>';
}

?>
