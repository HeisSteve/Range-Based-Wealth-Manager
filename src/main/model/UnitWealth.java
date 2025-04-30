package model;

import java.util.List;

import exceptions.DuplicateTagException;
import model.ObserversOservables.ObservableUnitWealth;

import java.util.ArrayList;

// Represents the smallest unit of “wealth” that can be broken down, 
// and contains the informations like the *name*, *amount*,  
// *fluid changes*(by how much can the amount change), 
// *type*(positive wealth, negative wealth),
//  *related set* (also contain information of the set like name and, 
//  Percentage of contribution)
public class UnitWealth extends ObservableUnitWealth{ // implements Writable {
    private String name;
    private double amount;
    private double fluidChange;
    private boolean type; // true for Positive(if amount>=0), false for negative(if amount<0)
    // private List<WealthSet> relatedSets;
    private List<String> tags;


    // Requires: length of name > 0
    // Effect: Constructs a UnitWealth with given name, amount,
    // and fluid changes = 0,
    // type = positive, and a empty related set
    public UnitWealth(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.fluidChange = 0;
        this.type = checkType();
        this.tags = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created a new Unit Wealth: " + this.name));
    }


    // Requires: length of name > 0
    // Effect: Constructs a UnitWealth with given name, amount,
    // and fluid changes = 0,
    // type = positive, and a empty related set
    public UnitWealth(String name, double amount,TagManager tagManager) {
        this.name = name;
        this.amount = amount;
        this.fluidChange = 0;
        this.type = checkType();
        // this.relatedSets = new ArrayList<>();
        this.tags = new ArrayList<>();
        addOserver(tagManager);
        EventLog.getInstance().logEvent(new Event("Created a new Unit Wealth: " + this.name));
    }

    public UnitWealth(String name, double amount, double fluidChange, TagManager tagManager) {
        this.name = name;
        this.amount = amount;
        this.fluidChange = fluidChange;
        this.type = checkType();
        this.tags = new ArrayList<>();
        addOserver(tagManager);
        EventLog.getInstance().logEvent(new Event("Created a new Unit Wealth: " + this.name));
    }

    // Requires: length of name > 0
    // MODIFIES: this
    // Effect: set name
    public void changeName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // Effect: change amount by given a, and changes the
    // type based on the current amount
    // positive if amount >= 0, Negative is amount <0.
    public void changeAmount(double a) {
        this.amount += a;
        this.type = checkType();
    }

    // EFFECTS: returns true if amount >= 0, otherwise false
    private boolean checkType() {
        if (this.amount >= 0) {
            return true;
        } else {
            return false;
        }
    }

    // // MODIFIES: this
    // // Effect: adds the given set to RelatedSets.
    // public void addRelatedSet(WealthSet set) {
    //     this.relatedSets.add(set);
    // }

    // // MODIFIES: this
    // // Effect: adds the given set to RelatedSets.
    // public void removeRelatedSet(WealthSet set) {
    //     this.relatedSets.remove(set);
    // } // Add Test

    // // Effect: returns a list of set names and Percentage of contribution
    // // that this unit is connected to
    // public List<String> relatedSets() {
    //     List<String> listSetInfo = new ArrayList<>();
    //     for (WealthSet ws : this.relatedSets) {
    //         double contributionPercentage = 0;
    //         contributionPercentage = this.amount * 100 / relatedTypeAmount(ws);
    //         String formattedValue = String.format("%.2f", contributionPercentage);
    //         listSetInfo.add(ws.getName() + " " + formattedValue + "% of" + " " + translateType() + " Contribution");
    //     }
    //     return listSetInfo;
    // }

    // // Effect: returns the same type of total amount of the Wealthset
    // private double relatedTypeAmount(WealthSet ws) {
    //     if (this.type) {
    //         return ws.getPosAmount();
    //     } else {
    //         return ws.getNegAmount();
    //     }
    // }

    // // Effect: returns the translated type, true = positive, false = negative
    // private String translateType() {
    //     if (this.type) {
    //         return "Positive";
    //     } else {
    //         return "Negative";
    //     }
    // }

    // Requires: fc >= 0
    // MODIFIES: this
    // Effect: set FluidChange to given fc
    public void setFluidChange(double fc) {
        this.fluidChange = fc;
    }

    // MODIFIES: this
    // EFFECTS: adds given tag if it is not already in tags
    public void addTag(String tag) throws DuplicateTagException{
        if(tags.contains(tag)){
           throw new DuplicateTagException();
        }
        tags.add(tag);
        notifyObserver(this, tag, true);
    }

    // MODIFIES: this
    // EFFECTS: removes given tag if it is in tags
    public void removeTag(String tag){
        if(tags.contains(tag)){
            tags.remove(tag);
            notifyObserver(this, tag, false);
        }
        
    }

    // getter method
    public String getName() {
        return this.name;
        
    }

    // getter method
    public double getAmount() {
        return this.amount;
    }

    // getter method
    public double getFluidChange() {
        return this.fluidChange;
    }

    // getter method
    public boolean getType() {
        return this.type;
    }

    // // getter method
    // public int getRelatedSize() {
    //     return this.relatedSets.size();
    // }

    // getter method
    public List<String> getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return name;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fluidChange);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (type ? 1231 : 1237);
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UnitWealth other = (UnitWealth) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
            return false;
        if (Double.doubleToLongBits(fluidChange) != Double.doubleToLongBits(other.fluidChange))
            return false;
        if (type != other.type)
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        return true;
    }

}
