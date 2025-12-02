# exercice 8
N  = int(input("Donner un nombre positif : "))
S = 0
for i in range(1,N):
    if N % i == 0  :
        S = S+ i
if N == S :
    print(N, " est un nombre parfait")
else :
    print(N, " n'est pas un nombre parfait")
