# 🏗️ Architecture du Système - ISEP Platform

## 📐 Vue d'Ensemble

Architecture modulaire en trois couches principales :
- **Backend** : API REST + WebSocket
- **Frontend Web** : Application React/Vue
- **Mobile** : Application Flutter/React Native

## 🎯 Architecture Générale

```
┌─────────────────────────────────────────────────────────────┐
│                    Clients (Multi-platform)                  │
├─────────────────┬───────────────────┬───────────────────────┤
│  Web Browser    │  Android App      │  iOS App              │
│  (React/Vue)    │  (Flutter/RN)     │  (Flutter/RN)         │
└────────┬────────┴──────────┬─────────┴──────────┬────────────┘
         │                   │                    │
         │  HTTPS/WSS         │  HTTPS/WSS         │  HTTPS/WSS
         │                   │                    │
┌────────▼───────────────────▼────────────────────▼──────────┐
│                    API Gateway / Load Balancer                │
│                    (Nginx / AWS ALB)                          │
└────────┬───────────────────────────────────────────────────────┘
         │
┌────────▼─────────────────────────────────────────────────────┐
│                    Backend Services (Spring Boot)             │
├───────────────────────────────────────────────────────────────┤
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │   Auth      │  │  Messaging   │  │   Services   │       │
│  │  Service    │  │   Service    │  │   Service    │       │
│  └──────────────┘  └──────────────┘  └──────────────┘       │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │   Bus       │  │  Calendar    │  │  Notifications│       │
│  │  Service    │  │   Service    │  │   Service    │       │
│  └──────────────┘  └──────────────┘  └──────────────┘       │
└────────┬───────────────────────────────────────────────────────┘
         │
┌────────▼─────────────────────────────────────────────────────┐
│                    Data Layer                                 │
├──────────────────────┬───────────────────┬───────────────────┤
│   PostgreSQL         │   Redis Cache     │   File Storage     │
│   (Main DB)          │   (Sessions/Cache)│   (S3/Cloudinary) │
└──────────────────────┴───────────────────┴───────────────────┘
```

## 🔧 Backend Architecture (Spring Boot)

### Structure Modulaire

```
backend/
├── src/main/java/com/isep/
│   ├── IsepApplication.java
│   │
│   ├── config/              # Configuration
│   │   ├── SecurityConfig
│   │   ├── WebSocketConfig
│   │   ├── RedisConfig
│   │   └── JpaConfig
│   │
│   ├── model/               # Entités JPA
│   │   ├── User
│   │   ├── Service
│   │   ├── Message
│   │   ├── Bus
│   │   ├── Activity
│   │   └── Announcement
│   │
│   ├── repository/          # Repositories Spring Data
│   │   ├── UserRepository
│   │   ├── ServiceRepository
│   │   └── ...
│   │
│   ├── service/             # Logique métier
│   │   ├── AuthService
│   │   ├── MessagingService
│   │   ├── ServiceService
│   │   ├── BusTrackingService
│   │   └── NotificationService
│   │
│   ├── controller/          # Contrôleurs REST
│   │   ├── AuthController
│   │   ├── MessageController
│   │   ├── ServiceController
│   │   └── ...
│   │
│   ├── security/            # Sécurité
│   │   ├── JwtTokenProvider
│   │   ├── JwtAuthenticationFilter
│   │   └── UserDetailsServiceImpl
│   │
│   ├── dto/                 # Data Transfer Objects
│   │   ├── UserDTO
│   │   ├── ServiceDTO
│   │   └── ...
│   │
│   ├── websocket/           # WebSocket
│   │   ├── ChatWebSocketHandler
│   │   └── BusTrackingHandler
│   │
│   └── exception/           # Gestion d'erreurs
│       ├── GlobalExceptionHandler
│       └── CustomException
│
└── src/main/resources/
    ├── application.yml
    ├── application-dev.yml
    ├── application-prod.yml
    └── db/migration/        # Flyway migrations
```

### Patterns Utilisés

