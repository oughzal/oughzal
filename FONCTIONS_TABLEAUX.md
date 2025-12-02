# Compilateur Algo - Support des Fonctions et Tableaux

## ✅ Nouvelles fonctionnalités ajoutées

Votre compilateur supporte maintenant :

### 1. **Tableaux**

Déclaration de tableaux :
```algo
variables
    nombres : tableau[10] de entier
    notes : tableau[5] de reel
```

Accès et modification :
```algo
nombres[0] = 100
x = nombres[0]
```

### 2. **Fonctions**

Déclaration de fonction avec valeur de retour :
```algo
fonction somme(a : entier, b : entier) : entier
début
    retourner a + b
fin
```

Appel de fonction :
```algo
resultat = somme(5, 10)
```

### 3. **Procédures**

Déclaration de procédure (sans valeur de retour) :
```algo
procédure afficherMessage(nom : chaine)
début
    écrire("Bonjour", nom, "!")
fin
```

Appel de procédure :
```algo
afficherMessage("Alice")
```

### 4. **Tableaux comme paramètres**

Les fonctions peuvent accepter des tableaux en paramètre :
```algo
fonction maximum(arr : tableau de entier, taille : entier) : entier
variables
    i, max : entier
début
    max = arr[0]
    pour i de 1 à taille - 1 faire
        si arr[i] > max alors
            max = arr[i]
        finsi
    finpour
    retourner max
fin
```

### 5. **Variables locales dans les fonctions**

Les fonctions peuvent avoir leurs propres variables locales :
```algo
fonction calcul() : entier
variables
    temp, resultat : entier
début
    temp = 10
    resultat = temp * 2
    retourner resultat
fin
```

## 📝 Exemples disponibles

- `examples/test_tableaux.algo` - Démonstration des tableaux
- `examples/test_fonctions.algo` - Démonstration des fonctions et procédures
- `examples/test_complet.algo` - Exemple complet avec tableaux et fonctions
- `examples/test_fonctions_integrees.algo` - Démonstration des fonctions intégrées ✅
- `examples/calculatrice.algo` - Calculatrice utilisant fonctions intégrées ✅

## 🚀 Utilisation

```bash
# Compiler et exécuter
.\gradlew run --args="examples\test_fonctions.algo"

# Ou utiliser le script
.\run.bat examples\test_fonctions.algo
```

## 🎨 Fonctions intégrées

Le compilateur inclut maintenant des **fonctions intégrées** prêtes à l'emploi:

### Fonctions mathématiques
- `abs(x)` - Valeur absolue
- `racine(x)` - Racine carrée
- `puissance(x, y)` - x à la puissance y
- `arrondi(x)` - Arrondi au plus proche
- `plancher(x)` - Arrondi inférieur
- `plafond(x)` - Arrondi supérieur
- `sin(x)`, `cos(x)`, `tan(x)` - Fonctions trigonométriques
- `log(x)` - Logarithme naturel
- `exp(x)` - Exponentielle

### Fonctions de chaînes
- `longueur(s)` - Longueur d'une chaîne
- `majuscule(s)` - Convertir en majuscules
- `minuscule(s)` - Convertir en minuscules
- `sousChaine(s, debut, fin)` - Extraire une sous-chaîne

### Fonctions aléatoires
- `aleatoire()` - Nombre aléatoire entre 0 et 1
- `aleatoire(max)` - Entier aléatoire entre 0 et max-1
- `aleatoire(min, max)` - Entier aléatoire entre min et max-1

Exemple d'utilisation:
```algo
hypotenuse = racine(puissance(a, 2) + puissance(b, 2))
texte = majuscule("bonjour")
```

## 🔧 Mise à niveau technique

- ✅ Upgrade vers Java 21 LTS
- ✅ Support complet des tableaux multidimensionnels (indexation)
- ✅ Gestion des fonctions avec paramètres et retour
- ✅ Portée des variables (locale/globale)
- ✅ Passage de tableaux en paramètre
- ✅ Fonctions intégrées (mathématiques, chaînes, aléatoires)
