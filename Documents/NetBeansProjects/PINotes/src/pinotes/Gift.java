/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinotes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Irene NB
 */
class Gift extends JFrame {

    enum State {
        RIGHT, LEFT, MIDDLE
    }

    BufferedImage imageGift;//禮物盒的image
    Timer timer;
    Timer timerMove;
    private float alpha = 0f;
    JPanel panel;
    State state = State.MIDDLE;
    int x = 800;
    Point current;
    Story story;

    public Gift(final BufferedImage image) {
        super("");
        String root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "pinotes" + File.separator + "PINotes_Image" + File.separator + "Home" + File.separator;
        try {
            imageGift = ImageIO.read(new File(root + "giftbox.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.drawImage(imageGift, x, 300, 300, 300, this);
                //點擊imageGift後 出現image
            }
        };
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        add(panel);
        panel.setOpaque(false); // make the background transparent

        //設定JFrame背景透明
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(d.width + 380, d.height + 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        timerMove = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateShake();
                repaint();
            }
        });
        //設fade in 的時間
        timer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.1f;
                if (alpha > 1) {
                    alpha = 1;
                    timer.stop();
                    //晃動動畫
                    timerMove.start();
                }
                repaint();
            }
        });
        //延遲2秒後開始
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    timer.start();
                } catch (InterruptedException e) {
                }
            }
        }.start();
        

        MouseInputAdapter handler = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                story = new Story(image);
                dispose();
            }
        };
        this.addMouseListener(handler);
        this.addMouseMotionListener(handler);
    }

    //左右晃動動畫
    private void animateShake() {
        switch (state) {
            case MIDDLE:
                x += 5;
                if (x > 820) {
                    state = State.LEFT;
                    break;
                }
                break;
            case LEFT:
                x -= 5;
                if (x < 780) {
                    state = State.RIGHT;
                    break;
                }
                break;
            case RIGHT:
                x += 5;
                if (x > 800) {
                    state = State.MIDDLE;
                    x = 800;
                    timerMove.stop();
                    break;
                }
                break;
        }
    }
}
