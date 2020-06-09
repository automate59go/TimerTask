# Projet d'IHM : Projet de mesure du temps passé par tâche
### réalisé par Deroubaix François
[Liens vers le sujet](https://www.iut-info.univ-lille.fr/~casiez/M2105/TP/ProjetTempsParTache/)
# Focntionnement

## Description :

Ce programme a pour bût de calculer le temps passé sur une tâche via une interface graphique.
Chaque tâche peut être créée, éditer, lancer, stopper, supprimer et encore étendue via la création d'une sous-tâche.
Cette application dispose d'un système de sauvegarde ce qui signifie que vous pouvez quitter à tout moment l'application.
Elle dispose aussi d'un System Tray, pour quitter l'application, la fermer ne suffit pas, car elle tourne en fond, pour la quitter allez dans la barre d'icones windows ou mac et clique droit, puis Exit.

## Exécution

> vous devez télécharger le repo, l'extraire, et lancer le Timertask.jar qui est présent

## Organisation du projet

Le projet est séparé en plusieurs parties au niveau du dossier src :

1. package application -> regroupe le main du programme, le fxml de la fenêtre principale et le css de l'application
2. package controller -> regroupe les class qui gère l'application et les évênements
3. package utils -> regroupe toutes les class outils qui permettent de faire fonctionner le projet
4. package views -> regroupe les différentes fenêtre modale

## Ressources utilisées

System tray : https://gist.github.com/jewelsea/e231e89e8d36ef4e5d8a

Icon : https://www.flaticon.com/
