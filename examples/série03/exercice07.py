# Ecrire 7
N = int(input("Donner un nombre : "))
i = 2
while N % i != 0 and i < N :
    i = i + 1
if i == N :
    print(N ," est un nombre premier")
else:
    print(N ," n'est pas un nombre premier")
