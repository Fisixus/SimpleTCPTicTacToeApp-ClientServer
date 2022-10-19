package TCP;

import TicTacToe.TicTacToe;
import Utility.Instructions;
import Utility.PlayerTypes;
import Utility.TCPSendReceive;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class TTTServer {
    private ServerStates state = ServerStates.HOLDING;

    public TTTServer(int port) throws IOException {
        ServerSocket server = null;
        Scanner scanner = new Scanner(System.in);
        server = new ServerSocket(port);
        TicTacToe ttt = new TicTacToe();

        //while (true) {
            Socket socket = server.accept();
            Reader readerString = new InputStreamReader(socket.getInputStream());
            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            char[] data;

            do{
                data = new char[1024];
                String receivedStr = TCPSendReceive.GetString(readerString, data);
                if(receivedStr.equals("Ready For TicTacToe!")){
                    state = ServerStates.SIDESELECTION;
                    ttt.StartGame();
                }
            }while (state == ServerStates.HOLDING);

            do{
                System.out.println(Instructions.INSTRUCTION_FOR_SELECTING_SIDE);

                String serverChar;
                do{
                    serverChar = scanner.nextLine();
                }while(!serverChar.equalsIgnoreCase("X") && !serverChar.equalsIgnoreCase("O"));

                TCPSendReceive.SendString(writer, Instructions.INSTRUCTION_FOR_SELECTING_SIDE);

                data = new char[1024];
                String receivedChar = TCPSendReceive.GetString(readerString,data);
                if(receivedChar.equalsIgnoreCase(serverChar)){
                    if(receivedChar.equalsIgnoreCase("X")){
                        serverChar = "O";
                    }
                    else{
                        serverChar = "X";
                    }
                    System.out.println(Instructions.PrintChangeSide(receivedChar, serverChar));
                }
                ttt.SetPlayerSides(serverChar, receivedChar);
                state = ServerStates.TURNASSIGN;

            }while (state == ServerStates.SIDESELECTION);


            boolean isServerGoingToStart = ttt.IsServerGoingToStart();

            do{
                System.out.println(Instructions.PrintSides(ttt.GetServerSide().name(),ttt.GetClientSide().name()));
                TCPSendReceive.SendString(writer, Instructions.PrintSides(ttt.GetServerSide().name(),ttt.GetClientSide().name()));

                if(isServerGoingToStart){
                    TCPSendReceive.SendString(writer, Instructions.PrintStartingSide("Server"));
                }
                else{
                    TCPSendReceive.SendString(writer, Instructions.PrintStartingSide("Client"));
                }
                System.out.println(Instructions.PrintStartingSide("Client"));
                state = ServerStates.PLAYING;
                break;
            }while (state == ServerStates.TURNASSIGN);

            PlayerTypes winnerType;
            do{
                if(isServerGoingToStart){
                    ServerPlay(scanner ,ttt, writer);
                    System.out.println(ttt.GetBoard2D());
                    winnerType = ttt.CalculateWinnerCondition();
                    if(winnerType != null){
                        state = ServerStates.RESULTING;
                        TCPSendReceive.SendString(writer,  "FIN!");
                        data = new char[1024];
                        String xx = TCPSendReceive.GetString(readerString,data);
                        TCPSendReceive.SendString(writer,  ttt.GetBoard2D());
                        break;
                    }

                    ClientPlay(scanner, ttt, writer, readerString);
                    TCPSendReceive.SendString(writer, ttt.GetBoard2D());
                    winnerType = ttt.CalculateWinnerCondition();
                    if(winnerType != null){
                        state = ServerStates.RESULTING;
                        TCPSendReceive.SendString(writer,  "FIN!");
                        System.out.println(ttt.GetBoard2D());
                        break;
                    }

                }
                else{
                    ClientPlay(scanner, ttt, writer, readerString);
                    TCPSendReceive.SendString(writer, ttt.GetBoard2D());
                    winnerType = ttt.CalculateWinnerCondition();
                    if(winnerType != null){
                        state = ServerStates.RESULTING;
                        TCPSendReceive.SendString(writer,  "FIN!");
                        System.out.println(ttt.GetBoard2D());
                        break;
                    }

                    ServerPlay(scanner ,ttt, writer);
                    System.out.println(ttt.GetBoard2D());
                    winnerType = ttt.CalculateWinnerCondition();
                    if(winnerType != null){
                        state = ServerStates.RESULTING;
                        TCPSendReceive.SendString(writer,  "FIN!");
                        data = new char[1024];
                        String xx = TCPSendReceive.GetString(readerString,data);
                        TCPSendReceive.SendString(writer,  ttt.GetBoard2D());
                        break;
                    }
                }
            }while (state == ServerStates.PLAYING);

            System.out.println(ttt.AnnounceResult(winnerType));
            TCPSendReceive.SendString(writer, ttt.AnnounceResult(winnerType));
            data = new char[1024];
            String lastCheck = TCPSendReceive.GetString(readerString,data);

            writer.close();
            readerString.close();
            socket.close();

        //}
    }

    private void ClientPlay(Scanner scanner, TicTacToe t, Writer writer, Reader readerString) throws IOException {
        boolean isMoveLegal = false;
        do{
            TCPSendReceive.SendString(writer, Instructions.INSTRUCTION_FOR_COORDINATE + "\n" + t.GetBoard2D());

            char[] data = new char[1024];
            String receivedCoordinate = TCPSendReceive.GetString(readerString,data);
            isMoveLegal  = MakeAMove(scanner, t, receivedCoordinate, PlayerTypes.Client);
            if(!isMoveLegal){
                TCPSendReceive.SendString(writer, "WrongInput");
                data = new char[1024];
                TCPSendReceive.GetString(readerString,data);
            }
            else {
                TCPSendReceive.SendString(writer, "TrueInput");
                data = new char[1024];
                TCPSendReceive.GetString(readerString,data);
            }
        }while(!isMoveLegal);
    }

    private void ServerPlay(Scanner scanner, TicTacToe t, Writer writer) throws IOException {
        boolean isMoveLegal = false;
        do{
            System.out.println(Instructions.INSTRUCTION_FOR_COORDINATE + "\n" + t.GetBoard2D());
            String newMove = scanner.nextLine();
            isMoveLegal  = MakeAMove(scanner, t, newMove, PlayerTypes.Server);
        }while(!isMoveLegal);
    }

    private boolean MakeAMove(Scanner scanner, TicTacToe t, String newMove, PlayerTypes playerTypes){
        try{
            int row = Integer.parseInt(String.valueOf(newMove.charAt(0)));
            int column = Integer.parseInt(String.valueOf(newMove.charAt(1)));
            return t.Play(playerTypes,row,column);
        }
        catch (Exception e){
            return false;
        }
    }

    public static void main(String[] argv) throws IOException {
        TTTServer simpleTCPServer = new TTTServer(7);
    }
}