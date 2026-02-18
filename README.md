# 🎓 ISEP ERP - Système de Gestion Complet pour Établissements d'Enseignement

## 📋 Description du Projet

ISEP ERP est une solution ERP modulable révolutionnaire conçue pour les établissements d'enseignement supérieur en Afrique de l'Ouest. Notre plateforme s'adapte à tous les types d'établissements : universités publiques, universités privées, instituts professionnels, écoles techniques et écoles de commerce.

## 🎯 Modèle Économique (Pivot ERP)

### Licences Établissements (B2B)
- **PETIT** (<500 étudiants) : 2M FCFA/an
- **MOYEN** (500-2000 étudiants) : 5M FCFA/an  
- **GRAND** (>2000 étudiants) : 10M FCFA/an
- **PERSONNALISÉ** : Sur devis

### Modules Optionnels
- **Gestion des Cités U** : 500k FCFA/module/an
- **Restauration Universitaire** : 500k FCFA/module/an
- **Laboratoires de Recherche** : 500k FCFA/module/an
- **Partenariats Entreprises** : 500k FCFA/module/an

### Services Additionnels
- **Maintenance** : 20% de licence/an
- **Formation** : 1M FCFA/session
- **Hébergement Cloud** : 200k FCFA/mois

## 🚀 Fonctionnalités Principales

### 1. Gestion Académique Core
- ✅ Inscriptions et admissions en ligne
- ✅ Gestion des étudiants et dossiers complets
- ✅ Catalogue de cours et programmes
- ✅ Emploi du temps automatique
- ✅ Suivi des notes et évaluations
- ✅ Bulletins et relevés de notes

### 2. Administration Établissement
- ✅ Configuration multi-établissements
- ✅ Gestion des utilisateurs et permissions
- ✅ Ressources humaines (personnel, enseignants)
- ✅ Finances et comptabilité
- ✅ Infrastructure et équipements
- ✅ Communication interne

### 3. Modules Optionnels

#### Module Cités Universitaires
- ✅ Gestion des résidences et chambres
- ✅ Affectation automatique
- ✅ Suivi des paiements de loyer
- ✅ Maintenance et sécurité

#### Module Restauration
- ✅ Gestion des restaurants universitaires
- ✅ Plans de repas et cartes étudiantes
- ✅ Suivi des stocks et nutrition
- ✅ Paiements et statistiques

#### Module Recherche
- ✅ Gestion des projets de recherche
- ✅ Laboratoires et équipements
- ✅ Publications et brevets
- ✅ Collaborations internationales

#### Module Entreprises
- ✅ Partenariats et conventions
- ✅ Stages et alternance
- ✅ Insertion professionnelle
- ✅ Réseau alumni

### 4. Analytics et Rapports
- ✅ Tableau de bord en temps réel
- ✅ Rapports personnalisables
- ✅ Analytics académiques
- ✅ Indicateurs de performance
- ✅ Export et sauvegarde

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
isep-erp-platform/
├── backend/                 # API Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/isep/
│   │   │   │       ├── controller/
│   │   │   │       │   ├── SchoolController.java
│   │   │   │       │   ├── StudentController.java
│   │   │   │       │   ├── CourseController.java
│   │   │   │       │   ├── EnrollmentController.java
│   │   │   │       │   ├── GradeController.java
│   │   │   │       │   └── ScheduleController.java
│   │   │   │       ├── service/
│   │   │   │       ├── repository/
│   │   │   │       ├── model/
│   │   │   │       │   ├── School.java
│   │   │   │       │   ├── Student.java
│   │   │   │       │   ├── Course.java
│   │   │   │       │   ├── Enrollment.java
│   │   │   │       │   ├── Grade.java
│   │   │   │       │   └── CourseSchedule.java
│   │   │   │       ├── security/
│   │   │   │       └── config/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── frontend-web/           # Application React ERP
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   │   ├── auth/
│   │   │   ├── erp/
│   │   │   │   ├── StudentsPage.jsx
│   │   │   │   ├── CoursesPage.jsx
│   │   │   │   ├── EnrollmentsPage.jsx
│   │   │   │   ├── GradesPage.jsx
│   │   │   │   ├── SchedulePage.jsx
│   │   │   │   ├── SchoolsPage.jsx
│   │   │   │   ├── ReportsPage.jsx
│   │   │   │   └── SettingsPage.jsx
│   │   │   └── DashboardPage.jsx
│   │   ├── services/
│   │   └── utils/
│   └── package.json
├── docs/                   # Documentation
│   ├── architecture/
│   ├── api/
│   ├── user-guide/
│   └── business-plan/
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

## 💡 Modèle d'Affaires ERP

### Revenus Récurrents (MRR/ARR)
- **Licences établissements** : 2-10M FCFA/an par établissement
- **Modules optionnels** : 500k FCFA/module/an
- **Services additionnels** : Maintenance, formation, hébergement
- **Target initial** : 20 établissements pilotes en 12 mois
- **Potentiel** : 200M FCFA/an récurrents
- **Expansion** : 100+ établissements en 24 mois

### Avantages Concurrentiels
- **Modulaire** : Configuration par type d'établissement
- **Localisé** : Adapté au contexte sénégalais et ouest-africain
- **Scalable** : De l'institut professionnel à l'université complète
- **Intégré** : Tous les modules sur une seule plateforme
- **Support local** : Formation et maintenance locale

### Stratégie de Go-to-Market
- **Phase 1** : ISEP comme premier client pilote (gratuit)
- **Phase 2** : 20 établissements partenaires (50% réduction)
- **Phase 3** : Expansion nationale (tarifs complets)
- **Phase 4** : Expansion UEMOA (francophonie)

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

- [x] Architecture ERP modulable définie
- [x] Backend API avec gestion multi-établissements
- [x] Frontend Web responsive pour administration
- [x] Modèles de données ERP core (School, Student, Course, etc.)
- [x] Interface utilisateur complète avec 8 pages ERP
- [x] Configuration modulaire par type d'établissement
- [x] Tests unitaires et d'intégration
- [ ] Documentation complète
- [ ] Déploiement production

## 🎯 Pivot ERP Réussi

**Avant le pivot** : SaaS B2B2C parent-connect sans marché  
**Après le pivot** : ERP B2B pour établissements d'enseignement

**Changements majeurs** :
- ❌ Suppression complète du modèle parent-connect
- ❌ Retrait focus communication parent-école
- ✅ Ajout modèles ERP multi-établissements
- ✅ Système de licences par taille d'établissement
- ✅ Interface d'administration complète
- ✅ Modules optionnels (cités, resto, recherche, entreprises)
- ✅ Focus marché éducation Sénégal/UEMOA

**Nouveau potentiel** : 500M FCFA ARR en 24 mois

---

**Version** : 4.0.0 - Pivot ERP Éducation  
**Dernière mise à jour** : 2026-02-18

## 📞 Contact

- **Email** : contact@isep-erp.sn
- **Téléphone** : +221 33 825 79 25
- **Site Web** : https://isep-erp.sn
- **Adresse** : Avenue Cheikh Anta Diop, Dakar, Sénégal

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 🙏 Remerciements

- ISEP - Institut Supérieur d'Enseignement Professionnel (Premier client pilote)
- Ministère de l'Enseignement Supérieur du Sénégal
- Tous les établissements partenaires pour leurs retours précieux

