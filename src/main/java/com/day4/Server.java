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
    // function to send msg from server to client
    static public void sendmsg(BufferedWriter bufferedWriter, String msg)
    {
        try 
        {
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) throws UnknownHostException, IOException 
    {
        int portNo = 3000;
        String fileName = "cookies.txt";

        if (null!=args && args.length>0) //check for command line argument
        {
            String[] portFile = args[1].split(" ");
            if (portFile.length==2)
            {
                portNo = Integer.parseInt(portFile[0]);
                fileName = portFile[1];
            }
            else
            {
                System.out.println("Default portno: "+ portNo+"\n" + fileName+" will be used ");
            }
        }

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
                    
                    
                    if (msgfromclient.equalsIgnoreCase("close"))
                    {
                        break;
                    }
                    else if (msgfromclient.equalsIgnoreCase("get-cookies"))
                    {
                        String randomCookie = listofCookies.get(new Random().nextInt(listofCookies.size()));
                        sendmsg(bufferedWriter, "cookie-text " + randomCookie);
                    }
                    else
                    {
                        sendmsg(bufferedWriter, "Wrong command. Only 2 commands(get-cookies and close) are accepted.");
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
