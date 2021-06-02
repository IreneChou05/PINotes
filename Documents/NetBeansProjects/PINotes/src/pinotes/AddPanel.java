/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinotes;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.bson.Document;

/**
 *
 * @author Irene NB
 */
class AddPanel extends JPanel {

    JTextArea textArea;
    Plane plane;
    BufferedImage image;
    BufferedImage imageStory;
    Gift gift;
    int num;
    String root1;

    public AddPanel() {
        setBackground(new Color(255, 248, 232));
        String title = "我做了什麼好棒棒的事呢~";
        Font font = new Font("標楷體", Font.BOLD, 24);
        JLabel text = new JLabel(title);
        text.setFont(font);
        text.setForeground(Color.BLACK);

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setFont(font);
        textArea.setForeground(Color.BLACK);
        JButton addBtn = new JButton("SEND");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMongoDB(textArea);
                //加入紙飛機動畫
                plane = new Plane();
                addBtn.setEnabled(false);
                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            addBtn.setEnabled(true);
                            gift = new Gift(imageStory);
                        } catch (InterruptedException e) {
                        }
                    }
                }.start();

            }
        });
        //設定ImageLabel圖案
        root1 = System.getProperty("user.dir") + File.separator + "src" + File.separator + "pinotes" + File.separator + "PINotes_Image" + File.separator + "Home" + File.separator + "letter" + File.separator;
        try {
            image = ImageIO.read(new File(RandomInmage()));//取隨機image
        } catch (IOException ex) {
            Logger.getLogger(AddPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageLabel label = new ImageLabel(new ImageIcon(image.getScaledInstance(360, 450, Image.SCALE_SMOOTH)));//設置ImageIcon大小
        setLayout(null);//setLayout(null)才可以使用setBounds

        textArea.setBounds(600, 100, 800, 400);
        addBtn.setBounds(950, 550, 100, 30);
        label.setLocation(100, 100);
        text.setBounds(600, 10, 300, 100);
        add(textArea);
        add(addBtn);
        add(label);
        add(text);
        repaint();

    }

    public void updateMongoDB(JTextArea textarea) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("testDB");
        MongoCollection<Document> col = db.getCollection("PINotes");
        new Thread() {
            @Override
            public void run() {
                String pin = textarea.getText();
                Document document = new Document();

                num = (int) col.countDocuments();
                document.append("No", num + 1)
                        .append("Date", getTime())
                        .append("Note", pin);
                col.insertOne(document);
                textarea.setText("");
                insertImage();//將雲端image下載至本地端
                //取得已存入之imageStory
                try {
                    String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "pinotes" + File.separator + "PINotes_Image" + File.separator + "story" + File.separator ;
                    imageStory = ImageIO.read(new File(path+String.valueOf(num + 1) + ".png"));
                } catch (IOException ex) {
                    try {
                        imageStory = ImageIO.read(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "pinotes" + File.separator + "PINotes_Image" + File.separator + "Home" + File.separator+"extra.png"));
                    } catch (IOException ex1) {
                        Logger.getLogger(AddPanel.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date current = new Date();
        return sdf.format(current);
    }

    //新增後可取得一個獎勵image，將其存入本地端
    public void insertImage() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("PINotes");

        GridFS gridFS = new GridFS(db);
        //Display Image document stored in collection in MongoDB
        DBCursor cursor = gridFS.getFileList();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        //Retrieve image from collection in MongoDB 
        List<GridFSDBFile> imageForOutput = gridFS.find(String.valueOf(num + 1) + ".png");//這邊之後也要改成String.valueOf(num)+".png"
        System.out.println(String.valueOf(num + 1) + ".png");
        if (imageForOutput.size() != 0) {
            try {
                String root2 = System.getProperty("user.dir") + File.separator + "src" + File.separator + "pinotes" + File.separator + "PINotes_Image" + File.separator + "story" + File.separator;
                imageForOutput.get(0).writeTo(root2 + String.valueOf(num + 1) + ".png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //取得隨機image
    public String RandomInmage() {
        String one = root1 + "1.png";
        String two = root1 + "2.png";
        String three = root1 + "3.png";
        String four = root1 + "4.png";
        String five = root1 + "5.png";
        
        String path[] = { one, two, three, four, five};
        Random ran = new Random();
        int randomValue = 0 + (ran.nextInt(path.length) - 0);
        return path[randomValue];
    }

}
