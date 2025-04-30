package ui.pages;

import javax.swing.*;

import exceptions.DuplicateTagException;
import exceptions.NoSuchUnitException;
import exceptions.TagNotInManagerException;
import model.UnitWealth;
import ui.DollarSignIcon;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.FlowLayout;
public class TagPage extends DefaultPage implements ActionListener {
    private static TagPage tagPage = new TagPage();
    private JList<String> tagList;
    private JList<UnitWealth> tagsUnitList;
    private DefaultListModel<String> tagModel;
    private JButton editRelatedUnitsButton;
    private DefaultListModel<UnitWealth> tagsUnitModel;
    private JLabel tagAmount;
    private JLabel tagName;
    private JLabel allTags;
    private JPanel tagInfoPanel;
    private DollarSignIcon dollarSign;


    private TagPage(){
        super();
        initializeTagsPanel();
    }

    public static TagPage getInstance(){
        return tagPage;
    }

    // MODIFIES: this
    // EFFECTS: initialize the tags panel and adds it to the Frame
    private void initializeTagsPanel() {
        initializeJTagsList();
        updateTagsList(tagModel);
        dollarSign = new DollarSignIcon();
        initializeTagsInfoPanel();
    }

     // MODIFIES: this
    // EFFECTS: initialize the Information panel for the selected tags
    private void initializeTagsInfoPanel() {
        tagInfoPanel = new JPanel();
        tagInfoPanel.setLayout(new GridBagLayout());
        JPanel layout = new JPanel();
        layout.setLayout(new GridLayout(6, 1, 0, 0));
        initializeSetInfoLabels();
        initializeTagsUnitList();
        JPanel setbotPanel = new JPanel();
        setbotPanel.setLayout(new FlowLayout());
        initializeRelateUnitButton();
        setbotPanel.add(editRelatedUnitsButton);
        setRelateUnitAction();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        tagInfoPanel.add(setbotPanel, gbc);
        layout.add(tagName);
        layout.add(tagAmount);
        layout.add(allTags);
        layout.add(new JScrollPane(tagsUnitList));
        tagInfoPanel.add(layout, new GridBagConstraints());
        initializeSplitPaneSet();
    }

     // MODIFIES: this
    // EFFECTS: intializes the Jlabel for setname, setAmount, related relatedLable
    private void initializeSetInfoLabels() {
        this.tagName = new JLabel();
        this.tagAmount = new JLabel();
        this.allTags = new JLabel();
    }

