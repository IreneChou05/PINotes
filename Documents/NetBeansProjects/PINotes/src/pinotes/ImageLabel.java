/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinotes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Irene NB
 */
class ImageLabel extends JLabel {

    public ImageLabel(String img) {
        this(new ImageIcon(img));
    }

    public ImageLabel(ImageIcon icon) {
        setIcon(icon);
        // setMargin(new Insets(0,0,0,0));
        setIconTextGap(0);
        // setBorderPainted(false);
        setBorder(null);
        setText(null);
        setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
    }

}
