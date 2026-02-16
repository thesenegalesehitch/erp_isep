# 🤝 Guide de Contribution - ISEP Platform

## 📋 Processus de Contribution

### 1. Fork et Clone
```bash
git clone https://github.com/isep/platform.git
cd platform
```

### 2. Créer une Branche
```bash
git checkout -b feature/nom-de-la-fonctionnalite
# ou
git checkout -b fix/nom-du-bug
```

### 3. Développement
- Suivre les conventions de code
- Écrire des tests
- Documenter le code

### 4. Commit
```bash
git commit -m "feat: ajout fonctionnalité X"
# Types: feat, fix, docs, style, refactor, test, chore
```

### 5. Push et Pull Request
```bash
git push origin feature/nom-de-la-fonctionnalite
```

## 📝 Conventions de Code

### Backend (Java)
- **Naming** : camelCase pour variables/méthodes, PascalCase pour classes
- **Indentation** : 4 espaces
- **Documentation** : JavaDoc pour toutes les méthodes publiques

### Frontend (JavaScript/TypeScript)
- **Naming** : camelCase
- **Indentation** : 2 espaces
- **Composants** : PascalCase pour les composants

### Mobile (Dart/JavaScript)
- Suivre les conventions Flutter/React Native

## ✅ Checklist avant PR

- [ ] Code compilé sans erreurs
- [ ] Tests passent
- [ ] Documentation à jour
- [ ] Conventions respectées
- [ ] Pas de logs de debug
- [ ] Code review effectué

## 🐛 Rapport de Bug

Utiliser le template GitHub Issues avec :
- Description du bug
- Steps to reproduce
- Comportement attendu
- Screenshots si applicable

## 💡 Suggestions

Les suggestions sont les bienvenues via GitHub Discussions ou Issues.

---

**Merci pour votre contribution !** 🎉

