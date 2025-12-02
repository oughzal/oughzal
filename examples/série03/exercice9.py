# Exercice 9
N = int(input("Donner un nombre : "))

# S = 0
# while N != 0 :
#     S = S + N % 10
#     N = N // 10 
# print("La somme est ",S)

S = 0 
for c in str(N):  # "12345" 1234 -> "1234"
    S += int(c)
print("La somme est ",S)