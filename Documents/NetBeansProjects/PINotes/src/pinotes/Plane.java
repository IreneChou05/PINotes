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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Irene NB
 */
class Plane extends JFrame {

    BufferedImage image;
    Timer timer;
    Timer timerMove;
    private float alpha = 0f;
    private int x = 800;
    private int y = 350;
    JPanel panel;

    public Plane() {
        super("");
        try {
            String root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "pinotes" + File.separator + "PINotes_Image" + File.separator + "Home" + File.separator;
            image = ImageIO.read(new File(root+"plane.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.drawImage(image, x, y, 300, 300, this);
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
        //設移動動畫的時間
        timerMove = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x += 20;
                y -= 5;
                repaint();
            }
        });
        //設fade in 的時間
        timer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.05f;
                if (alpha > 1) {
                    alpha = 1;
                    timer.stop();
                    timerMove.start();//當fade in 完成，開始移動
                    //延遲2秒後暫停
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                timerMove.stop();
                                dispose();//2秒後自動關閉
                            } catch (InterruptedException e) {
                            }
                        }
                    }.start();
                }
                repaint();
            }
        });
        timer.start();
    }
}
