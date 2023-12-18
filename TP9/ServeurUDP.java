import java.net.*;
import java.io.*;

public class ServeurUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket sock = new DatagramSocket(1234);
            while (true) {
                System.out.println("-Waiting data");
                DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
                sock.receive(receivePacket);

                String receivedStr = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + receivedStr);

                // Envoie la même chaîne au client en utilisant le même port du client
                DatagramPacket sendPacket = new DatagramPacket(receivedStr.getBytes(), receivedStr.length(), receivePacket.getAddress(), receivePacket.getPort());
                sock.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
