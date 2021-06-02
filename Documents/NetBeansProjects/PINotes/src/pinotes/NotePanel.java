/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinotes;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.bson.Document;

/**
 *
 * @author Irene NB
 */
class NotePanel extends JPanel {

    BufferedImage image;
    JTextArea textArea;
    List listNo;
    List listDate;
    List listNote;
    List list;
    int y;

    public NotePanel() {
        String root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "pinotes" + File.separator + "PINotes_Image" + File.separator + "Home" + File.separator;
        setBackground(new Color(255, 248, 232));
        setLayout(new FlowLayout(FlowLayout.LEADING, 50, 60));//設定FlowLayout每個element之間的間隔
        try {
            image = ImageIO.read(new File(root+"pin.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void getMongoDB() {
        listNo = new ArrayList<String>(Arrays.asList());
        listDate = new ArrayList<String>(Arrays.asList());
        listNote = new ArrayList<String>(Arrays.asList());
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("testDB");
        MongoCollection<Document> col = db.getCollection("PINotes");
        MongoCursor<Document> cursor = col.find().iterator();
        new Thread() {
            public void run() {
                while (cursor.hasNext()) {
                    Document pin = cursor.next();
                    int num = pin.getInteger("No");
                    String date = pin.getString("Date");
                    String note = pin.getString("Note");
                    listNo.add(String.valueOf(num));
                    listDate.add(date);
                    listNote.add(note);
                }
            }
        }.start();
        RandomNum(col);
        y = list.size() * 210 + 10;
        setPreferredSize(new Dimension(1500, y));
    }

    public void RandomNum(MongoCollection col) {
        //隨機順序排列
        list = new ArrayList();
        int size = (int) col.countDocuments();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int j = 0; j < list.size(); j++) {
            textArea = new JTextArea(15, 35) {//在textArea的左上角畫pin.png
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 5, 10, 35, 35, this);
                    repaint();
                }
            };
            add(textArea);
            add(new JScrollPane(textArea));
            textArea.setLineWrap(true);
            textArea.setFont(new Font("標楷體", Font.BOLD, 20));
            textArea.setForeground(Color.BLACK);
            textArea.setBackground(setColor());
            textArea.setEditable(false);
            textArea.append("\n" + "\n");
            textArea.append(" " + "No: " + listNo.get((int) list.get(j)) + "\n");
            textArea.append(" " + "Date: " + listDate.get((int) list.get(j)) + "\n");
            textArea.append(" " + "Note: " + "\n\n "+listNote.get((int) list.get(j)) + "\n");
            add(Box.createHorizontalStrut(30));//textArea 後接的間隔
        }
    }
//設定便利貼顏色隨機

    public Color setColor() {
        Color yellow = new Color(255, 255, 185);
        Color blue = new Color(184, 241, 237);
        Color green = new Color(184, 241, 204);
        Color pink = new Color(250, 220, 220);
        Color purple = new Color(218, 218, 252);
        Color white = new Color(255, 255, 255);
        Color color[] = {yellow, blue, green, pink, purple, white};
        Random ran = new Random();
        //隨機取一顏色
        int randomValue = 0 + (ran.nextInt(color.length) - 0);
        return color[randomValue];
    }
}
