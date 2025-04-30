package ui.buttons;

import java.awt.*;
import javax.swing.*;

// Represents a Add button with a plus sign image that is 160 by 50. 
public class AddButton extends JButton {

    // EFFECTS: Constructs a button that has a add image and is 160 by 50, with white background 
    public AddButton(String label) {
        super(label);
        setFocusable(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(160, 50));
        setBackground(Color.WHITE);
        ImageIcon buttonIcon = reScale();
        setIcon(buttonIcon);
    }

    //REFERENCE: image from https://www.cleanpng.com/png-computer-icons-plus-and-minus-signs-clip-art-plus-2773199/
    // EFFECTS: rescales the image to 50 by 50 and returns it 
    private ImageIcon reScale() {
        ImageIcon buttonIcon = new ImageIcon("image/addSign.png");
        Image image = buttonIcon.getImage(); // Get the image from the ImageIcon
        Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

}
