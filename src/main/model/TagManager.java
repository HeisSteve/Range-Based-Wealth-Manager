package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.DuplicateTagException;
import exceptions.TagNotInManagerException;
import model.ObserversOservables.Observer;

public class TagManager implements Observer {
    private Map<String, List<UnitWealth>> manager;

    // EFFECTS: creates empty manager
    public TagManager() {
        manager = new HashMap<>();
    }

    public Map<String, List<UnitWealth>> getTagMap() {
        return manager;
    }

    // EFFECTS: throws DuplicateTagException if the given tag is already in the
    // manager, else adds the given tag to the manager
    public void addTag(String tag) throws DuplicateTagException {
        if (manager.containsKey(tag)) {
            throw new DuplicateTagException();
        }
        manager.put(tag, new ArrayList<>());
    }

    // EFFECTS: gets the List of UnitWealth that has the given tag, otherwise throw
    // TagNotInManagerException
    public List<UnitWealth> getRelatedUnits(String tag) throws TagNotInManagerException {
        if (!manager.containsKey(tag)) {
            throw new TagNotInManagerException();
        }
        return manager.get(tag);
    }

    // EFFECTS: returns the total amount of all the Units of the given related tag
    public double getRelatedUnitsAmounts(String tag) throws TagNotInManagerException {
        List<UnitWealth> units = getRelatedUnits(tag);
        double amount = 0;
        for (UnitWealth unit : units) {
            amount += unit.getAmount();
        }
        return amount;
    }

    // EFFECTS: returns the total fluid of all the Units of the given related tag
    public double getRelatedFluidChanges(String tag) throws TagNotInManagerException {
        List<UnitWealth> units = getRelatedUnits(tag);
        double changes = 0;
        for (UnitWealth unit : units) {
            changes += unit.getFluidChange();
        }
        return changes;
    }

    // MODIFIES: this
    // EFFECTS: adds the given unit in the manager
    private void addUnit(UnitWealth unit, String modifiedTag) {
        if (!manager.containsKey(modifiedTag)) {
            List<UnitWealth> units = new ArrayList<>();
            units.add(unit);
            manager.put(modifiedTag, units);
        } else {
            List<UnitWealth> units = manager.get(modifiedTag);
            if (!units.contains(unit)) {
                units.add(unit);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the given unit in the manager
    private void removeUnit(UnitWealth unit, String modifiedTag) {
        if (manager.containsKey(modifiedTag)) {
            List<UnitWealth> units = manager.get(modifiedTag);
            units.remove(unit);
        }
    }

    @Override
    public void update(UnitWealth unit, String modifiedTag, Boolean isAdded) {
        if (isAdded) {
            addUnit(unit, modifiedTag);
        } else {
            removeUnit(unit, modifiedTag);
        }
    }

}
