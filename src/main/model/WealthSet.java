// package model;

// import java.util.ArrayList;
// import java.util.List;

// import org.json.JSONObject;
// import persistence.Writable;
// import java.util.Collections;

// // Represents a collection of "UnitWealth", and holds infromation like
// // "name" of the set, 
// // *total amount*(combo amount of all the UnitWealth under it),
// // *total fulidchange*(combo amount of all the UnitWealth under it)
// // *type* of wealth, if totalAmount >= 0, it is true, otherwise false
// // and a list of "UnitWealth" that contribuates to the Wealthset,
// public class WealthSet implements Writable {
//     private String name;
//     private double totalAmount;
//     private double totalFluidChange;
//     private double positiveTotalAmount;
//     private double negativeTotalAmount;
//     private List<UnitWealth> unitWealths;
//     private boolean type; // positive = true, negative = false

//     // Requires: length of name > 0
//     // Effect: Constructs a WealthSet with given name,
//     // total amount = 0, fluid changes = 0,
//     // type = positive, and a empty UnitWealth set
//     public WealthSet(String name) {
//         this.name = name;
//         this.totalAmount = 0;
//         this.totalFluidChange = 0;
//         this.positiveTotalAmount = 0;
//         this.negativeTotalAmount = 0;
//         this.unitWealths = new ArrayList<>();
//         this.type = true;
//         EventLog.getInstance().logEvent(new Event("Created a New Wealth Set: " + this.name));
//     }

//     // MODIFIES: this
//     // EFFECTS: adds UnitWealth to the list, UnitWealths,
//     // Updates the total amount, and total fulid change,
//     // and also the type
//     public void addUnitWealth(UnitWealth uw) {
//         unitWealths.add(uw);
//         this.totalAmount += uw.getAmount();
//         this.totalFluidChange += uw.getFluidChange();
//         checkType();
//         if (uw.getType()) {
//             this.positiveTotalAmount += uw.getAmount();
//         } else {
//             this.negativeTotalAmount += uw.getAmount();
//         }
//         EventLog.getInstance().logEvent(new Event("Added " + uw.getName() + " to " + this.name));
//     }

//     // EFFECTS: returns true if amount >= 0, otherwise false
//     private void checkType() {
//         if (this.totalAmount >= 0) {
//             this.type = true;
//         } else {
//             this.type = false;
//         }
//     }

//     // MODIFIES: this
//     // EFFECTS: removes UnitWealth from the list of UnitWealth
//     // Updates the total amount, and total fulid change,
//     // and also the type
//     public void removeUnitWealth(UnitWealth uw) {
//         unitWealths.remove(uw);
//         this.totalAmount -= uw.getAmount();
//         this.totalFluidChange -= uw.getFluidChange();
//         checkType();
//         if (uw.getType()) {
//             this.positiveTotalAmount -= uw.getAmount();
//         } else {
//             this.negativeTotalAmount -= uw.getAmount();
//         }
//         EventLog.getInstance().logEvent(new Event("Removed " + uw.getName() + " from " + this.name));
//     }

//     // MODIFIES: this
//     // EFFECTS: reverse order of UnitWealth
//     public void reverseUnitWealth() {
//         Collections.reverse(this.unitWealths);
//         EventLog.getInstance().logEvent(new Event("Reversed order of Unit Wealths in " + this.name));
//     }

//     // Requires: length of name > 0
//     // MODIFIES: this
//     // Effect: setter method
//     public void changeName(String name) {
//         this.name = name;
//     }

//     // getter method
//     public String getName() {
//         return this.name;
//     }

//     // getter method
//     public double getTotalAmount() {
//         return this.totalAmount;
//     }

//     // getter method
//     public double getPosAmount() {
//         return this.positiveTotalAmount;
//     }

//     // getter method
//     public double getNegAmount() {
//         return this.negativeTotalAmount;
//     }

//     // getter method
//     public double getTotalFluidChange() {
//         return totalFluidChange;
//     }

//     // getter method
//     public List<UnitWealth> getUnitWealths() {
//         return this.unitWealths;
//     }

//     // getter method
//     public boolean getType() {
//         return this.type;
//     }

//     @Override
//     public JSONObject toJson() {
//         JSONObject json = new JSONObject();
//         json.put("name", this.name);
//         json.put("totalAmount", this.totalAmount);
//         json.put("positiveTotalAmount", this.positiveTotalAmount);
//         json.put("negativeTotalAmount", this.negativeTotalAmount);
//         json.put("totalFluidChange", this.totalFluidChange);
//         json.put("type", this.type);
//         json.put("unitWealths", this.unitWealths);
//         return json;
//     }

//     @Override
//     public String toString() {
//         return name;
//     }

// }
