package com.day4;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Random;

class CookieClientHandler implements Runnable
{
    private Socket socket;
    private List<String> listofCookies;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public CookieClientHandler(Socket socket, String filename)
    {
        try 
        {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Cookie cookie = new Cookie(filename);
            listofCookies = cookie.readCookie();
        } 
        catch (IOException e) 
        {
            closeEverything(socket, bufferedReader, bufferedWriter);
            e.printStackTrace();
        }
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        try
        {
            if (bufferedReader!=null) bufferedReader.close();
            if (bufferedWriter!=null) bufferedWriter.close();
            if (socket!=null) socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // function to send msg from server to client
    public void sendmsg(BufferedWriter bufferedWriter, String msg)
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

    @Override
    public void run()
    {
        while (socket.isConnected())
        {
            try
            {
                while (true)
                {
                    String msgfromclient = bufferedReader.readLine(); //receive msg from client
                    System.out.println("Client: " + msgfromclient);
                    
                    if (msgfromclient.equalsIgnoreCase("close"))
                    {
                        bufferedReader.close();
                        bufferedWriter.close();
                        socket.close();
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
            } 
            catch (IOException e)
            {
                closeEverything(socket, bufferedReader, bufferedWriter);
                e.printStackTrace();
                break;
            }
        }
    }
}
