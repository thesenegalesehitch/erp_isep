# 🎓 ISEP ERP - Système de Gestion Complet pour Établissements d'Enseignement

## 📋 Description du Projet

ISEP ERP est une solution ERP modulable révolutionnaire conçue pour les établissements d'enseignement supérieur en Afrique de l'Ouest. Notre plateforme s'adapte à tous les types d'établissements : universités publiques, universités privées, instituts professionnels, écoles techniques et écoles de commerce.

## 🎯 Modèle Économique (Open Source Gratuit)

### Licence et Accès
- **100% GRATUIT** : Tous les établissements peuvent utiliser l'ERP gratuitement
- **Open Source** : Code source disponible sur GitHub
- **Sans restriction** : Pas de limites d'étudiants ou de fonctionnalités
- **Communauté** : Contribution et amélioration collaborative

### Services Complémentaires (Optionnel)
- **Support technique** : Formation et assistance personnalisée
- **Hébergement cloud** : Solution managée pour les établissements
- **Développement sur mesure** : Modules spécifiques sur demande
- **Formation utilisateur** : Sessions de formation pour le personnel

### Avantages du Modèle Gratuit
- **Adoption maximale** : Aucune barrière financière
- **Impact social** : Accessible à tous les établissements
- **Communauté active** : Développement collaboratif
- **Innovation ouverte** : Contributions de tous

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

## 💡 Modèle d'Affaires Open Source

### Impact et Accessibilité
- **Adoption illimitée** : Tous les établissements peuvent déployer gratuitement
- **Aucune restriction** : Nombre illimité d'étudiants et de fonctionnalités
- **Déploiement autonome** : Les établissements hébergent leur propre instance
- **Support communautaire** : Documentation complète et forums d'entraide

### Avantages Concurrentiels
- **Gratuité totale** : Alternative aux solutions commerciales coûteuses
- **Modulaire** : Configuration par type d'établissement
- **Localisé** : Adapté au contexte sénégalais et ouest-africain
- **Scalable** : De l'institut professionnel à l'université complète
- **Intégré** : Tous les modules sur une seule plateforme
- **Customisable** : Code source ouvert pour adaptations locales

### Stratégie de Déploiement
- **Phase 1** : ISEP comme premier client pilote
- **Phase 2** : 20 établissements partenaires (formation gratuite)
- **Phase 3** : Publication open source sur GitHub
- **Phase 4** : Adoption nationale et régionale
- **Phase 5** : Communauté de contributeurs mondiaux

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


## 🤝 Contribuer

Nous welcome toutes les contributions ! Consultez le guide [CONTRIBUTING.md](./CONTRIBUTING.md) pour savoir comment :
- Reporter des bugs
- Proposer des améliorations
- Soumettre du code
- Traduire l'interface
- Partager la documentation

## 📄 Licence

Ce projet est sous licence **MIT** - Voir le fichier [LICENSE](LICENSE) pour plus de détails.

**Vous pouvez utiliser, modifier et distribuer ce ERP gratuitement pour tous vos établissements !**

## 🙏 Remerciements

- ISEP - Institut Supérieur d'Enseignement Professionnel (Premier client pilote)
- Ministère de l'Enseignement Supérieur du Sénégal
- Tous les établissements partenaires pour leurs retours précieux

