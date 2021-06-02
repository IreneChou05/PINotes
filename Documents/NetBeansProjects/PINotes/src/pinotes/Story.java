/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinotes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author Irene NB
 */
class Story extends JFrame {

    JPanel panel;

    public Story(final BufferedImage image) {
        
        super("");
        setBackground(Color.WHITE);
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 50, 50, 800, 450, this);
                repaint();
            }
        };
        add(panel);
      
        setSize(920, 600);
        setVisible(true);
        
    }
}
