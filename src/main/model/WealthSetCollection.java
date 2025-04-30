// package model;

// import java.util.List;

// import org.json.JSONObject;

// import persistence.Writable;

// import java.util.ArrayList;

// // Represents a collection of all WealthSets in the application
// public class WealthSetCollection implements Writable {
//     private List<WealthSet> totalSets;

//     public WealthSetCollection() {
//         totalSets = new ArrayList<>();
//     }

//     // EFFECTS: this
//     // EFFECTS: add the set to totalSets instead
//     public void addSet(WealthSet set) {
//         this.totalSets.add(set);
//     }

//     // EFFECTS: return the set of given n
//     public WealthSet getSet(int n) {
//         return this.totalSets.get(n);
//     }

//     // EFFECTS: return the set size
//     public int getSize() {
//         return this.totalSets.size();
//     }

//     // EFFECTS: return the true if set is empty, otherwise false
//     public boolean isEmptySet() {
//         return this.totalSets.isEmpty();
//     }

//     // EFFECTS: return the true if set is empty, otherwise false
//     public List<WealthSet> getTotalSets() {
//         return this.totalSets;
//     }

//     // EFFECTS: returns this as JSON object
//     public JSONObject toJson() {
//         JSONObject json = new JSONObject();
//         json.put("WealthSetCollection", this.totalSets);
//         return json;
//     }
// }
