package ui.buttons;

import java.awt.*;
import javax.swing.*;

//Represents Button that is 200 by 100 with no color
public class MainButton extends JButton {

    // EFFECTS: Constructs a button that is 200 by 100, with no background or boarder
    public MainButton(String label) {
        super(label);

        setFocusable(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(200, 100));
        setBackground(new Color(0, 0, 0, 0));
    }
}
