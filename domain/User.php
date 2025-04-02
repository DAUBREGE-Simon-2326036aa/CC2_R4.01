<?php

namespace domain;

/**
 * Classe User - Représente un utilisateur de l'application
 *
 * Cette classe encapsule les données et comportements d'un utilisateur
 * avec ses propriétés de base et méthodes d'accès.
 */
class User
{
    /**
     * Identifiant de connexion de l'utilisateur
     * @var string
     */
    protected $login;

    /**
     * Mot de passe de l'utilisateur (devrait être hashé en production)
     * @var string
     */
    protected $password;

    /**
     * Nom de famille de l'utilisateur
     * @var string
     */
    protected $name;

    /**
     * Prénom de l'utilisateur
     * @var string
     */
    protected $firstName;

    /**
     * Date associée à l'utilisateur (création/modification)
     * @var mixed
     */
    protected $date;

    /**
     * Constructeur de la classe User
     *
     * @param string $login Identifiant de connexion
     * @param string $password Mot de passe
     * @param string $name Nom de famille
     * @param string $firstName Prénom
     * @param mixed $date Date associée
     */
    public function __construct($login, $password, $name, $firstName, $date)
    {
        $this->login = $login;
        $this->password = $password;
        $this->name = $name;
        $this->firstName = $firstName;
        $this->date = $date;
    }

    /**
     * Récupère l'identifiant de connexion de l'utilisateur
     *
     * @return string L'identifiant de connexion
     */
    public function getLogin()
    {
        return $this->login;
    }
}