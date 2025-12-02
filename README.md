# Compilateur de Pseudo-code Français (AlgoC)

Un compilateur/interpréteur de pseudo-code français écrit en Kotlin.

## Caractéristiques

- **Syntaxe française** : Utilise des mots-clés français naturels
- **Variables typées** : Support des types entier, réel, chaîne et booléen
- **Structures de contrôle** : Si/Alors/Sinon, Pour, TantQue, Répéter/Jusqu'à
- **Opérations** : Arithmétiques, logiques et de comparaison
- **Entrée/Sortie** : Instructions Lire et Écrire

## Installation

### Prérequis
- Java 17 ou supérieur
- Gradle (optionnel, le wrapper est inclus)

### Construction du projet

```bash
# Sur Windows
.\gradlew.bat build

# Sur Linux/Mac
./gradlew build
```

Le fichier JAR sera généré dans `build/libs/algo-compiler-1.0.0.jar`

## Utilisation

```bash
java -jar build/libs/algo-compiler-1.0.0.jar fichier.algo
```

## Syntaxe

### Structure de base

```
algorithme NomAlgorithme

variables
    x, y : entier
    nom : chaine
    resultat : reel

début
    // Votre code ici
fin
```

### Types de données
- `entier` : Nombres entiers
- `reel` : Nombres à virgule flottante
- `chaine` : Chaînes de caractères
- `booleen` : Vrai ou Faux

### Affectation
```
x := 10
nom := "Alice"
```

### Entrée/Sortie
```
lire(x)
écrire("Bonjour", nom)
```

### Conditions
```
si x > 0 alors
    écrire("Positif")
sinon
    écrire("Négatif ou nul")
finsi
```

### Boucles

#### Boucle Pour
```
pour i de 1 à 10 faire
    écrire(i)
finpour
```

#### Boucle TantQue
```
tantque x < 100 faire
    x := x + 1
fintantque
```

#### Boucle Répéter/Jusqu'à
```
répéter
    x := x + 1
jusqu'à x > 10
```

### Opérateurs

#### Arithmétiques
- `+` : Addition
- `-` : Soustraction
- `*` : Multiplication
- `/` : Division
- `mod` : Modulo

#### Comparaison
- `=` : Égal
- `<>` ou `!=` : Différent
- `<` : Inférieur
- `>` : Supérieur
- `<=` : Inférieur ou égal
- `>=` : Supérieur ou égal

#### Logiques
- `et` : ET logique
- `ou` : OU logique
- `non` : NON logique

## Exemples

### Exemple 1 : Calcul de factorielle
```
algorithme Factorielle

variables
    n, i, resultat : entier

début
    écrire("Entrez un nombre:")
    lire(n)
    
    resultat := 1
    pour i de 1 à n faire
        resultat := resultat * i
    finpour
    
    écrire("Factorielle de", n, "=", resultat)
fin
```

### Exemple 2 : Nombres pairs
```
algorithme NombresPairs

variables
    i : entier

début
    écrire("Les 10 premiers nombres pairs:")
    
    pour i de 1 à 10 faire
        écrire(i * 2)
    finpour
fin
```

### Exemple 3 : Devine le nombre
```
algorithme DevineNombre

variables
    secret, essai, tentatives : entier

début
    secret := 42
    tentatives := 0
    
    répéter
        écrire("Devinez le nombre:")
        lire(essai)
        tentatives := tentatives + 1
        
        si essai < secret alors
            écrire("Trop petit!")
        sinon
            si essai > secret alors
                écrire("Trop grand!")
            finsi
        finsi
    jusqu'à essai = secret
    
    écrire("Bravo! Trouvé en", tentatives, "tentatives")
fin
```

## Notes

- Les accents sont optionnels (vous pouvez écrire `debut` au lieu de `début`)
- Les commentaires commencent par `//`
- Les instructions peuvent être sur plusieurs lignes
- La casse n'est pas sensible pour les mots-clés

## Développement

### Structure du projet
```
src/main/kotlin/com/algocompiler/
├── Main.kt           # Point d'entrée
├── Token.kt          # Définition des tokens
├── Lexer.kt          # Analyse lexicale
├── AST.kt            # Arbre syntaxique abstrait
├── Parser.kt         # Analyse syntaxique
└── Interpreter.kt    # Interpréteur
```

## Licence

Ce projet est sous licence MIT.
