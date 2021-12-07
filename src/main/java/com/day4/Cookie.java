package com.day4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Cookie
{
    public String readCookie(String filePath) throws FileNotFoundException, IOException
    {
        List<String> list1 = new ArrayList<>();
        list1 = Files.readAllLines(Paths.get(filePath));
        return list1.get(0);
    }
}
