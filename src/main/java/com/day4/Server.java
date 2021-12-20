package com.day4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
    public void startServer(){
        Socket socket=null;
        BufferedReader bufferedReader=null;
        BufferedWriter bufferedWriter=null;
        Cookie cookie = new Cookie(this.dir);
        try{
            serverSocket = new ServerSocket(this.port);
            System.out.println("Server is starting at port "+ this.port);
            socket = serverSocket.accept();
            bufferedWriter = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()) );
            bufferedReader = new BufferedReader( new InputStreamReader(socket.getInputStream()) );
            System.out.println("Client connected");
            sendmsg(bufferedWriter,"Welcome to Fortune cookie server. To end connection, pls type close");
            
            while(socket.isBound() && !socket.isClosed()){
                String msgfromClient =  bufferedReader.readLine();
                System.out.println("Client: "+msgfromClient);
                if (msgfromClient.trim().toLowerCase().equals("close")){
                    break;
                }
                if (msgfromClient.trim().toLowerCase().equals("get-cookies")){
                    sendmsg(bufferedWriter, "cookie-text "+cookie.randomCookie());
                }
                else{
                    sendmsg(bufferedWriter, msgfromClient );
                }
            }
            bufferedWriter.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            closeAll(bufferedReader,bufferedWriter,socket);
        }
    }

    public void sendmsg(BufferedWriter bufferedWriter, String msgtoClient){
        try {
            bufferedWriter.write(msgtoClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeAll(BufferedReader bufferedReader, BufferedWriter bufferedWriter, Socket socket){
        try{
            if (bufferedReader!=null){bufferedReader.close();}
            if (bufferedWriter!=null){bufferedWriter.close();}
            if (!socket.isClosed()){socket.close();}
        }
        catch (IOException e){
            e.printStackTrace();
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
        server.startServer();
    }
}