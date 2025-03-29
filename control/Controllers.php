<?php
namespace control;

class Controllers
{

    public function  authenticateAction($userCreation, $userCheck, $dataUsers){

        // Si l'utilisateur n'a pas de session ouverte
        if( !isset($_SESSION['login']) ) {

            // Si la page d'origine est le formulaire de connexion ou de création de compte
            if( isset($_POST['login']) && isset($_POST['password']) )
            {
                // Création du compte si la précédente page était le formulaire de création de compte
                if( isset($_POST['name'])  && isset($_POST['firstName'])  ){
                    if ( ! $userCreation->createUser($_POST['login'], $_POST['password'], $_POST['name'], $_POST['firstName'], $dataUsers) )
                    {
                        // retourne une erreur si la création du compte a échoué
                        $error = 'creation impossible';
                        return $error;
                    }
                    else{
                        // Enregistrement des informations de session si la création du compte a réussi
                        $_SESSION['login'] = $_POST['login'] ;
                    }
                }
                else { // Vérification de l'authentification si la précédente page était le formulaire de connexion
                    if( !$userCheck->authenticate($_POST['login'], $_POST['password'], $dataUsers) )
                    {
                        // retourne une erreur si le compte n'est pas enregistré
                        $error = 'bad login or pwd';
                        return $error;

                    }
                    // Enregistrement des informations de session après une authentification réussie
                    else {
                        $_SESSION['login'] = $_POST['login'] ;
                    }
                }
            }
            else{
                // retourne une erreur si la personne ne passe pas par le forumlaire de création ou de connexion
                $error = 'not connected';
                return $error;
            }

        }
    }

    public function annoncesAction( $data, $annoncesCheck)
    {
            $annoncesCheck->getAllAnnonces($data);
    }

    public function postAction($id, $data, $annoncesCheck)
    {
        $annoncesCheck->getPost($id, $data);
    }
}