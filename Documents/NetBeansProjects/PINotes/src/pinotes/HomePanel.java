/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinotes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Irene NB
 */
class HomePanel extends JPanel {

    enum State {
        ONE, TWO, THREE, FOUR, FIVE
    }

    BufferedImage image;
    BufferedImage img_PINotes;
    int size;
    int y = -450;
    Timer timerPost;
    Timer timerWord;
    State state = State.ONE;

    public HomePanel() {
        setBackground(new Color(255, 248, 232));
        try {
            String root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "pinotes" + File.separator + "PINotes_Image" + File.separator + "Home" + File.separator;

            image = ImageIO.read(new File(root + "post_it.png"));
            img_PINotes = ImageIO.read(new File(root + "PINotes.png"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        timerWord = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateFSM();
                repaint();
            }
        });

        timerPost = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size += 5;
                if (size > 250) {
                    size = 250;
                    timerPost.stop();
                    timerWord.start();
                }
                repaint();
            }
        });
        //延遲2秒後開始
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    timerPost.start();
                } catch (InterruptedException e) {
                }
            }
        }.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 250, 200, size, size, this);
        g.drawImage(img_PINotes, 400, y, 800, 400, this);
        repaint();
    }

    //上下跳動動畫
    private void animateFSM() {
        switch (state) {
            case ONE:
                y += 20;
                if (y > 160) {
                    state = State.TWO;
                    break;
                }
                break;
            case TWO:
                y -= 5;
                if (y < 130) {
                    state = State.THREE;
                    break;
                }
                break;
            case THREE:
                y += 5;
                if (y > 160) {
                    state = State.FOUR;
                    break;
                }
                break;
            case FOUR:
                y -= 5;
                if (y < 150) {
                    state = State.FIVE;
                    break;
                }
                break;
            case FIVE:
                y += 5;
                if (y > 160) {
                    y = 160;
                    timerWord.stop();
                    break;
                }
                break;
        }

    }

}
