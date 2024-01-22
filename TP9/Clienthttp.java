import java.io.*;
import java.net.*;

public class ClientHTTP {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java ClientHTTP <hostname>");
            System.exit(1);
        }

        try {
            // Ouvrir un socket sur le port 80 avec le nom d'hôte spécifié en ligne de commande
            Socket socket = new Socket(args[0], 80);

            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            InputStreamReader isw = new InputStreamReader(socket.getInputStream());

            BufferedWriter bufOut = new BufferedWriter(osw);
            BufferedReader bufIn = new BufferedReader(isw);

            // Requête HTTP GET
            String request = "GET / HTTP/1.0\r\n\r\n";
            bufOut.write(request, 0, request.length());
            bufOut.flush();

            // Lecture de la réponse ligne par ligne
            String line = bufIn.readLine();
            while (line != null) {
                System.out.println(line);
                line = bufIn.readLine();
            }

            // Fermeture des flux et du socket
            bufIn.close();
            bufOut.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
