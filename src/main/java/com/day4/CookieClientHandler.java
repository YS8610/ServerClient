package com.day4;

import java.io.*;
import java.net.*;


class CookieClientHandler implements Runnable
{
    Socket socket;
    Cookie cookie;
    
    public CookieClientHandler(Socket socket, Cookie cookie){
        this.socket = socket;
        this.cookie = cookie;
    }

    public void run(){
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try{
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
}
