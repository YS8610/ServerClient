package com.day4;

import java.io.*;
import java.net.*;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Server 
{

    public static void main( String[] args ) throws UnknownHostException, IOException 
    {
        Cookie cookietxt = new Cookie("./cookies.txt");
        List<String> listofCookies = cookietxt.readCookie();
        
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = null;
        
        serverSocket = new ServerSocket(3000); //listening to port 3000
        System.out.println("Server started. Waiting for connection...");
        
        while (true)
        {
            try 
            {
                socket = serverSocket.accept(); //waiting for client to connect
                System.out.println("Client connected..");
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                
                while (true)
                {
                    String msgfromclient = bufferedReader.readLine(); //receive msg from client
                    System.out.println("Client: " + msgfromclient);
                    
                    // String randomCookie = listofCookies.get(5);
                    String randomCookie = listofCookies.get(new Random().nextInt(listofCookies.size()));
                    bufferedWriter.write("echo " + randomCookie);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    
                    if (msgfromclient.equalsIgnoreCase("close"))
                    {
                        break;
                    }
                }

                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();
                serverSocket.close();
                break;

            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
