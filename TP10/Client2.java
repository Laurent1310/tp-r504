import java.io.*;
import javax.json.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

public class Client2 {
    public static void main(String[] args) {
        // Vérification des arguments en ligne de commande
        if (args.length < 1) {
            System.err.println("Usage: java Client2 <hostname>");
            System.exit(1);
        }

        // Clé API OMDb (remplacez XXXXX par votre clé)
        String apiKey = "751ea6aa";

        // Création de l'URL de requête à l'API OMDb avec la clé API
        String omdbUrl = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=Avengers";

        // Création du client HTTP et de la requête GET
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(omdbUrl);

        try {
            // Exécution de la requête
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            // Traitement de la réponse
            if (entity != null) {
                InputStream content = entity.getContent();
                InputStreamReader isr = new InputStreamReader(content);

                // Création du lecteur JSON
                JsonReader reader = Json.createReader(isr);

                // Récupération de l'objet JSON principal
                JsonObject jsonObject = reader.readObject();

                // Fermeture du lecteur JSON et du flux
                reader.close();
                isr.close();

                // Accès à la valeur de la clé "Runtime" (durée du film)
                String runtime = jsonObject.getString("Runtime");
                System.out.println("Durée du film : " + runtime);

                // Accès à la valeur de la clé "Year" (année de sortie)
                String year = jsonObject.getString("Year");
                System.out.println("Année de sortie : " + year);
            }

            // Fermeture des ressources
            response.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

