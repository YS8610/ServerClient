package com.day4;

import java.io.*;
import java.net.*;
// import java.util.List;
// import java.util.Random;


public class Server 
{

    private ServerSocket serverSocket;

    private Server(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    public void startServer(String filename)
    {
        while(!serverSocket.isClosed())
        {
            try 
            {
                Socket socket = serverSocket.accept();
                System.out.println("New Client connected!");
                CookieClientHandler cookieClientHandler = new CookieClientHandler(socket,filename);
    
                Thread thread = new Thread(cookieClientHandler);
                thread.start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void closeServerSocket()
    {
        try
        {
            if(serverSocket!=null)
            {
                serverSocket.close();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) throws UnknownHostException, IOException 
    {
        int portNo = 3000;
        String fileName = "cookies.txt";

        if (null!=args && args.length==2) //check for command line argument
        {
            portNo = Integer.parseInt(args[0]);
            fileName = args[1];
        }
        System.out.println("Server will listening at port "+ portNo+" and reading "+ fileName);

        ServerSocket serverSocket = new ServerSocket(portNo);
        Server server = new Server(serverSocket);
        server.startServer(fileName);
    }
}