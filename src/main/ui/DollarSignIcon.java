package ui;

import java.awt.Image;
import javax.swing.*;

public class DollarSignIcon {
    private Image dollarSign;
    private ImageIcon dollarSignIcon;

     // REFERENCE: image from
    // https://hiclipart.com/free-transparent-background-png-clipart-dxvcw/download
    // EFFECTS: rescale the image to 100 by 100
    public DollarSignIcon() {
        ImageIcon dollarSignTemp = new ImageIcon("image/dollarSign.png");
        Image image = dollarSignTemp.getImage(); 
        this.dollarSign = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.dollarSignIcon = new ImageIcon(dollarSign);
    }

    public Image getImage(){
        return dollarSign;
    }

    public ImageIcon getIcon(){
        return dollarSignIcon;
    }


}
