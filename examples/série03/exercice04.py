# Exercice 4 : Somme des carrés
N = int(input("Entrez un nombre entier positif : "))
s = 0
for i in range(1, N + 1):
    s += i**2
print(f"La somme est : {s}")