/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinotes;

import java.applet.Applet;
import javax.swing.JFrame;

/**
 *
 * @author Irene NB
 */
public class PINotes extends Applet{

    public static void main(String args[]) {
        PINFrame frame = new PINFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true); // display frame
    } // end main
} // end class MenuTest


