/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinotes;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Irene NB
 */
class StoryPanel extends JPanel {

    String root;
    BufferedImage image;
    JButton nextBtn;
    JButton frontBtn;
    List list;
    JTextArea textArea;
    int j;

    public StoryPanel() {
        setBackground(new Color(255, 248, 232));
        setLayout(new FlowLayout(FlowLayout.LEADING, 50, 60));//設定FlowLayout每個element之間的間隔
    }

    public void displayStory() {
        list = new ArrayList(); 
        String root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "pinotes" + File.separator + "PINotes_Image" + File.separator + "story" + File.separator;
        File folder = new File(root); 
        File[] filesInFolder = folder.listFiles(); // return folder內的內容
        for (File file : filesInFolder) { 
            if (!file.isDirectory()) { 
                list.add(new String(file.getName())); 
            }
        }

        nextBtn = new JButton("next");
    
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (j < list.size() - 1) {
                    frontBtn.setEnabled(true);
                    j += 1;
                } else {
                    nextBtn.setEnabled(false);
                    
                }
            }
        });
        frontBtn = new JButton("front");
        
        frontBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (j > 0) {
                    nextBtn.setEnabled(true);
                    j -= 1;
                } else {
                    frontBtn.setEnabled(false);
                }
            }
        });
      
        textArea = new JTextArea(30, 85) {//在textArea內設置圖片
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File(root + list.get(j).toString())), 20, 20, 800, 450, this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                repaint();
            }
        };
        textArea.setEditable(false);
        add(frontBtn);
        add(textArea);
        add(nextBtn);
        repaint();

    }
}
