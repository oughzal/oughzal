#exercice 03 : 
for i in range(1,11):
    print(f"La table de mutiplication de {i}")
    for j in range(1,11):
        print(f"{i} x {j} = {i*j}")

for i in range(1,11):
    for j in range(1,11):
        print(i*j,end="\t")
    print()

