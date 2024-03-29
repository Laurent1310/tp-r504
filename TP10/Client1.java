import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

public class Client1 {
    public static void main(String[] args) {
        // Vérification des arguments en ligne de commande
        if (args.length < 1) {
            System.err.println("Usage: java Client1 <hostname>");
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                // Lecture de la réponse ligne par ligne et affichage à l'écran
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Fermeture des ressources
            response.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

