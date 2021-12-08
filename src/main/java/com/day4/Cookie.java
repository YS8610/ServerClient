package com.day4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Cookie
{
    String cookieDir;
    public Cookie(String dir)
    {
        this.cookieDir = dir;
    }
    
    public List<String> readCookie() throws FileNotFoundException, IOException
    {
        List<String> cookieList = new ArrayList<>();
        cookieList = Files.readAllLines(Paths.get(this.cookieDir));
        return cookieList;
    }
}
