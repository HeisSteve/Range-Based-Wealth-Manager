package ui.pages;

import javax.swing.*;

import model.UnitWealth;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.*;


public class UnitsPage extends DefaultPage implements ActionListener{
    private static UnitsPage unitsPage = new UnitsPage();
    private JList<UnitWealth> unitList;
    private DefaultListModel<UnitWealth> unitModel;
    private JLabel unitName;
    private JLabel unitAmount;

    private UnitsPage(){
        super();
        initializeUnitsPanel();
    }

    public static UnitsPage getInstance(){
        return unitsPage;
    }

    // MODIFIES: this
    // EFFECTS: initialize the second panel, unit panel, and adds it to the Frame
    private void initializeUnitsPanel() {
        initializeJUnitList();
        updateUnitsList(unitModel);
        initializeUnitInfoPanel();
    }

     // MODIFIES: this
    // EFFECTS: initialize the JList for units, the list model, and sets the model
    // for unitList
    private void initializeJUnitList() {
        unitList = new JList<>();
        unitModel = new DefaultListModel<>();
        unitList.setModel(unitModel);
    }

     // MODIFIES: this
    // EFFECTS: initialize the Information panel for the selected unit
    private void initializeUnitInfoPanel() {
        JPanel unitInfoPanel = new JPanel();
        unitInfoPanel.setLayout(new GridBagLayout());
        JPanel layout = new JPanel();
        layout.setLayout(new GridLayout(3, 1, 0, 10));

        unitName = new JLabel();
        unitAmount = new JLabel();

        unitInfoPanel.add(layout, new GridBagConstraints());
        layout.add(unitName);
        layout.add(unitAmount);

        unitList.getSelectionModel().addListSelectionListener(e -> {
            UnitWealth unit = unitList.getSelectedValue();
            if (unit != null) {
                unitName.setText("Unit Name: " + unit.getName());
                unitAmount.setText("Unit Amount: " + unit.getAmount() + " +/- " + unit.getFluidChange());
            }
        });

        JSplitPane split = new JSplitPane();
        split.setDividerLocation(200);
        split.setLeftComponent(new JScrollPane(unitList));
        split.setRightComponent(unitInfoPanel);
        panel.add(split);
    }

    
    // MODIFIES: this
    // EFFECTS: updates the Unit model to contain all Units in unitWealths
    private void updateUnitsList(DefaultListModel<UnitWealth> model) {
        model.clear();
        for (UnitWealth unit : system.getAllUnits()) {
            model.addElement(unit);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    @Override
    public void updatePage() {
        updateUnitsList(unitModel);
    }

}
