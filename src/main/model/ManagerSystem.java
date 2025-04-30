package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import exceptions.DuplicateTagException;
import exceptions.NoSuchUnitException;
import persistence.Writable;

public class ManagerSystem implements Writable{
    private static ManagerSystem managerSystem = new ManagerSystem();
    private List<UnitWealth> allUnits;
    private TagManager tagManager;

    
    private ManagerSystem(){}

    public static ManagerSystem getInstance(){
        return managerSystem;
    }

    //EFFECTS: initlizes the system with empty allUnits, and a new TagManager
    public void setDefaultState(){
        this.allUnits = new ArrayList<>();
        this.tagManager = new TagManager(); 
    }

    // //MODIFIES: this
    // //EFFECTS: sets the state of the system with given List<Unit>, and given TagManager
    // public void setState(List<UnitWealth> units, TagManager tagManager){
    //     this.allUnits = units;
    //     this.tagManager = tagManager; 
    // }

    //MODIFIES: this
    //EFFECTS: creates a new WealthUnit and adds it to the allUnits
    public void createUnit(String name, double Amount){
        UnitWealth unit = new UnitWealth(name, Amount, tagManager);
        allUnits.add(unit);
    }

    //MODIFIES: this
    //EFFECTS: creates a new WealthUnit and adds it to the allUnits
    public void createUnit(String name, double Amount, double fluidChange){
        UnitWealth unit = new UnitWealth(name, Amount, fluidChange, tagManager);
        allUnits.add(unit);
    }
   

    // MODIFIES: this
    //EFFECTS: adds a tag to the Unit with the given name, else throws NoSuchUnitException
    public void addTag(String name, String tag) throws NoSuchUnitException, DuplicateTagException{
        getUnit(name).addTag(tag);        
    }

    // MODIFIES: this
    //EFFECTS: adds a tag to the Unit with the given name, else throws NoSuchUnitException
    public void createTag(String name) throws DuplicateTagException{
        tagManager.addTag(name);;        
    }

    //EFFECTS: returns the Unit with the given name, else throws NoSuchUnitException
    public UnitWealth getUnit(String name) throws NoSuchUnitException{
        for(UnitWealth unit: allUnits){
            if (unit.getName().equals(name)){
                return unit;
            }
        }
        throw new NoSuchUnitException();
    }


    //EFFECTS: returns the amount of all the Units 
    public String getName(){
        return "Overall";
    }

    //EFFECTS: returns the amount of all the Units 
    public double getTotalAmount(){
        double totalAmount = 0;
        for(UnitWealth unit: allUnits){
            totalAmount += unit.getAmount();
        }
        return totalAmount;
    }

    //EFFECTS: returns the amount >= 0 of all the Units 
    public double getTotalPositiveAmount(){
        double totalAmount = 0;
        for(UnitWealth unit: allUnits){
            if (unit.getAmount() >= 0)
            totalAmount += unit.getAmount();
        }
        return totalAmount;
    }

    //EFFECTS: returns the amount < 0 of all the Units 
    public double getTotalNegativeAmount(){
        double totalAmount = 0;
        for(UnitWealth unit: allUnits){
            if (unit.getAmount() < 0)
            totalAmount += unit.getAmount();
        }
        return totalAmount;
    }


    //EFFECTS: returns the fulid change of all the Units 
    public double getTotalFluidChange(){
        double totalFulidChange = 0;
        for(UnitWealth unit: allUnits){
            totalFulidChange += unit.getFluidChange();
        }
        return totalFulidChange;
    }

    public Set<String>  getAllTags(){
        return tagManager.getTagMap().keySet();
    }

    public List<UnitWealth> getAllUnits(){
        return allUnits;
    }

    public TagManager  getTagManager(){
        return tagManager;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
                json.put("units", this.allUnits);
                // json.put("tagManager", this.tagManager.getTagMap());
                return json;
    }

}
