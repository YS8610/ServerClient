package com.day4;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client 
{
    //function to send msg from client to server
    public static void sendmsg(BufferedWriter bufferedWriter, String msg)
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

    public static void main(String[] args) 
    {

        String ip = "127.0.0.1";
        int portNo = 3000;

        if (null!=args && args.length>0) //check for command line argument
        {
            String[] ipPort = args[0].split(":");
            if (ipPort.length==2)
            {
                ip = ipPort[0];
                portNo = Integer.parseInt(ipPort[1]);
            }
        }
        System.out.println("Connecting to ip: "+ip + " port: " + portNo);

        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        
        try 
        {
            socket = new Socket(ip,portNo); //Connect to server 
            System.out.println("Connected. Accepting input...");
            inputStreamReader = new InputStreamReader(socket.getInputStream()); //read from server
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream()); //send stuff to server

            bufferedReader = new BufferedReader(inputStreamReader); // handle the data more efficient
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            
            Scanner scanner = new Scanner(System.in);
            
            while (true)
            {
                String msg = scanner.nextLine();
                sendmsg(bufferedWriter, msg);
                String msgfromServer = bufferedReader.readLine();
                if (msg.equalsIgnoreCase("close"))
                {
                    scanner.close();
                    break;
                }
                else if (msgfromServer.length()>=11 && msgfromServer.substring(0,11).equals("cookie-text"))
                {
                    System.out.println("Server: " + msgfromServer.substring(11).trim());
                }
                else
                {
                    System.out.println("Server: " + msgfromServer);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (socket!=null) socket.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (outputStreamWriter != null) outputStreamWriter.close();
                if (bufferedReader != null) bufferedReader.close();
                if (bufferedWriter != null) bufferedWriter.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
