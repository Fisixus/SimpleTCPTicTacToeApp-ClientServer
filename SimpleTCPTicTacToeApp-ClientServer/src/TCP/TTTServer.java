package TCP;

import Utility.TCPSendReceive;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TTTServer {

    private ServerStates state = ServerStates.HOLDING;
    private int playerCounter = 0;

    public final String INSTRUCTION_FOR_INPUT = "Please press 1 for X, press 2 for O!";
    public final String INSTRUCTION_FOR_COORDINATE = "Please press your coordinate!" +
                                                       "\n" +
                                                        "Ex. For left top corner press 00, for right bottom corner press 22!";

    public TTTServer(int port) throws IOException {
        ServerSocket server = null;
        server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();
            Reader readerString = new InputStreamReader(socket.getInputStream());
            Writer writer = new OutputStreamWriter(socket.getOutputStream());

            do{
                char[] data = new char[1024];
                String receivedStr = TCPSendReceive.GetString(readerString, data);
                if(receivedStr.equals("Ready For TicTacToe!")){
                    playerCounter++;
                    if(playerCounter == 1){
                        TCPSendReceive.SendString(writer, "WAITING FOR SECOND PLAYER...");
                    }
                    else{
                        state = ServerStates.SIDESELECTION;
                        //TODO: Generate a board!
                    }
                }
            }while (state == ServerStates.HOLDING);

            do{
                //TCPSendReceive.SendString(writer, "");
            }while (state == ServerStates.SIDESELECTION);
            //writer.close();
            //readerString.close();
            //socket.close();
        }
    }

    public static void main(String[] argv) throws IOException {
        TTTServer simpleTCPServer = new TTTServer(7);
    }
}