package com.example;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
//import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Map.Entry;

//import javax.imageio.ImageIO;
import javax.swing.JFrame;

//import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class VisualMemoryAi implements KeyListener{
    private Robot r;
    //private String word;
    int times = 0;
    boolean[][] arr;

    public static void main(String[] args) {
        try {
            VisualMemoryAi clicker = new VisualMemoryAi();
            clicker.setupKeyListener();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public VisualMemoryAi() throws AWTException {
        this.r = new Robot();
        //this.words = new HashSet<String>();
    }

    public void setupKeyListener() {
        System.out.println("Press 'A' to start the auto clicker.");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this example
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a') {
            System.out.println("Key 'A' pressed. Starting the auto clicker...");
            try {
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(200);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(50);
                r.mouseMove(620, 570);
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(30);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                this.startLogic();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public void startLogic() throws InterruptedException{
        long startTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - startTime;
        while(elapsedTime < 1000){
            try {
                Thread.sleep(100);
                this.getWordInFrame();
                
            } catch (IOException | TesseractException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }

    public void getWordInFrame() throws IOException, TesseractException, InterruptedException {
        int count = 0;
        //Thread.sleep(200);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rect = new Rectangle((int)screenSize.getWidth(), (int)screenSize.getHeight());
        BufferedImage image = r.createScreenCapture(rect);
        //Color def = new Color(image.getRGB(100, 200));
        for(int i = 280; i < 640; i++){
            Color c = new Color(image.getRGB(450, i));
            if(c.getRed() == 79 && c.getGreen() == 135 && c.getBlue() == 209){
                count ++;
                i += 20;
                if(times > 50){
                    i -= 10;
                }
            }
        }
        System.out.println("num spaces:" + count);
        Thread.sleep(700);
        rect = new Rectangle((int)screenSize.getWidth(), (int)screenSize.getHeight());
        image = r.createScreenCapture(rect);
        this.arr = new boolean[count + 1][count + 1];
        for(int i = 0; i < this.arr.length; i++){
            for(int j = 0; j < this.arr[0].length; j++){
                Color colorOfSquare = new Color(image.getRGB(450 + (j *(396/(count + 1))), 280 + (i * (396/(count + 1)))));
                if(colorOfSquare.getRed() == 255){
                    this.arr[i][j] = true;
                    System.out.println("true");
                } else {
                    System.out.println("false");
                }
            }
        }
        Thread.sleep(1000);
        for(int i = 0; i < this.arr.length; i++){
            for(int j = 0; j < this.arr[0].length; j++){
                if(this.arr[i][j]){
                    r.mouseMove(450 + (j *(396/(count + 1))), 280 + (i * (396/(count + 1))));
                    r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(30);
                    r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
            }
        }
        times++;
        Thread.sleep(1000);
        
        
    }
}
