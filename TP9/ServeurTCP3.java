import java.io.*;
import java.net.*;

public class ServeurTCP3 {
    public static void main(String[] args) {
        try {
            ServerSocket socketserver = new ServerSocket(2016);
            System.out.println("Serveur en attente");

            while (true) {
                Socket socket = socketserver.accept();
                System.out.println("Connection d’un client");

                // Lire la chaîne du client
                DataInputStream dIn = new DataInputStream(socket.getInputStream());
                String receivedMsg = dIn.readUTF();
                System.out.println("Message reçu: " + receivedMsg);

                // Inverser la chaîne
                String reversedMsg = new StringBuilder(receivedMsg).reverse().toString();

                // Envoyer la chaîne inversée au client
                DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                dOut.writeUTF(reversedMsg);

                // Fermer la connexion après avoir envoyé la réponse
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
