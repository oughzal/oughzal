# Exercice 11 
from math import gcd  
a = int(input("Donner  a : "))
b = int(input("Donner  b : "))

d = min(a,b)
while a % d !=0 or b % d !=0 :
    d = d - 1
print(a,"/",b," = " , a//d, "/", b//d,sep="")

d = gcd(a,b)
print(a,"/",b," = " , a//d, "/", b//d,sep="")
