package model.ObserversOservables;

import java.util.ArrayList;
import java.util.List;

import model.UnitWealth;

public abstract class ObservableUnitWealth {
    private List<Observer> observer;

    public ObservableUnitWealth(){
        observer = new ArrayList<>();
    }
    // MODIFIES: this
    // EFFECTS: adds the given osberver to the Observers, if it is not already in the list
    public void addOserver(Observer o) {
        if (!observer.contains(o)){
            observer.add(o);
        }

    }

    // MODIFIES: this
    // EFFECTS: removes the given osberver from Observers
    public void removeObserver(Observer o){
        observer.remove(o);
    }

    public void notifyObserver(UnitWealth unit, String modifiedTag, Boolean isAdded){
        for(Observer o : observer){
            o.update(unit,modifiedTag, isAdded);
        }
    }
}
