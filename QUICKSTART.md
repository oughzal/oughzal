# Guide de démarrage rapide

## Compilation

### Option 1: Avec Gradle (recommandé)
```powershell
# Si vous avez Gradle installé
gradle build

# Le JAR sera dans build/libs/algo-compiler-1.0.0.jar
```

### Option 2: Avec le compilateur Kotlin directement
```powershell
# Exécutez le script de compilation
.\compile.bat

# Le JAR sera dans build/libs/algo-compiler.jar
```

### Option 3: Compiler manuellement
```powershell
kotlinc src\main\kotlin\com\algocompiler\*.kt -include-runtime -d algo-compiler.jar
```

## Exécution

### Avec le script d'aide
```powershell
.\run.bat examples\hello.algo
```

### Directement avec Java
```powershell
java -jar build\libs\algo-compiler.jar examples\hello.algo
```

## Test rapide

Créez un fichier `test.algo`:
```
algorithme Test

variables
    x : entier

début
    écrire("Entrez un nombre:")
    lire(x)
    écrire("Le double est:", x * 2)
fin
```

Puis exécutez:
```powershell
java -jar build\libs\algo-compiler.jar test.algo
```