1. **Repository Pattern** : Abstraction de l'accès aux données
2. **Service Layer** : Logique métier isolée
3. **DTO Pattern** : Transfert de données optimisé
4. **Factory Pattern** : Création d'objets complexes
5. **Observer Pattern** : Notifications et événements

## 🌐 Frontend Architecture (React/Vue)

### Structure React

```
frontend-web/
├── public/
├── src/
│   ├── components/          # Composants réutilisables
│   │   ├── common/
│   │   ├── forms/
│   │   ├── layout/
│   │   └── ui/
│   │
│   ├── pages/              # Pages de l'application
│   │   ├── auth/
│   │   ├── dashboard/
│   │   ├── services/
│   │   ├── messaging/
│   │   └── calendar/
│   │
│   ├── services/           # Services API
│   │   ├── api/
│   │   ├── websocket/
│   │   └── storage/
│   │
│   ├── store/              # State management (Redux/Pinia)
│   │   ├── slices/
│   │   └── actions/
│   │
│   ├── hooks/              # Custom hooks
│   ├── utils/              # Utilitaires
│   ├── constants/          # Constantes
│   └── App.jsx/tsx
│
└── package.json
```

### Architecture Mobile (Flutter)

```
mobile/
├── lib/
│   ├── main.dart
│   │
│   ├── models/             # Modèles de données
│   │   ├── user.dart
│   │   ├── service.dart
│   │   └── ...
│   │
│   ├── services/            # Services
│   │   ├── api_service.dart
│   │   ├── websocket_service.dart
│   │   └── notification_service.dart
│   │
│   ├── screens/            # Écrans
│   │   ├── auth/
│   │   ├── dashboard/
│   │   ├── services/
│   │   └── messaging/
│   │
│   ├── widgets/           # Widgets réutilisables
│   │   ├── common/
│   │   └── forms/
│   │
│   ├── providers/         # State management (Provider/Riverpod)
│   │   └── ...
│   │
│   └── utils/             # Utilitaires
│
└── pubspec.yaml
```

## 🔌 Communication Inter-Services

### REST API
- **Format** : JSON
- **Authentification** : JWT Bearer Token
- **Versioning** : `/api/v1/`
- **Documentation** : Swagger/OpenAPI

### WebSocket
- **Protocole** : STOMP over WebSocket
- **Authentification** : JWT via handshake
- **Use cases** :
  - Messagerie temps réel
  - Suivi bus temps réel
  - Notifications push

## 💾 Base de Données

### PostgreSQL (Principal)

#### Schéma Principal
```sql
-- Tables principales
users
services
messages
conversations
bus_tracking
bus_reservations
activities
activity_registrations
announcements
forums
forum_posts
```

### Redis (Cache)
- **Sessions utilisateurs**
- **Cache de données fréquentes**
- **Pub/Sub pour WebSocket**
- **Rate limiting**

## 🔐 Sécurité

### Authentification
- JWT avec refresh tokens
- Expiration automatique
- Blacklist pour déconnexion

### Autorisation
- RBAC (Role-Based Access Control)
- Permissions granulaires
- Validation côté serveur

### Protection
- CORS configuré
- CSRF protection
- Rate limiting
- Input validation
- SQL injection prevention (JPA)

## 📊 Monitoring et Logs

### Logging
- **Backend** : Logback/SLF4J
- **Frontend** : Console + Sentry
- **Format** : JSON structuré

### Monitoring
- **Health checks** : `/actuator/health`
- **Metrics** : Prometheus
- **APM** : Application Performance Monitoring

## 🚀 Déploiement

### CI/CD Pipeline
```
Git Push → GitHub Actions
  ↓
Build & Test
  ↓
Docker Image
  ↓
Deploy to Staging
  ↓
Tests E2E
  ↓
Deploy to Production
```

### Containers
- **Backend** : Spring Boot JAR
- **Frontend** : Nginx serve static files
- **Database** : PostgreSQL container
- **Cache** : Redis container

---

**Version** : 1.0.0  
**Date** : 2025-11-02

