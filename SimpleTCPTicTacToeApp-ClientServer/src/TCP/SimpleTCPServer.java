package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleTCPServer {

    public SimpleTCPServer() throws IOException {
        this(7);
    }

    public SimpleTCPServer(int port) throws IOException {
        ServerSocket server = null;
        server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();
            Reader reader = new InputStreamReader(socket.getInputStream());
            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            char[] data = new char[1024];
            int numberOfReadCharacters;
            while (true) {
                numberOfReadCharacters = reader.read(data, 0, 1024);
                if (numberOfReadCharacters == -1) {
                    break;
                }
                if (numberOfReadCharacters > 0) {
                    //DataInputStream dis = new DataInputStream(socket.getInputStream());
                    //dis.readInt();
                    String str = new String(data, 0, numberOfReadCharacters);
                    System.out.println(str);
                    writer.write(data, 0, numberOfReadCharacters);
                    writer.flush();
                }

            }
            writer.write(-1);
            writer.close();
            reader.close();
            socket.close();
        }
    }

    public static void main(String[] argv) throws IOException {
        SimpleTCPServer simpleTCPServer = new SimpleTCPServer();
    }
}