import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Client1 {
    public static void main(String[] args) {
        // Q1.2 - Vérifier le nombre d'arguments
        if (args.length == 0) {
            System.err.println("Erreur : Veuillez fournir au moins 1 argument.");
            System.exit(1);
        }

        // Q1.3 - Création du client et de la requête HTTP
        try {
            // Création du client HTTP
            CloseableHttpClient client = HttpClients.createDefault();

            // Construction de l'URL à partir du premier argument
            String url = "http://" + args[0];

            // Création de la requête HTTP GET
            HttpGet request = new HttpGet(url);

            // Reste du code à ajouter pour l'envoi de la requête et la gestion de la réponse
            // ...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
