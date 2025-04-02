# ApiPanier2

ApiPanier est une API REST permettant la gestion de paniers d'achats pour une application e-commerce.

## Description

Cette API permet de gérer des paniers d'achats avec les fonctionnalités suivantes:
- Récupérer la liste de tous les paniers
- Récupérer les informations d'un panier spécifique via sa référence
- Mettre à jour le statut et les informations d'un panier
- Gérer les lignes de panier (ajouter, modifier, supprimer)
- Accéder au catalogue de produits via une API externe
- Ajouter directement des produits au panier
- Afficher les détails des produits dans les paniers

## Prérequis

- Java 22
- Maven 3.9+
- Serveur d'application compatible Jakarta EE (GlassFish 7.0+)


## Structure des données

### Panier
Un panier est représenté par:
- `id`: Identifiant unique du panier
- `nom`: Nom du panier
- `description`: Description du panier
- `gestionnaireCreationId`: ID du gestionnaire qui a créé le panier
- `prixTotal`: Prix total du panier
- `estValide`: Indicateur si le panier est validé
- `derniereMaj`: Date de dernière modification
- `lignes`: Liste des lignes du panier

### LignePanier
Une ligne de panier est représentée par:
- `id`: Identifiant unique de la ligne
- `panierId`: ID du panier associé
- `produitId`: ID du produit
- `quantite`: Quantité du produit
- `prixUnitaire`: Prix unitaire du produit
- `sousTotal`: Sous-total calculé (quantité * prix unitaire)

### Produit
Un produit est représenté par:
- `id`: Identifiant unique du produit
- `nom`: Nom du produit
- `prix`: Prix du produit
- `prixCategorie`: Catégorie de prix (A_L_UNITE, AU_KILO, A_LA_DOUZAINE)
- `typeProduit`: Type de produit (LAITERIE, OEUFS, FRUIT, LEGUME, EPICERIE, BOISSON, BOULANGERIE)

## Endpoints de l'API

### Gestion des paniers

#### Obtenir tous les paniers
```
GET /api/paniers
```
Retourne un tableau JSON de tous les paniers disponibles (format simplifié).

#### Obtenir tous les paniers complets avec leurs lignes
```
GET /api/paniers/complet
```
Retourne un tableau JSON de tous les paniers disponibles avec leurs lignes.

#### Obtenir un panier spécifique par référence (format simplifié)
```
GET /api/paniers/{reference}
```
Retourne les informations d'un panier spécifique au format JSON.

#### Obtenir un panier spécifique par ID (format complet)
```
GET /api/paniers/id/{id}
```
Retourne les informations d'un panier spécifique avec ses lignes au format JSON.

#### Obtenir les lignes d'un panier
```
GET /api/paniers/id/{id}/lignes
```
Retourne les lignes d'un panier spécifique au format JSON.

#### Créer un panier
```
POST /api/paniers/create
Content-Type: application/json

{
  "nom": "Panier de fruits",
  "description": "Assortiment de fruits frais",
  "gestionnaireCreationId": 1
}
```
Crée un nouveau panier et retourne son ID.

#### Ajouter une ligne à un panier
```
POST /api/paniers/id/{id}/lignes
Content-Type: application/json

{
  "produitId": 101,
  "quantite": 2.5,
  "prixUnitaire": 1.99
}
```
Ajoute une ligne à un panier existant et retourne l'ID de la ligne.

#### Mettre à jour un panier (mode simplifié)
```
PUT /api/paniers/{reference}
Content-Type: application/json

{
  "client": "nom_client",
  "articles": "liste_articles",
  "status": "e"
}
```
Met à jour les informations d'un panier existant (format simplifié).

#### Mettre à jour un panier (mode complet)
```
PUT /api/paniers/id/{id}
Content-Type: application/json

{
  "nom": "Panier de fruits modifié",
  "description": "Description mise à jour",
  "gestionnaireCreationId": 1
}
```
Met à jour les informations d'un panier existant.

#### Mettre à jour une ligne de panier
```
PUT /api/paniers/id/{panierId}/lignes/{ligneId}
Content-Type: application/json

{
  "produitId": 101,
  "quantite": 3.0,
  "prixUnitaire": 1.99
}
```
Met à jour les informations d'une ligne de panier existante.

#### Supprimer un panier
```
DELETE /api/paniers/id/{id}
```
Supprime un panier et toutes ses lignes.

#### Supprimer une ligne de panier
```
DELETE /api/paniers/lignes/{ligneId}
```
Supprime une ligne de panier spécifique.

#### Valider un panier
```
PUT /api/paniers/id/{id}/validate
```
Change le statut d'un panier à validé.

### Gestion des produits (NOUVEAU)

#### Obtenir tous les produits
```
GET /api/products
```
Retourne un tableau JSON de tous les produits disponibles.

#### Obtenir les détails d'un produit
```
GET /api/products/{id}
```
Retourne les informations détaillées d'un produit spécifique.
