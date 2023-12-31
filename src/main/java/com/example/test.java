package com.example;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class test {
    public static void main(String[] args){
        Robot r;
        try {
            r = new Robot();
            r.mouseMove(620, 570);
            
            //1050
            //150, 420, 950, 130
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
