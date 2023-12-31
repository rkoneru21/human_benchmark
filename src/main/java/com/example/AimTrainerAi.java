package com.example;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.*;

public class AimTrainerAi implements KeyListener{
    private Robot r;
    

    public static void main(String[] args) {
        try {
            AimTrainerAi clicker = new AimTrainerAi();
            clicker.setupKeyListener();
        } catch (AWTException e) {
            e.printStackTrace();
        }  
    }

    public AimTrainerAi()  throws AWTException {
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
                r.mouseMove(650, 450);
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(200);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                
                this.startLogic();
            } catch (InterruptedException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    
    public void startLogic() throws InterruptedException, IOException{
        int x = 0;
        while(x < 60){
            Rectangle rect = new Rectangle(200, 300, 900, 300);
            BufferedImage image = r.createScreenCapture(rect);
            File output = new File("C:/users/rkone/personal projects/wordtestpics/screenshot.jpg");
            ImageIO.write(image, "jpg", output);
            Point p = this.getColorChangeCoordinates(image);
            r.mouseMove((int)p.getX() + 230, (int)p.getY() + 310);
            r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(10);
            x++;
        }
    } 

    public Point getColorChangeCoordinates(BufferedImage image) {
            int width = image.getWidth();
            int height = image.getHeight();

            // Define the initial color
            int initialColor = image.getRGB(0, 0);

            // Iterate through each pixel
            for (int x = 0; x < width; x+=10) {
                for (int y = 0; y < height; y+=10) {
                    int currentColor = image.getRGB(x, y);

                    // Compare with the initial color
                    if (currentColor != initialColor && y != 350) {
                        // Color change detected, return the coordinates
                        return new Point(x, y);
                    }
                }
            }
            return null;
        }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }
}
