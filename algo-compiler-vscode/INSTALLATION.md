# 🚀 Guide d'Installation - Extension Algo Compiler VS Code

## Étape 1: Installer Node.js

Si ce n'est pas déjà fait, téléchargez et installez **Node.js** depuis [nodejs.org](https://nodejs.org/)

Vérifiez l'installation:
```bash
node --version
npm --version
```

## Étape 2: Compiler le projet Algo Compiler

Depuis le dossier racine `Algo-compiler`:

```bash
cd c:\Users\Omar\Documents\Algo-compiler
.\gradlew build
```

Ceci génère le fichier JAR dans: `build/libs/algo-compiler-1.0.0.jar`

## Étape 3: Installer les dépendances de l'extension

```bash
cd algo-compiler-vscode
npm install
```

## Étape 4: Compiler l'extension TypeScript

```bash
npm run compile
```

## Étape 5: Tester l'extension

### Option A: Mode Développement (Recommandé pour tester)

1. Ouvrir le dossier `algo-compiler-vscode` dans VS Code
2. Appuyer sur `F5` pour lancer une nouvelle fenêtre VS Code avec l'extension chargée
3. Dans la nouvelle fenêtre, ouvrir un fichier `.algo` ou en créer un nouveau
4. Tester les fonctionnalités:
   - Coloration syntaxique
   - Snippets (taper `algo` puis Tab)
   - Exécution (`Ctrl+Shift+R`)

### Option B: Installer comme extension locale

1. Compiler l'extension en fichier VSIX:
   ```bash
   npm run package
   ```

2. Installer le fichier généré:
   - Dans VS Code: `Ctrl+Shift+P`
   - Taper: `Extensions: Install from VSIX...`
   - Sélectionner le fichier `algo-compiler-1.0.0.vsix`

## Étape 6: Configuration (Optionnel)

Ouvrir les paramètres VS Code (`Ctrl+,`) et rechercher "algo":

```json
{
    "algoCompiler.compilerPath": "C:/Users/Omar/Documents/Algo-compiler/build/libs/algo-compiler-1.0.0.jar",
    "algoCompiler.javaPath": "java",
    "algoCompiler.clearOutputBeforeRun": true,
    "algoCompiler.showExecutionTime": true
}
```

## Étape 7: Tester avec un exemple

1. Créer un nouveau fichier `test.algo`
2. Écrire:
   ```algo
   algorithme Test
   
   variables
       x : entier
   
   début
       x = 42
       écrire("Résultat:", x)
   fin
   ```

3. Appuyer sur `Ctrl+Shift+R` pour exécuter
4. Voir le résultat dans le terminal de sortie

## 🎉 C'est terminé!

Votre extension est maintenant prête à l'emploi!

## 📝 Commandes utiles

| Commande | Description |
|----------|-------------|
| `npm install` | Installer les dépendances |
| `npm run compile` | Compiler TypeScript → JavaScript |
| `npm run watch` | Compiler en mode watch (auto-recompile) |
| `npm run package` | Créer le fichier VSIX |
| `F5` dans VS Code | Lancer en mode debug |

## 🐛 Problèmes courants

### "Cannot find module 'vscode'"
```bash
npm install @types/vscode --save-dev
```

### "tsc command not found"
```bash
npm install -g typescript
```

### Le compilateur n'est pas trouvé
Vérifiez que le JAR existe:
```bash
dir "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar"
```

Si absent, recompiler:
```bash
cd C:\Users\Omar\Documents\Algo-compiler
.\gradlew clean build
```

## 🔄 Mettre à jour l'extension

Après modification du code:

1. **Mode développement**: Rechargez la fenêtre (`Ctrl+R` dans la fenêtre de test)
2. **Extension installée**: Recompilez et réinstallez:
   ```bash
   npm run compile
   npm run package
   ```
   Puis réinstaller le VSIX

## 📚 Structure du projet

```
algo-compiler-vscode/
├── src/
│   └── extension.ts          # Code principal
├── syntaxes/
│   └── algo.tmLanguage.json  # Grammaire de coloration
├── snippets/
│   └── algo.json             # Snippets
├── images/
│   └── icon.png              # Icône
├── package.json              # Manifest de l'extension
├── tsconfig.json             # Configuration TypeScript
└── README.md                 # Documentation
```

## 🎯 Prochaines étapes

- ✅ Tester tous les exemples dans `examples/`
- ✅ Personnaliser les snippets
- ✅ Ajouter des raccourcis clavier personnalisés
- ✅ Partager l'extension avec d'autres utilisateurs

---

**Bon développement! 🚀**
