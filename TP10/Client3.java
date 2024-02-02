
import java.util.Scanner;  
import java.io.*;
import javax.json.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

public class Client3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Saisie du titre au clavier
            System.out.println("Veuillez saisir le titre du film (ou 'exit' pour quitter) :");
            String TitreFilm = scanner.nextLine();

            // Vérification de la sortie
            if (TitreFilm.equalsIgnoreCase("exit")) {
                System.out.println("Programme terminé.");
                break;
            }

            // Clé API OMDb (remplacez XXXXX par votre clé)
            String apiKey = "751ea6aa";

            // Création de l'URL de requête à l'API OMDb avec la clé API et le titre du film
            String omdbUrl = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + TitreFilm;

            // Création du client HTTP et de la requête GET
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(omdbUrl);

            try {
                // Exécution de la requête
                CloseableHttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();

                // Traitement de la réponse
                if (entity != null) {
                    InputStreamReader isr = new InputStreamReader(entity.getContent());
                    JsonReader jsonReader = Json.createReader(isr);

                    // Lecture de l'objet JSON principal
                    JsonObject jsonObject = jsonReader.readObject();

                    // Affichage des informations essentielles
                    System.out.println("Titre : " + jsonObject.getString("Title"));
                    System.out.println("Année de sortie : " + jsonObject.getString("Year"));
                    System.out.println("Acteurs principaux : " + jsonObject.getString("Actors"));

                    // Récupération du tableau JSON des évaluations
                    JsonArray ratingsArray = jsonObject.getJsonArray("Ratings");

                    // Recherche de la note de "Rotten Tomatoes"
                    for (JsonValue value : ratingsArray) {
                        JsonObject rating = (JsonObject) value;
                        if ("Rotten Tomatoes".equals(rating.getString("Source"))) {
                            String rottenTomatoesRating = rating.getString("Value");
                            System.out.println("Note Rotten Tomatoes : " + rottenTomatoesRating);

                            // Évaluation du score
                            evaluateRottenTomatoesScore(rottenTomatoesRating);
                            break;
                        }
                    }

                    // Fermeture des ressources
                    jsonReader.close();
                    isr.close();
                }

                // Fermeture des ressources
                response.close();
                client.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void evaluateRottenTomatoesScore(String rating) {
        // Extraction du pourcentage de la note
        int percentage = Integer.parseInt(rating.replaceAll("[^0-9]", ""));

        // Évaluation du score
        if (percentage < 20) {
            System.out.println("Évaluation : Nul");
        } else if (percentage >= 20 && percentage < 50) {
            System.out.println("Évaluation : Bof");
        } else if (percentage >= 50 && percentage < 70) {
            System.out.println("Évaluation : Bien");
        } else {
            System.out.println("Évaluation : Très bien");
        }
    }
}

