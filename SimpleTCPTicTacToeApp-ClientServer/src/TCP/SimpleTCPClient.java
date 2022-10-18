package TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SimpleTCPClient {
    public SimpleTCPClient(String message) throws IOException {
        this("localhost", 7, message);
    }

    public SimpleTCPClient(String hostNameOrIp, int port, String message) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(hostNameOrIp);
        Socket socket = new Socket(inetAddress, port);
        Reader reader = new InputStreamReader(socket.getInputStream());
        Writer writer = new OutputStreamWriter(socket.getOutputStream());
        writer.write(message);
        writer.flush();
        char[] data = new char[1024];
        int numberOfReadCharacters;
        while (true) {
            numberOfReadCharacters = reader.read(data, 0, 1024);
            if (numberOfReadCharacters == -1) {
                break;
            }
            if (numberOfReadCharacters > 0) {
                String str = new String(data, 0, numberOfReadCharacters);
                System.out.println(str);
                break;
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        SimpleTCPClient simpleTCPClient = new SimpleTCPClient("Hello, CNT5715!!!");
    }
}
