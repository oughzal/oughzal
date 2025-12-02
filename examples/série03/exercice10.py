# Exercice10
N = int(input("Donner un nombre : "))
# Inv = 0
# while N != 0 :
#     Inv = Inv*10 + N % 10
#     N = N // 10
# print("La somme est ",Inv)

Inv = ""
for c in str(N): # "1234"
    Inv =c + Inv 
print("L'inverse est ",int(Inv))

print("l'inverse est",str(N)[::-1]) # "12345"


