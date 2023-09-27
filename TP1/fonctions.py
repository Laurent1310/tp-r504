
def puissance(a, b):
    # Vérifier que les deux arguments sont entiers
    if not type(a) is int :
        raise TypeError("Les deux arguments doivent être des entiers.")
    if not type(b) is int :
    	raise TypeError("Les deux arguments doivent être des entiers.")
    
    # Calcul de a**b
    i=0
    resultat=a
    for i in range (1,b):
    	resultat = resultat*a
    	i=i+1
    return resultat


