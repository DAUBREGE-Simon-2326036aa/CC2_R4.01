<?php

namespace domain;
class User
{
    protected $login;
    protected $password;
    protected $name;
    protected $firstName;
    protected  $date;

    public function __construct($login, $password, $name, $firstName, $date )
    {
        $this->login = $login;
        $this->password = $password;
        $this->name = $name;
        $this->firstName = $firstName;
        $this->date = $date;
    }

    public function getLogin()
    {
        return $this->login;
    }
}