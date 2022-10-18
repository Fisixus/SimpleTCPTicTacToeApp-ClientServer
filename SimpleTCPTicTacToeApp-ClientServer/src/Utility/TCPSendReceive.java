package Utility;

import java.io.*;

public class TCPSendReceive {

    public static void SendString(Writer writer, String message) throws IOException {
        writer.write(message);
        writer.flush();
    }

    public static String GetString(Reader reader, char[] data) throws IOException {
        int numberOfReadCharacters = reader.read(data, 0, 1024);
        if(numberOfReadCharacters == -1)
            return "";
        String str = new String(data, 0, numberOfReadCharacters);
        return str;
    }

    public static void SendInt(Writer writer, int message) throws IOException {
        writer.write(message);
        writer.flush();
    }

    /*
    public static String GetInt(DataInputStream reader, char[] data) throws IOException {
        reader.readInt()
        return str;
    }
    */

}
