package com.example;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class NumberMemoryAi implements KeyListener{
    private Robot r;
    private String word;
    int times = 0;

    public static void main(String[] args) {
        try {
            NumberMemoryAi clicker = new NumberMemoryAi();
            clicker.setupKeyListener();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public NumberMemoryAi() throws AWTException {
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
                char[] c = word.toCharArray();
                Thread.sleep(2000 + (1000 * times));
                for(char x : c){
                    if(x != ' '){
                        r.keyPress(x);
                        Thread.sleep(30);
                        r.keyRelease(x);
                    }
                }
                r.mouseMove(620, 500);
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(30);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(1000);
                r.mouseMove(640, 600);
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(30);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                times++;
            } catch (IOException | TesseractException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }

    public void getWordInFrame() throws IOException, TesseractException {
        Rectangle rect;
        if(times < 17){
            rect = new Rectangle(20, 345, 1200, 120);
        } else {
            System.out.println("kajsfkjf");
            rect = new Rectangle(20, 317, 1200, 210);
        }
        //Rectangle rect = new Rectangle(20, 345, 1200, 120);
        BufferedImage image = r.createScreenCapture(rect);
        BufferedImage modifiedImage = convertRgbToBlack(image, new Color(79, 135, 209));
        File output = new File("C:/users/rkone/personal projects/human_benchmark-1/wordtestpics/screenshot.jpg");
        ImageIO.write(modifiedImage, "jpg", output);
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/users/rkone/personal projects/human_benchmark-1/Tess4j/tessdata/");
        tesseract.setLanguage("eng");
        tesseract.setVariable("tessedit_char_whitelist", "0123456789");
        tesseract.setVariable("tessedit_char_blacklist", " ");
        tesseract.setVariable("enable_new_segsearch", "0");
        String text = tesseract.doOCR(output);
        text = text.trim();
        text = text.replace("\n", "");
        text = text.replace(" ", "");
        //output.delete();
        System.out.println(text);
        word = text;
    }

    public BufferedImage convertRgbToBlack(BufferedImage originalImage, Color targetColor) {
        BufferedImage modifiedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                Color pixelColor = new Color(originalImage.getRGB(x, y));

                // Check if the pixel color matches the target color
                if (pixelColor.equals(targetColor)) {
                    modifiedImage.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    modifiedImage.setRGB(x, y, originalImage.getRGB(x, y));
                }
            }
        }

        return modifiedImage;
    }
}
