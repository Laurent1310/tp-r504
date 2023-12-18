import java.io.*;
import java.net.*;

public class ClientTCP3 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 2016);

            // Envoyer la chaîne au serveur
            String msg = "Bonjour";
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            dOut.writeUTF(msg);

            // Recevoir la chaîne inversée du serveur
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            String reversedMsg = dIn.readUTF();
            System.out.println("Message inversé reçu: " + reversedMsg);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
