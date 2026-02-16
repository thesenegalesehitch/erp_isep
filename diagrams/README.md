# 📊 Diagrammes PlantUML - ISEP Platform

Ce dossier contient tous les diagrammes PlantUML pour le projet ISEP Platform.

## 📁 Fichiers disponibles

### 1. Diagramme de Classe (`01-diagramme-classe.puml`)
- Représente toutes les entités du modèle de données
- Affiche les relations entre les classes (OneToMany, ManyToOne, etc.)
- Inclut les principaux attributs et méthodes de chaque modèle
- Montre les enums et statuts possibles

### 2. Diagramme de Cas d'Utilisation (`02-diagramme-cas-utilisation.puml`)
- Représente tous les cas d'utilisation par rôle
- Organisé par packages fonctionnels :
  - Authentification
  - Services Étudiants
  - Messagerie
  - Bus de Ramassage
  - Calendrier et Activités
  - Actualités
- Couleurs distinctes pour chaque rôle

### 3. Diagramme de Séquence - Inscription (`03-sequence-inscription.puml`)
- Flux complet du processus d'inscription
- Étapes : Validation → Vérification → Création → Génération JWT
- Inclut les interactions avec la base de données
- Montre la gestion des erreurs

### 4. Diagramme de Séquence - Connexion (`04-sequence-connexion.puml`)
- Flux complet du processus de connexion
- Authentification Spring Security
- Génération de tokens JWT
- Gestion des erreurs d'authentification

## 🚀 Utilisation

Voir le README principal pour les instructions d'utilisation des diagrammes PlantUML.

---

*Générés le 2025-11-02*

