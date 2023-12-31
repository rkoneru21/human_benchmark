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
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import net.sourceforge.tess4j.TesseractException;

public class SequenceMemoryAi implements KeyListener{
    private Robot r;
    //private String word;
    int times = 0;
    boolean[][] arr;

    public static void main(String[] args) {
        try {
            SequenceMemoryAi clicker = new SequenceMemoryAi();
            clicker.setupKeyListener();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public SequenceMemoryAi() throws AWTException {
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
        int count = 1;
        Thread.sleep(200);
        ArrayList<Integer> list = new ArrayList<Integer>();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rect = new Rectangle((int)screenSize.getWidth(), (int)screenSize.getHeight());
        BufferedImage image = r.createScreenCapture(rect);
        Color c;
        for(int i = 0; i < count; i++){
            c = new Color(image.getRGB(496, 320));
            if(c.equals(Color.WHITE)){
                list.add(1);
            }
            c = new Color(image.getRGB(620, 320));
            if(c.equals(Color.WHITE)){
                list.add(2);
            }
            c = new Color(image.getRGB(760, 320));
            if(c.equals(Color.WHITE)){
                list.add(3);
            }
            c = new Color(image.getRGB(496, 460));
            if(c.equals(Color.WHITE)){
                list.add(4);
            }
            c = new Color(image.getRGB(620, 460));
            if(c.equals(Color.WHITE)){
                list.add(5);
            }
            c = new Color(image.getRGB(760, 460));
            if(c.equals(Color.WHITE)){
                list.add(6);
            }
            c = new Color(image.getRGB(496, 600));
            if(c.equals(Color.WHITE)){
                list.add(7);
            }
            c = new Color(image.getRGB(620, 600));
            if(c.equals(Color.WHITE)){
                list.add(8);
            }
            c = new Color(image.getRGB(760, 600));
            if(c.equals(Color.WHITE)){
                list.add(9);
            }
        }
        Thread.sleep(700);
        for(Integer i : list){
            System.out.println(i);
        }
        for(Integer i : list){
            int xpos = i % 3;
            int ypos = (i/3) + 1;
            r.mouseMove(480 + ((xpos - 1) * 140), 320 + ((ypos - 1) * 140));
            r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(30);
            r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
        Thread.sleep(300);
        count++;
    }
}
