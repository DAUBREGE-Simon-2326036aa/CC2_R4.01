<?php
namespace control;

/**
 * Classe Controllers - Gère les actions du contrôleur
 *
 * Cette classe contient les méthodes pour :
 * - Gérer l'authentification des utilisateurs
 * - Gérer les actions liées aux annonces
 */
class Controllers
{
    /**
     * Gère l'authentification et la création de compte utilisateur
     *
     * @param mixed $userCreation Service de création d'utilisateur
     * @param mixed $userCheck Service de vérification d'utilisateur
     * @param mixed $dataUsers Données des utilisateurs
     * @return string|null Message d'erreur ou null si succès
     */
    public function authenticateAction($userCreation, $userCheck, $dataUsers)
    {
        // Si l'utilisateur n'a pas de session ouverte
        if (!isset($_SESSION['login'])) {

            // Vérifie si la requête provient d'un formulaire de connexion/création
            if (isset($_POST['login']) && isset($_POST['password'])) {

                // Cas de la création de compte
                if (isset($_POST['name']) && isset($_POST['firstName'])) {
                    if (!$userCreation->createUser(
                        $_POST['login'],
                        $_POST['password'],
                        $_POST['name'],
                        $_POST['firstName'],
                        $dataUsers
                    )) {
                        return 'creation impossible'; // Échec création compte
                    }

                    // Succès création compte
                    $_SESSION['login'] = $_POST['login'];
                }
                // Cas de la connexion simple
                else {
                    if (!$userCheck->authenticate(
                        $_POST['login'],
                        $_POST['password'],
                        $dataUsers
                    )) {
                        return 'bad login or pwd'; // Échec authentification
                    }

                    // Succès authentification
                    $_SESSION['login'] = $_POST['login'];
                }
            } else {
                return 'not connected'; // Accès non autorisé
            }
        }

        return null; // Tout s'est bien passé
    }

    /**
     * Récupère toutes les annonces
     *
     * @param mixed $data Données des annonces
     * @param mixed $annoncesCheck Service de gestion des annonces
     */
    public function annoncesAction($data, $annoncesCheck)
    {
        $annoncesCheck->getAllAnnonces($data);
    }

    /**
     * Récupère une annonce spécifique
     *
     * @param int $id Identifiant de l'annonce
     * @param mixed $data Données des annonces
     * @param mixed $annoncesCheck Service de gestion des annonces
     */
    public function postAction($id, $data, $annoncesCheck)
    {
        $annoncesCheck->getPost($id, $data);
    }
}