package com.example;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JFrame;

import java.awt.*;


public class ReactionTimeAi implements KeyListener {
    private Robot r;
    private Color c;
    

    public static void main(String[] args) {
        try {
            ReactionTimeAi clicker = new ReactionTimeAi();
            clicker.setupKeyListener();
        } catch (AWTException e) {
            e.printStackTrace();
        }  
    }

    public ReactionTimeAi() throws AWTException {
        this.r = new Robot();
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
            this.getColorInFrame();
            if(c.getRed() == 112){
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                //Thread.sleep(20);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(500);
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                //Thread.sleep(20);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
        }
    } 

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }

    private void getColorInFrame() {
        c = r.getPixelColor(200, 200);
    }
}
