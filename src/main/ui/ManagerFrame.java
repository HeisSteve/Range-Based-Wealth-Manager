package ui;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import exceptions.DuplicateTagException;
import exceptions.NoSuchUnitException;
import exceptions.TagNotInManagerException;
import model.Event;
import model.EventLog;
import model.ManagerSystem;
import model.UnitWealth;
import persistence.JsonWealthReader;
import persistence.JsonWealthWriter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.FlowLayout;
// import ui.buttons.AddButton;
// import ui.buttons.MainButton;
import ui.pages.MainPage;
import ui.pages.TagPage;
import ui.pages.UnitsPage;

// Represents a GUI Users can interact with, which contains 3 tabbed panel, Main, Unit, Set. 
public class ManagerFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 1000;
    private static final int HIEGHT = 800;
    private MainPage mainPage;

    private UnitsPage unitsPage;

    private TagPage tagPage;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadItem;
    private JMenuItem saveItem;

    private JTabbedPane tabbedPane;
    private DollarSignIcon dollarSign;
    // private ImageIcon dollarSignIcon;

    private static final String JSON_STORE_SET = "./data/totalWealthSet.json";

    private ManagerSystem system;
    private JsonWealthReader wealthSetReader;
    private JsonWealthWriter wealthSetWriter;

    // EFFECTS: Constructs a GUI with a set of All Units, and a Collection of
    // ALL Sets, and GUI Initialization
    public ManagerFrame() {
        initializeSystem();
        mainPage = MainPage.getInstance();
        unitsPage = UnitsPage.getInstance();
        tagPage = TagPage.getInstance();
        dollarSign = new DollarSignIcon();
        initializeFrame();
        initializeMenuBar();
        initializeTabbedPane();
    }

    // MODIFIES: this
    // EFFECTS: initialize the tabbed pane with 3 panels added, main, second third
    public void initializeTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.add("Main", mainPage.getPanel());
        tabbedPane.add("Units", unitsPage.getPanel());
        tabbedPane.add("Tags", tagPage.getPanel());
        add(tabbedPane);
    }

    // MODIFIES: this
    // EFFECTS: initialize the Units and sets in which it is empty.
    private void initializeSystem() {
        this.system = ManagerSystem.getInstance();
        system.setDefaultState();
        this.wealthSetReader = new JsonWealthReader(JSON_STORE_SET);
        this.wealthSetWriter = new JsonWealthWriter(JSON_STORE_SET);
    }

    // MODIFIES: this
    // EFFECTS: initialize the JFrame with the size of WIDTH, HIEGHT CONSTANT
    private void initializeFrame() {
        this.setTitle("Wealth Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(WIDTH, HIEGHT);
        this.setLocationRelativeTo(null);
        // initializeIcon();

        this.setIconImage(dollarSign.getImage());
        initializeWindowListener();
    }

    // MODIFIES: this
    // EFFECTS: initialize the actions for the window
    private void initializeWindowListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
            }
        });
    }

  

    // MODIFIES: this
    // EFFECTS: initialize the manu bar with the options of save and load, and adds
    // it to the Frame
    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        menuBar.add(fileMenu);
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        this.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: handles the actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadItem) {
            load();
        }
        if (e.getSource() == saveItem) {
            save();
        }
    }

    // MODIFIES: this
    // EFFECTS: save all Units and all sets created to file
    private void save() {
        try {
            wealthSetWriter.open();
            wealthSetWriter.write(system);
            wealthSetWriter.close();
            JOptionPane.showMessageDialog(null, "Saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: ");
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads all Units and all sets from file
    private void load() {
        try {
            wealthSetReader.read();
            unitsPage.updatePage();
            tagPage.updatePage();
            mainPage.updatePage();
            // upDateProgressBar();
            // mainButton.setText("Total Amount: " + system.getTotalAmount());
            
            JOptionPane.showMessageDialog(null, "Loaded!");
        } catch (IOException e) {
            System.out.println("Unable to read from file");
        } catch (NoSuchUnitException e) {
            System.out.println("Unable to read from file");
        } catch (DuplicateTagException e) {
            System.out.println("Unable to read from file");
        }
    }

    public static void main(String[] args) {
        new ManagerFrame();
    }

}
