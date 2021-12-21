package com.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class Cookie {
    String dir;

    public Cookie(){
        this.dir = "./cookie.txt";
    }
    public Cookie(String dir){
        Path file = Path.of(dir);
        if (Files.exists(file) && !Files.isDirectory(file)){
            this.dir = dir;
        }
        else{
            this.dir = "./cookie.txt";
        }
    }
    public String randomCookie(){
        Random random = new Random();
        Path file = Path.of(this.dir);
        List<String> cookietxt;
        String randomCookie="";
        try {
            cookietxt = Files.readAllLines(file);
            randomCookie = cookietxt.get(random.nextInt(cookietxt.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return randomCookie;
    }
}