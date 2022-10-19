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

            if(receivedStr.contains("FIN!")){
                TCPSendReceive.SendString(writer, "FIN!");
                data = new char[1024];
                receivedStr = TCPSendReceive.GetString(readerString, data);
                System.out.println(receivedStr);
                break;
            }

            System.out.println(receivedStr);
            String coordinate = scanner.nextLine();
            TCPSendReceive.SendString(writer, coordinate);

            data = new char[1024];
            receivedStr = TCPSendReceive.GetString(readerString, data);

            /*
            if(receivedStr.contains("coordinate!")){
                System.out.println(receivedStr);
                coordinate = scanner.nextLine();
                TCPSendReceive.SendString(writer, coordinate);
            }
            */
            if(receivedStr.contains("FIN!")){
                break;
            }

            System.out.println(receivedStr);
        }

        writer.close();
        readerString.close();
        socket.close();
        System.out.println("FINITO!");
    }

    public static void main(String[] args) throws IOException {
        TTTClient client = new TTTClient("localhost", 7);
    }
}
