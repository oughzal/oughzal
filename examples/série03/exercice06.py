# Ecrire 6
N = int(input("Donner un nombre entier positif : "))
F = 1
# pour i de N à 1 pas -1 Faire
for i in range(N,0,-1):
    F = F*i
print(N,"! = ", F)