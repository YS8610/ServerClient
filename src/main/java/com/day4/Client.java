package com.day4;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client 
{
    String ip;
    int port;

    //Constructor
    public Client(){
        this.port = 3000;
        this.ip = "127.0.0.1";
    }
    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

//method
    private void writemsg(BufferedWriter bufferedWriter, String msgtoServer){
        try {
            bufferedWriter.write(msgtoServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startClient(){
        Socket socket =null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        Scanner scan = new Scanner(System.in);

        try {
            socket = new Socket(this.ip, this.port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(true){
                String msgfromServer = bufferedReader.readLine();
                parseServerMsg(msgfromServer);
                String msgtoServer = scan.nextLine();
                writemsg(bufferedWriter, msgtoServer);
                if ("close".equals(msgtoServer)){
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try{
                scan.close();
                if (bufferedReader !=null){bufferedReader.close();}
                if (bufferedWriter !=null){bufferedWriter.close();}
                if (!socket.isClosed()){socket.close();}
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    private void parseServerMsg(String msgfromServer){
        if (msgfromServer.length()>=11 && msgfromServer.substring(0, 11).equals("cookie-text")){
            System.out.println("Server: "+msgfromServer.substring(11).trim());
        }
        else{
            System.out.println("Server: "+msgfromServer);
        }
    }

    private static String[] parseArg(String[] args) {
        String[] a = {"127.0.0.1","3000"};
        if (args.length==1 && args[0].contains(":")){
            a[0] = args[0].split(":")[0];
            a[1] = args[0].split(":")[1];
        }
        else{
            System.out.println("Ip connected: "+ a[0]+ "\nPort Used: "+a[1]);
        }
        return a;
    }

    public static void main(String[] args) {
        String[] parsedArgs = parseArg(args);
        String ip = parsedArgs[0];
        int port;
        try{
            port = Integer.parseInt( parsedArgs[1]);
        }
        catch (NumberFormatException e){
            port = 3000;
            System.out.println("Port used will be " + port);
        }
        Client client = new Client(ip,port);
        client.startClient();
    }
}