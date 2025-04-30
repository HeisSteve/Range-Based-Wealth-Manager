package model.ObserversOservables;

import model.UnitWealth;

public interface Observer {
    public void update(UnitWealth unit, String modifiedTag, Boolean isAdded);
}
