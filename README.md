# 🎓 ISEP Platform - Plateforme de Communication et Services Étudiants

## 📋 Description du Projet

L'Institut Supérieur d'Enseignement Professionnel (ISEP) souhaite développer une application mobile et web dédiée à la gestion de la communication interne au sein de l'établissement, tout en permettant aux étudiants de partager leurs compétences et services professionnels.

## 🎯 Objectifs du Projet

### 1. Communication Interne
- Faciliter les échanges entre étudiants, enseignants et administration
- Messagerie instantanée individuelle et de groupe
- Forums de discussion par spécialité/programme

### 2. Partage de Services Étudiants
- Annuaire des services proposés par les étudiants
- Système de notation et commentaires
- Recherche par domaine de compétence ou localisation

### 3. Gestion d'Informations Pratiques
- Actualités et annonces administratives
- Calendrier des activités scolaires
- Gestion des bus de ramassage avec suivi en temps réel

## 🚀 Fonctionnalités Principales

### 1. Authentification et Gestion des Utilisateurs
- ✅ Inscription avec vérification email ou numéro d'étudiant
- ✅ Authentification avec compte ISEP unique
- ✅ Gestion des rôles (étudiant, enseignant, administration)

### 2. Espace Services Étudiants
- ✅ Publication de services par domaine de compétence
- ✅ Système de notation et commentaires
- ✅ Recherche et filtrage avancé
- ✅ Réservation de services

### 3. Messagerie et Communication
- ✅ Chat individuel en temps réel
- ✅ Chat de groupe
- ✅ Forums par spécialité
- ✅ Notifications push

### 4. Gestion des Bus de Ramassage
- ✅ Suivi en temps réel via carte interactive
- ✅ Inscription aux trajets
- ✅ Notifications d'arrivée
- ✅ Signalement de retards

### 5. Calendrier et Activités
- ✅ Calendrier interactif des événements
- ✅ Inscription en ligne aux activités
- ✅ Notifications push pour rappels
- ✅ Gestion des places disponibles

### 6. Informations Administratives
- ✅ Section dédiée aux annonces
- ✅ Téléchargement de documents
- ✅ Diffusion d'informations importantes

## 🛠️ Technologies

### Backend
- **Framework** : Spring Boot 3.x
- **Sécurité** : Spring Security + JWT
- **ORM** : JPA/Hibernate
- **WebSocket** : Spring WebSocket pour messagerie temps réel
- **Cache** : Redis pour sessions et cache
- **Base de données** : PostgreSQL (principal) + Redis (cache)

### Frontend Web
- **Framework** : React.js 18+ ou Vue.js 3+
- **UI Framework** : Material-UI ou Tailwind CSS
- **State Management** : Redux Toolkit ou Pinia
- **WebSocket Client** : Socket.IO Client

### Application Mobile
- **Framework** : Flutter 3.x ou React Native
- **Push Notifications** : Firebase Cloud Messaging
- **WebSocket** : Socket.IO pour temps réel

### Infrastructure
- **API Maps** : Google Maps API ou OpenStreetMap
- **Notifications** : Firebase Cloud Messaging
- **File Storage** : AWS S3 ou Cloudinary
- **CI/CD** : GitHub Actions ou GitLab CI

## 📁 Structure du Projet

```
isep-platform/
├── backend/                 # API Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/isep/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── repository/
│   │   │   │       ├── model/
│   │   │   │       ├── security/
│   │   │   │       └── config/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── frontend-web/           # Application React/Vue
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── store/
│   │   └── utils/
│   └── package.json
├── mobile/                 # Application Flutter/React Native
│   ├── lib/                # Flutter
│   └── src/                # React Native
├── docs/                   # Documentation
│   ├── architecture/
│   ├── api/
│   └── user-guide/
└── docker/                 # Configuration Docker
    ├── docker-compose.yml
    └── Dockerfile.*
```

## 🚦 Installation

### Prérequis
- Java 17+
- Node.js 18+
- PostgreSQL 14+
- Redis 7+
- Docker & Docker Compose (optionnel)

### Installation Backend
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

### Installation Frontend Web
```bash
cd frontend-web
npm install
npm run dev
```

### Installation Mobile
```bash
cd mobile
# Flutter
flutter pub get
flutter run

# React Native
npm install
npm run android/ios
```

### Docker Compose (Tout en un)
```bash
docker-compose up -d
```

## 📚 Documentation

- [Architecture](./docs/architecture/README.md)
- [API Documentation](./docs/api/README.md)
- [Guide Utilisateur](./docs/user-guide/README.md)
- [Guide Développeur](./docs/developer-guide/README.md)

## 🔐 Sécurité

- Authentification JWT
- Chiffrement des données sensibles
- Rate limiting
- Validation des entrées
- Protection CSRF
- HTTPS obligatoire en production

## 📊 État du Projet

- [ ] Architecture définie
- [ ] Backend API en développement
- [ ] Frontend Web en développement
- [ ] Application Mobile en développement
- [ ] Tests unitaires et d'intégration
- [ ] Documentation complète
- [ ] Déploiement production

## 🤝 Contribution

Ce projet est développé pour l'ISEP. Pour contribuer, voir [CONTRIBUTING.md](./CONTRIBUTING.md)

## 📝 Licence

Propriétaire - ISEP © 2026

---

**Version** : 1.0.0  
**Dernière mise à jour** : 2026-02-16

