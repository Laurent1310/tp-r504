#!/bin/bash

# -----------------------------------
# CONFIGURATION
# -----------------------------------

# Définir le répertoire de résultats
result_dir="/home/user/tp-r504/TP14/Resultats-Diag"

# Limiter le nombre de fichiers de résultats conservés
max_results=7

# -----------------------------------
# FONCTIONS nettoyer les résultats anciens (Si ils n'ont pas était modifier depuis plus de 7 jours ils seront supprimés ) 
# -----------------------------------

clean_old_results() {
  find "$result_dir" -type f -mtime +7 -exec rm -f {} \;
}

#La fonction clean_old_results utilise la commande find pour rechercher tous les fichiers (-type f) dans le répertoire spécifié ("$result_dir") qui ont une date de modification (-mtime) de plus de 7 jours (+7), puis elle utilise rm -f pour les supprimer.


# -----------------------------------
# FONCTIONS gérer la rotation des logs
# -----------------------------------

if [ $(ls -1q "$result_dir"/scan_log*.txt 2>/dev/null | wc -l) -gt $max_results ]; then
  mv "$result_dir"/scan_log*.txt "$result_dir"/scan_log_old/
fi



# -----------------------------------
# SCRIPT PRINCIPAL
# -----------------------------------

# Créer le répertoire de résultats s'il n'existe pas déjà
mkdir -p "$result_dir"

# Obtenir la date et l'heure actuelles au format YYYYMMDD_HHMMSS
timestamp=$(date +"%Y%m%d_%H%M%S")

# Exécuter debsecan avec les options nécessaires
vulnerabilities=$(/usr/bin/sudo /usr/bin/debsecan --suite $(/usr/bin/lsb_release --codename --short) --only-fixed | tee "$result_dir/result_$timestamp.txt" | grep -c "Vulnerability")

# -----------------------------------
# SCRIPT FICHIER RESULTAT
# -----------------------------------

# Ajouter un texte personnalisé dans le fichier résultat
echo "Scan debsecan effectué avec succès, voici le nombre de vulnérabilités : $vulnerabilities" >> "$result_dir/result_$timestamp.txt"


# Afficher le succès du scan et l'emplacement des résultats
echo "Scan debsecan effectué avec succès. Résultats stockés dans : $result_dir/result_$timestamp.txt"


# Ajouter une entrée dans le fichier journal
echo "$(date +"%Y-%m-%d %H:%M:%S") - Scan debsecan effectué avec succès. Nombre de vulnérabilités : $vulnerabilities" >> "$result_dir/scan_log.txt"

#Page HTML
# Tri des résultats par date
sort -r -t' ' -k1,2 "$result_dir/scan_log.txt" > "$result_dir/scan_log_sorted.txt"

# Statistiques globales
total_tests=$(wc -l < "$result_dir/scan_log_sorted.txt")
total_vulnerabilities=$(awk '{sum+=$NF} END{print sum}' "$result_dir/scan_log_sorted.txt")


# Page HTML
echo "
<html>
<head>
  <style>
    body {
      font-family: 'Arial', sans-serif;
      background-color: #f4f4f4;
      margin: 20px;
    }
    h1 {
      color: #333;
    }
    p {
      color: #555;
    }
    #vulnerabilitiesChart {
      max-width: 600px;
      margin: 20px 0;
    }
  </style>
  <script src='https://cdn.jsdelivr.net/npm/chart.js'></script>
</head>
<body>
  <h1>Rapport de Scan debsecan</h1>
  <p>Date du dernier scan : $(date +"%Y-%m-%d")</p>
  <p>Heure du dernier scan : $(date +"%H:%M:%S")</p>
  <p>Nombre de vulnérabilités détectées : $vulnerabilities</p>
  <p>Total des tests effectués : $total_tests</p>
  <p>Total des vulnérabilités détectées : $total_vulnerabilities</p>

  <h2>Historique des tests</h2>
  <ul>
    $(awk '{print "<li>" $0 "</li>"}' "$result_dir/scan_log_sorted.txt")
  </ul>

  <h2>Graphique des vulnérabilités au fil du temps</h2>
  <canvas id='vulnerabilitiesChart'></canvas>

  <script>
    var ctx = document.getElementById('vulnerabilitiesChart').getContext('2d');
    var vulnerabilitiesData = {
      labels: $(awk '{print $1 " " $2}' "$result_dir/scan_log_sorted.txt"),
      datasets: [{
        label: 'Nombre de vulnérabilités',
        data: $(awk '{print $NF}' "$result_dir/scan_log_sorted.txt"),
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }]
    };
    var vulnerabilitiesChart = new Chart(ctx, {
      type: 'line',
      data: vulnerabilitiesData,
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  </script>
</body>
</html>" > "$result_dir/report.html"



# Nettoyer les résultats anciens pour éviter l'encombrement
clean_old_results



