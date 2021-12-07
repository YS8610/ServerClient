package com.day4;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class ServerClient 
{
    public static void main( String[] args ) throws UnknownHostException, IOException 
    {
        System.out.println("Server Starting...");
        ServerSocket server = new ServerSocket(3000);
        System.out.println("listening at port 3000, localhost = 127.0.0.1");
        Socket socket = server.accept();

        // socket = new Socket("localhost",3000);
        try (InputStream is = socket.getInputStream())
        {
            // is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
            // String string = dis.readUTF();
            // System.out.println("Message: " + string);
            // need to return cookie from cookie class
        }

        socket.close();
        server.close();
    }
}
