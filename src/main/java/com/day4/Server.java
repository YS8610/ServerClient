package com.day4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    int port;
    ServerSocket serverSocket;
    String dir;

// constructor
    public Server(){
        this.port = 3000;
        this.dir = "cookie.txt";
    }
    public Server(int port,String dir){
        this.port = port;
        this.dir = dir;
    }
//method
    public void startServer() throws IOException{
        Cookie cookie = new Cookie(this.dir);
        ExecutorService threadPool = Executors.newFixedThreadPool(5); 
        serverSocket = new ServerSocket(this.port);

        while (!serverSocket.isClosed()){
            Socket socket = serverSocket.accept();
            CookieClientHandler cookieClientHandler = new CookieClientHandler(socket,cookie);
            threadPool.submit(cookieClientHandler);
            // new Thread(cookieClientHandler).start();
        }
    }

    private static String[] parseArg(String[] args){
        String[] a = {"3000","cookies.txt"};
        if(args.length==0){
            System.out.println("Default port 3000 and default file used is cookies.txt");
        }
        else if (args.length==2){
            a[0] = args[0];
            a[1] = args[1];
            System.out.println("Port Used: "+ a[0]+" text file used: "+ a[1]);
        }
        else{
            System.out.println("Default port 3000 and default file used is cookies.txt");
        }
        return a;
    }

    public static void main(String[] args) {
        String[] parsedArg = parseArg(args);
        int port = Integer.parseInt(parsedArg[0]);
        String cookiefile = parsedArg[1];
        Server server = new Server(port, cookiefile);
        try {
            server.startServer();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}