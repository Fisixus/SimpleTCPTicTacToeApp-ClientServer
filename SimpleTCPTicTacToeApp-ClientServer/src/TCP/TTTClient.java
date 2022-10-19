package TCP;

import Utility.Instructions;
import Utility.TCPSendReceive;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TTTClient {


    public TTTClient(String hostNameOrIp, int port) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(hostNameOrIp);
        Socket socket = new Socket(inetAddress, port);
        Scanner scanner = new Scanner(System.in);

        Reader readerString = new InputStreamReader(socket.getInputStream());
        DataInputStream readerNumber = new DataInputStream(socket.getInputStream());
        Writer writer = new OutputStreamWriter(socket.getOutputStream());

        TCPSendReceive.SendString(writer, "Ready For TicTacToe!");

        char[] data = new char[1024];
        String receivedStr = TCPSendReceive.GetString(readerString, data);

        if(receivedStr.equals(Instructions.INSTRUCTION_FOR_SELECTING_SIDE)){
            System.out.println(receivedStr);
            String clientChar;
            do{
                clientChar = scanner.nextLine();
            }while(!clientChar.equalsIgnoreCase("X") && !clientChar.equalsIgnoreCase("O"));
            TCPSendReceive.SendString(writer, clientChar);
        }

        data = new char[1024];
        receivedStr = TCPSendReceive.GetString(readerString, data);
        System.out.println(receivedStr);

        data = new char[1024];
        receivedStr = TCPSendReceive.GetString(readerString, data);
        System.out.println(receivedStr);

        while (true){
            data = new char[1024];
            receivedStr = TCPSendReceive.GetString(readerString, data);
            if(receivedStr.equalsIgnoreCase("FIN!")){
                //TODO
                break;
            }
            System.out.println(receivedStr);
            String coordinate = scanner.nextLine();
            TCPSendReceive.SendString(writer, coordinate);
        }
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
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        TTTClient client = new TTTClient("localhost", 7);
    }
}
