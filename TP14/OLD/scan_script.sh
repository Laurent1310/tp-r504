#!/bin/bash

# Définir le répertoire de résultats
result_dir="/home/user/tp-r504/TP14/Résultats Diag"

# Créer le répertoire s'il n'existe pas déjà
mkdir -p "$result_dir"

# Obtenir la date et l'heure actuelles au format YYYYMMDD_HHMMSS
timestamp=$(date +"%Y%m%d_%H%M%S")

# Exécuter debsecan avec les options nécessaires
vulnerabilities=$(/usr/bin/sudo /usr/bin/debsecan --suite $(/usr/bin/lsb_release --codename --short) --only-fixed | tee "$result_dir/result_$timestamp.txt" | grep -c "Vulnerability")

# Ajouter un texte personnalisé dans le fichier résultat
echo "Scan debsecan effectué avec succès, voici le nombre de vulnérabilités : $vulnerabilities" >> "$result_dir/result_$timestamp.txt"

echo "Scan debsecan effectué avec succès. Résultats stockés dans : $result_dir/result_$timestamp.txt"

