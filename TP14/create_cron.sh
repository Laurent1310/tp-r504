#!/bin/bash

# Ajouter la tâche planifiée dans crontab
(crontab -l 2>/dev/null; echo "30 2 * * * /home/user/tp-r504/TP14/scan_script.sh") | crontab -

echo "Tâche planifiée ajoutée avec succès à 02h30 tous les jours."
