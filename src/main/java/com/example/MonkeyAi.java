package com.example;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
//import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class MonkeyAi implements KeyListener{
    private Robot r;
    //private String word;
    int times = 0;
    boolean[][] arr;

    public static void main(String[] args) {
        try {
            MonkeyAi clicker = new MonkeyAi();
            clicker.setupKeyListener();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public MonkeyAi() throws AWTException {
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
                r.mouseMove(620, 600);
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
        r.mouseMove(1200, 200);
        Thread.sleep(200);
        Rectangle rect = new Rectangle(2, 160, 1250, 520);
        BufferedImage image = r.createScreenCapture(rect);
        LinkedHashMap<BufferedImage, Entry<Integer, Integer>> images = new LinkedHashMap<>();
        this.arr = new boolean[image.getHeight()][image.getWidth()];
        for(int i = 0; i < this.arr.length; i++){
            for(int j = 0; j < this.arr[0].length; j++){
                //System.out.println(".");
                if(!this.arr[i][j]){
                    Color c = new Color(image.getRGB(j, i));
                    
                    // 80 tall
                    // 80 wide
                    if(c.getRed() == 97 && c.getGreen() == 147 && c.getBlue() == 214 && i < 439){
                        System.out.println(i + " " + j);
                        //System.out.println("!");
                        for(int k = i ; k < i + 80; k++){
                            for(int f = j - 8; f < j + 73; f++){
                                
                                this.arr[k][f] = true;
                            }
                        }
                        BufferedImage newImage = r.createScreenCapture(new Rectangle(j - 6, i + 160, 80, 80));
                        Entry<Integer, Integer> gg = Map.entry(j - 8 + 2, i + 160);
                        images.put(newImage, gg);
                    }
                }
            }
        }
        ArrayList<Entry<BufferedImage, Integer>> entries = new ArrayList<Entry<BufferedImage, Integer>>();
        File output = new File("C:/users/rkone/personal projects/human_benchmark/wordtestpics/screenshot.jpg");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/users/rkone/personal projects/human_benchmark/Tess4j/tessdata/");
        tesseract.setLanguage("eng");
        tesseract.setVariable("tessedit_char_whitelist", "0123456789");
        tesseract.setVariable("tessedit_char_blacklist", " ");
        tesseract.setVariable("enable_new_segsearch", "0");
        for(BufferedImage key : images.keySet()){
            ImageIO.write(key, "jpg", output);
            String text = tesseract.doOCR(output);
            System.out.println(text);
            System.out.println(images.get(key).getKey() + "  " + images.get(key).getValue());
            text = text.trim();
            text = text.replace(" ", "");
            Entry<BufferedImage, Integer> entry = Map.entry(key, Integer.valueOf(text));
            //AbstractMap.SimpleEntry<BufferedImage, Integer> entry = new AbstractMap.SimpleEntry<BufferedImage, Integer>();
            entries.add(entry);
        }
        Collections.sort(entries, new Comparator<Entry<BufferedImage, Integer>>() {
            public int compare(Entry<BufferedImage, Integer> a, Entry<BufferedImage, Integer> b){
                return a.getValue().compareTo(b.getValue());
            }
        });
        for(Entry<BufferedImage, Integer> e : entries){
            for(BufferedImage key : images.keySet()){
                if(e.getKey() == key){
                    int coordX = images.get(key).getKey();
                    int coordY = images.get(key).getValue();
                    r.mouseMove(coordX + 30, coordY + 30);
                    r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(30);
                    r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    //Thread.sleep(200);
                }
            }
        }
        
        r.mouseMove(620, 550);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(30);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    
}
