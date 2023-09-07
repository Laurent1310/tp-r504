import fonctions as f

print("Hello, World!")

while True :
	# Demande à l'utilisateur de saisir un nombre
	a = input("Veuillez entrer un nombre : ")
	b = input("Veuillez entrer la puissance : ")

	# Convertit la chaîne de caractères en un nombre entier
	a = int(a)
	b = int(b)

	# Calcule le carré du nombre
	res = f.puissance(a,b)

	# Affiche le résultat
	print(f"Le carré de {a} est {res}")
 
