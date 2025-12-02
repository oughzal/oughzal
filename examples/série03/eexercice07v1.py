# Ecrire 7
N = int(input("Donner un nombre : "))
i = 2
p = True
S = 0
for i in range(2,N):
    if N % i == 0 :
        p = False
if p == True :
    print(N ," est un nombre premier")
else:
    print(N ," n'est pas un nombre premier")