    // EFFECTS: set the addListSelectionListen to the list of tag in the tag
    // panel.
    private void setRelateUnitAction() {
        tagList.getSelectionModel().addListSelectionListener(e -> {

            String selectedTag = tagList.getSelectedValue();
            if (selectedTag != null) {
                tagName.setText("Tag : " + selectedTag);
                try {
                    tagAmount.setText("Total tag Amount: " + system.getTagManager().getRelatedUnitsAmounts(selectedTag)
                            + " +/- " + system.getTagManager().getRelatedFluidChanges(selectedTag));
                } catch (TagNotInManagerException e1) {
                    // Never true
                }
                try {
                    allTags
                            .setText("Related Units: " + system.getTagManager().getRelatedUnits(selectedTag).size());
                } catch (TagNotInManagerException e1) {
                    // Never true
                }
                allTags.setIcon(dollarSign.getIcon());
                editRelatedUnitsButton.setVisible(true);
                tagsUnitList.setVisible(true);
                updateTagsUnitList(tagsUnitModel);

            } else {
                editRelatedUnitsButton.setVisible(false);
                tagsUnitList.setVisible(false);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS initialize the Unit List of a set
    private void initializeTagsUnitList() {
        tagsUnitList = new JList<>();
        tagsUnitModel = new DefaultListModel<>();
        tagsUnitList.setModel(tagsUnitModel);
        tagsUnitList.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: initialize the SplitPane for the tag panel
    private void initializeSplitPaneSet() {
        JSplitPane splitTags = new JSplitPane();
        splitTags.setDividerLocation(200);
        splitTags.setLeftComponent(new JScrollPane(tagList));
        splitTags.setRightComponent(tagInfoPanel);
        panel.add(splitTags);
    }

    
    // MODIFIES: this
    // EFFECTS: initialize the relateUnitButton
    private void initializeRelateUnitButton() {
        editRelatedUnitsButton = new JButton("Edit the related Units");
        editRelatedUnitsButton.setFocusable(false);
        editRelatedUnitsButton.setSize(editRelatedUnitsButton.getPreferredSize());
        editRelatedUnitsButton.setVisible(false);
        editRelatedUnitsButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: updates the tag model to contain all units with the tag
    private void updateTagsUnitList(DefaultListModel<UnitWealth> tagsUnitModel) {
        String selectedTag = tagList.getSelectedValue();
        tagsUnitModel.clear();
        try {
            for (UnitWealth unit : system.getTagManager().getRelatedUnits(selectedTag)) {
                tagsUnitModel.addElement(unit);
            }
        } catch (TagNotInManagerException e) {
            // Never True
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize the JList for tag, the list model, and tag the model
    // for tagLists
    private void initializeJTagsList() {
        tagList = new JList<>();
        tagModel = new DefaultListModel<>();
        tagList.setModel(tagModel);
    }

    // MODIFIES: this
    // EFFECTS: updates the Tag model to contain all tag
    private void updateTagsList(DefaultListModel<String> tagModel) {
        tagModel.clear();
        for (String tag : this.system.getAllTags()) {
            tagModel.addElement(tag);
        }
    }

	@Override
	public void updatePage() {
		updateTagsList(tagModel);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        editEvents();
    }

    // MODIFIES: this
    // EFFECTS: displays the available options to create, and handles the actions
    // relating to the edit the related units button
    private void editEvents() {
        String[] options = { "Relate a Unit Wealth", "Remove a Unit Wealth", "Reverse order of Unit Wealths",
                "Cancel" };
        int choice = JOptionPane.showOptionDialog(
                null,
                "What would you like to do?",
                "Select an Action",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        proccessEdits(choice);
    }

     // MODIFIES: this
    // EFFECTS: handles the actions relating to the edit related units button
    private void proccessEdits(int choice) {
        switch (choice) {
            case 0:
                addUnitToTag();
                break;
            case 1:
                removeUnitFromTag();
                break;
            case 2:
                // reverseUnitOrder(); //TODO
                break;
            case 3:
                System.out.println("User chose: Cancel");
                break;
            default:
                System.out.println("User closed the dialog or no option selected.");
                break;
        }
    }

     // // MODFIES: this
    // // EFFECTS: create a new dialog, that ask user for the name of unit they
    // want to add, and adds it to the selected set
    public void addUnitToTag() {
        String selectedTag = tagList.getSelectedValue();
        // boolean unitFound = false;
        String unitName = JOptionPane.showInputDialog(
                "\"Please enter the name of the Unit Wealth you would like to relate this to: ");
        try {
            system.addTag(unitName, selectedTag);
            updateTagsUnitList(tagsUnitModel);
            allTags.setText("Related Units: " + system.getTagManager().getRelatedUnits(selectedTag).size());
            this.tagAmount
            .setText("Set Amount: " + system.getTagManager().getRelatedUnitsAmounts(selectedTag) + " +/- " +
            system.getTagManager().getRelatedFluidChanges(selectedTag));
            JOptionPane.showMessageDialog(null, "Added a new Unit!");
        } catch (NoSuchUnitException e) {
            JOptionPane.showMessageDialog(null, "Unit not found.");
        } catch (DuplicateTagException e) {
            JOptionPane.showMessageDialog(null, "Already in the set!");
        } catch (TagNotInManagerException e) {
            JOptionPane.showMessageDialog(null, "Tag Not Found");
        }
    }

     // // MODFIES: this
    // // EFFECTS: create a new dialog, that ask user for the name of unit they
    // want to remove, and removes it from the selected set
    private void removeUnitFromTag() {
        String selectedTag = tagList.getSelectedValue();
        String unitName = JOptionPane.showInputDialog(
                "\"Please enter the name of the Unit Wealth you would like to remove: ");
        try {
            system.getUnit(unitName).removeTag(selectedTag);
            updateTagsUnitList(tagsUnitModel);
            try {
                allTags.setText("Related Units: " + system.getTagManager().getRelatedUnits(selectedTag).size());
                this.tagAmount
                        .setText("tag Amount: " + system.getTagManager().getRelatedUnitsAmounts(selectedTag) + " +/- "
                                + system.getTagManager().getRelatedFluidChanges(selectedTag));
            } catch (TagNotInManagerException e) {
                JOptionPane.showMessageDialog(null, "Error in finding the Tag");
            }
            JOptionPane.showMessageDialog(null, "Removed!");
        } catch (NoSuchUnitException e) {
            JOptionPane.showMessageDialog(null, "Unit not found.");
        }
    }
}
