package ui.pages;

import javax.swing.*;

import exceptions.DuplicateTagException;
import ui.buttons.AddButton;
import ui.buttons.MainButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import ui.buttons.AddButton;
import ui.buttons.MainButton;

public class MainPage extends DefaultPage implements ActionListener{
    private static MainPage mainpage = new MainPage();
    private JPanel centerPanel;
    private JPanel botPanel;
    private MainButton mainButton;
    private AddButton addButton;


    private MainPage (){
        super();
        initializeMainButtons();
    }

    public static MainPage getInstance(){
        return mainpage;
    }


     // MODIFIES: this
    // EFFECTS: initialize the Main button and the add buttons
    private void initializeMainButtons() {
        initializeMainButton();
        initializeAddButton();
    }

    // MODIFIES: this
    // EFFECTS: initialize the Main button and adds it to the main panel
    private void initializeMainButton() {
        mainButton = new MainButton("Total Amount: " + system.getTotalAmount());
        mainButton.setSize(mainButton.getPreferredSize());
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(mainButton, new GridBagConstraints());
        panel.add(centerPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initialize the add button and adds it to the main panel
    private void initializeAddButton() {
        botPanel = new JPanel();
        botPanel.setLayout(new FlowLayout());
        addButton = new AddButton("add");
        addButton.setSize(addButton.getPreferredSize());
        botPanel.add(addButton);
        panel.add(botPanel, BorderLayout.SOUTH);
        addButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: initialize the mainPanel and adds it to the Frame
    @Override
    public void updatePage() {
       mainButton.setText("Total Amount: " + system.getTotalAmount());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addEvents();
    }

     // MODIFIES: this
    // EFFECTS: displays the available options to create, and handles the actions
    // relating to the Add button
    private void addEvents() {
        String[] options = { "Create a new Unit Wealth", "Create a new tag", "Cancel" };
        int choice = JOptionPane.showOptionDialog(
                null,
                "What would you like to do?",
                "Select an Action",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        proccessChoices(choice);
    }

    // MODIFIES: this
    // EFFECTS: handles the actions relating to the Add button
    private void proccessChoices(int choice) {
        switch (choice) {
            case 0:
                createUnitWealth();
                break;
            case 1:
                createTag();
                break;
            case 2:
                System.out.println("User chose: Cancel");
                break;
            default:
                System.out.println("User closed the dialog or no option selected.");
                break;
        }
    }

    // MODFIES: this
    // EFFECTS: create a new wealth set in the system
    private void createTag() {
        String tag = JOptionPane.showInputDialog("\"Please enter the name of the Tag: ");
        try {
            system.createTag(tag);
            JOptionPane.showMessageDialog(null, "Created a new Tag!");
            TagPage.getInstance().updatePage();
        } catch (DuplicateTagException e) {
            JOptionPane.showMessageDialog(null, "This Tag has already been created!");
        }

    }

    // MODFIES: this
    // EFFECTS: create a new unit wealth and adds it to the total set, unitWealths.
    private void createUnitWealth() {
        String unitName = JOptionPane.showInputDialog("\"Please enter the name of the Unit Wealth: ");
        String unitInput = JOptionPane.showInputDialog("Please enter the amount in this Unit Wealth: ");
        String fliudChangeInput = JOptionPane.showInputDialog("How Much can this Unit Change? ");
        int unitAmount = 0;
        unitAmount = Integer.parseInt(unitInput);
        int fliudChange = Integer.parseInt(fliudChangeInput);
        system.createUnit(unitName, unitAmount, fliudChange);
        mainButton.setText(("Total Amount: " + system.getTotalAmount()));
        JOptionPane.showMessageDialog(null, "Created a new Unit!");
        UnitsPage.getInstance().updatePage();
        // upDateProgressBar();
        System.out.println("ran");
    }



    public static void main(String[] args) {
        new MainPage();
    }

}
