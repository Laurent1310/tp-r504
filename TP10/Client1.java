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

        // Création du client HTTP et de la requête GET
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://" + args[0];
        HttpGet request = new HttpGet(url);

        try {
            // Exécution de la requête
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            // Traitement de la réponse (vous pouvez afficher ou sauvegarder le contenu ici)
            if (entity != null) {
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
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

