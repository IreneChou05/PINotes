/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinotes;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

/**
 *
 * @author Irene NB
 */
public class PINFrame extends JFrame {

    BufferedImage image;
    HomePanel panel = new HomePanel();
    NotePanel notePanel = new NotePanel();
    AddPanel addPanel = new AddPanel();
    StoryPanel storyPanel = new StoryPanel();
    private CardLayout card;

    public PINFrame() {
        super("PINotes");
        
        Page("home");
        JMenu fileMenu = new JMenu("筆記(N)");
        fileMenu.setMnemonic('N');//設定快捷鍵

        JMenuItem homeItem = new JMenuItem("HomePage(H)");
        homeItem.setMnemonic('H');
        fileMenu.add(homeItem);
        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Page("home");
            }
        });

        JMenuItem addItem = new JMenuItem("新增筆記(A)");
        addItem.setMnemonic('A');
        fileMenu.add(addItem);
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Page("add");
            }
        });

        JMenuItem wholeItem = new JMenuItem("筆記園地(W)");
        wholeItem.setMnemonic('W');
        fileMenu.add(wholeItem);
        wholeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Page("note");
                //轉換頁面時刷新notePanel textArea的內容
                notePanel.removeAll();
                notePanel.getMongoDB();
            }
        });
        
        JMenuItem storyItem = new JMenuItem("故事區(S)");
        storyItem.setMnemonic('S');
        fileMenu.add(storyItem);
        storyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Page("story");
                //轉換頁面時刷新storyPanel textArea的內容
                storyPanel.removeAll();
                storyPanel.displayStory();
            }
        });

        JMenuItem exitItem = new JMenuItem("結束(E)");
        exitItem.setMnemonic('E');
        fileMenu.add(exitItem);
        exitItem.addActionListener(e -> System.exit(0));

        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(fileMenu);

    }

    public void Page(String index) {
        Container cp = getContentPane();
        card = new CardLayout();//因為後期會對layout進行操作，才將其單獨定義出來
        cp.setLayout(card);//cp.setLayout(new CardLayout())會出現異常

        //設定三個card
        cp.add("HomePage", panel);
        cp.add("新增筆記", addPanel);
        cp.add("筆記園地", new JScrollPane(notePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        cp.add("故事區", new JScrollPane(storyPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

        switch (index) {
            case "home":
                card.show(cp, "HomePage");
                break;
            case "add":
                card.show(cp, "新增筆記");
                break;
            case "note":
                card.show(cp, "筆記園地");
                break;
            case "story":
                card.show(cp, "故事區");
                break;

        }

    }
}
