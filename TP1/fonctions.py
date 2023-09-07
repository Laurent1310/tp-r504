
def puissance(a, b):
    # Vérifier que les deux arguments sont entiers
    if not type(a) is int :
        raise TypeError("Les deux arguments doivent être des entiers.")
    if not type(b) is int :
    	raise TypeError("Les deux arguments doivent être des entiers.")
    
    # Calcul de a**b
    resultat = a ** b
    return resultat


