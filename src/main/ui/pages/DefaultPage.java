package ui.pages;

import javax.swing.*;
import model.ManagerSystem;

import java.awt.BorderLayout;
import java.awt.Color;



public abstract class DefaultPage {
    private static final int WIDTH = 1000;
    private static final int HIEGHT = 800;
    protected JPanel panel;
    protected ManagerSystem system;

    public DefaultPage() {
        system = ManagerSystem.getInstance();
        initializePanel();
    }

    // MODIFIES: this
    // EFFECTS: initialize the mainPanel and adds it to the Frame
    private void initializePanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(0, 0, WIDTH, HIEGHT);
        panel.setBackground(Color.WHITE);
    }

    public JPanel getPanel() {
        return panel;
    }

    public abstract void updatePage();
}
