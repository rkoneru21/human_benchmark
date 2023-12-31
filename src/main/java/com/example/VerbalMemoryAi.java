package com.example;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class VerbalMemoryAi implements KeyListener {
    private Robot r;
    private String word;
    private HashSet<String> words;

    public static void main(String[] args) {
        try {
            VerbalMemoryAi clicker = new VerbalMemoryAi();
            clicker.setupKeyListener();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public VerbalMemoryAi() throws AWTException {
        this.r = new Robot();
        this.words = new HashSet<String>();
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
                this.getWordInFrame();
                
                if(this.words.contains(word)){
                    r.mouseMove(600, 500);
                    r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(30);
                    r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(50);
                } else {
                    words.add(word);
                    r.mouseMove(700, 500);
                    r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(30);
                    r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(50);
                }
            } catch (IOException | TesseractException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }

    private void getWordInFrame() throws IOException, TesseractException {
        Rectangle rect = new Rectangle(300, 375, 680, 100);
        BufferedImage image = r.createScreenCapture(rect);
        File output = new File("C:/users/rkone/personal projects/human_benchmark-1/wordtestpics/screenshot.jpg");
        ImageIO.write(image, "jpg", output);
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/users/rkone/personal projects/human_benchmark-1/Tess4j/tessdata/");
        tesseract.setLanguage("eng");
        tesseract.setVariable("enable_new_segsearch", "0");
        String text = tesseract.doOCR(output);
        output.delete();
        System.out.println(text);
        word = text;
    }
}
