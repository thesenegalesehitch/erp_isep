# 🎓 ISEP Platform - SaaS de Communication pour Établissements d'Enseignement

## 📋 Description du Projet

ISEP Platform est une solution SaaS B2B conçue pour les établissements d'enseignement supérieur en Afrique de l'Ouest. Notre plateforme permet aux écoles, universités et centres de formation de gérer leur communication interne, leurs activités et d'analyser leurs performances via un tableau de bord analytics complet.

## 🎯 Modèle Économique

### Abonnements B2B
- **Basic** : 50€/mois - Jusqu'à 200 étudiants
- **Premium** : 100€/mois - Jusqu'à 500 étudiants  
- **Enterprise** : 200€/mois - Jusqu'à 2000 étudiants

### Marché Cible
- Écoles privées et publiques en Afrique de l'Ouest
- Centres de formation professionnelle
- Universités privées
- Instituts spécialisés

## 🚀 Fonctionnalités Principales

### 1. Gestion Multi-Établissements
- ✅ Création et administration de plusieurs écoles
- ✅ Gestion des abonnements et facturation
- ✅ Contrôle d'accès par établissement
- ✅ Analytics par école et global

### 2. Communication Interne
- ✅ Messagerie instantanée individuelle et de groupe
- ✅ Forums de discussion par spécialité/programme
- ✅ Annonces administratives avec priorités
- ✅ Notifications en temps réel

### 3. Gestion d'Activités
- ✅ Calendrier interactif des événements
- ✅ Création et gestion d'activités (cours, examens, ateliers)
- ✅ Inscription en ligne avec gestion des places
- ✅ Rappels automatiques

### 4. Analytics pour Directions
- ✅ Tableau de bord de performance
- ✅ Métriques d'engagement des utilisateurs
- ✅ Statistiques d'utilisation par fonctionnalité
- ✅ Rapports de croissance et tendances

### 5. Administration Centralisée
- ✅ Gestion des utilisateurs et rôles
- ✅ Modération de contenu
- ✅ Export de données
- ✅ Sécurité multi-niveaux

## 🛠️ Technologies

### Backend
- **Framework** : Spring Boot 3.x
- **Sécurité** : Spring Security + JWT
- **ORM** : JPA/Hibernate
- **WebSocket** : Spring WebSocket pour messagerie temps réel
- **Cache** : Redis pour sessions et cache
- **Base de données** : PostgreSQL (principal) + Redis (cache)

### Frontend Web
- **Framework** : React.js 18+ avec TypeScript
- **UI Framework** : Material-UI v5
- **State Management** : Zustand
- **WebSocket Client** : Socket.IO Client
- **Design** : Responsive et Mobile-First

### Infrastructure
- **Base de données** : PostgreSQL
- **Cache** : Redis
- **File Storage** : Cloudinary/AWS S3
- **CI/CD** : GitHub Actions
- **Monitoring** : Analytics intégré

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
├── frontend-web/           # Application React
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── store/
│   │   └── utils/
│   └── package.json
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

### Installation Frontend
```bash
cd frontend-web
npm install
npm run dev
```

### Docker Compose (Tout en un)
```bash
docker-compose up -d
```

## 💡 Modèle d'Affaires

### Revenus Récurrents (MRR/ARR)
- **Abonnements mensuels** : 50-200€ par école
- **Target initial** : 100 écoles en 12 mois
- **Potentiel** : 10k-20k€/mois récurrents
- **Expansion** : 500 écoles en 24 mois

### Avantages Concurrentiels
- Solution locale adaptée au contexte africain
- Prix compétitif vs solutions occidentales
- Support en français et langues locales
- Focus sur les besoins spécifiques des écoles ouest-africaines

### Stratégie de Go-to-Market
- **Phase 1** : Pilote avec 10 écoles au Sénégal
- **Phase 2** : Expansion au Mali, Burkina, Côte d'Ivoire
- **Phase 3** : Toute l'Afrique de l'Ouest

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

- [x] Architecture SaaS B2B définie
- [x] Backend API avec gestion multi-écoles
- [x] Frontend Web responsive
- [x] Modèle d'abonnement implémenté
- [x] Analytics pour directions
- [x] Tests unitaires et d'intégration
- [ ] Documentation complète
- [ ] Déploiement production

## 🤝 Contribution

Ce projet est développé comme une solution SaaS commerciale. Pour les partenariats ou collaborations, voir [CONTRIBUTING.md](./CONTRIBUTING.md)

## 📝 Licence

Propriétaire - ISEP Platform © 2026

---

**Version** : 2.0.0 - Pivot B2B SaaS  
**Dernière mise à jour** : 2026-02-18

## 🎯 Pivot Stratégique Réussi

**Avant le pivot** : Application étudiante mono-école sans modèle économique  
**Après le pivot** : Solution SaaS B2B multi-écoles avec revenus récurrents

**Changements majeurs** :
- ❌ Suppression des services étudiants et bus tracking
- ❌ Retrait de l'application mobile Flutter
- ✅ Ajout du modèle d'abonnement B2B
- ✅ Support multi-établissements
- ✅ Analytics avancés pour directions
- ✅ Focus sur le marché ouest-africain

**Nouveau potentiel** : 100k-200k€ ARR en 24 mois

