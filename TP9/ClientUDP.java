import java.io.*;
import java.net.*;

public class ClientUDP {
    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("adresse=" + addr.getHostName());

            String s = "Hello World";
            byte[] data = s.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, addr, 1234);

            DatagramSocket sock = new DatagramSocket();
            sock.send(sendPacket);

            // Attend la réponse du serveur
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            sock.receive(receivePacket);

            String receivedStr = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received from server: " + receivedStr);

            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
