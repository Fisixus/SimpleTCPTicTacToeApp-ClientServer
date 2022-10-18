package TCP;

import Utility.TCPSendReceive;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TTTClient {
    public TTTClient(String hostNameOrIp, int port) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(hostNameOrIp);
        Socket socket = new Socket(inetAddress, port);

        Reader readerString = new InputStreamReader(socket.getInputStream());
        DataInputStream readerNumber = new DataInputStream(socket.getInputStream());
        Writer writer = new OutputStreamWriter(socket.getOutputStream());

        TCPSendReceive.SendString(writer, "Ready For TicTacToe!");

        while (true) {
            char[] data = new char[1024];
            String receivedStr = TCPSendReceive.GetString(readerString, data);
            break;
            /*
            numberOfReadCharacters = readerString.read(data, 0, 1024);
            if (numberOfReadCharacters == -1) {
                break;
            }
            if (numberOfReadCharacters > 0) {
                String str = new String(data, 0, numberOfReadCharacters);
                System.out.println(str);
                break;
            }
             */
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        TTTClient client = new TTTClient("localhost", 7);
    }
}
