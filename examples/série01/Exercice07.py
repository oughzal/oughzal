# //Entrée
# Ecrire("Donner le temps T : ")
# Lire(T)
T = int(input("Donner le temps T : "))

# //Traitement
H = T // 3600
T = T % 3600
M = T // 60
S = T % 60

# //Sortie
print(H,":",M,":",S,sep="")