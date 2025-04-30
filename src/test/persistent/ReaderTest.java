package persistent;


import persistence.JsonWealthReader;
import model.ManagerSystem;
import model.UnitWealth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.DuplicateTagException;
import exceptions.NoSuchUnitException;
import exceptions.TagNotInManagerException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest{

    private ManagerSystem system;
    private UnitWealth unitA;
    private UnitWealth unitB;

    @BeforeEach
    void setUp(){
        ManagerSystem.getInstance().setDefaultState();
        system = ManagerSystem.getInstance();
        unitA = new UnitWealth("RBC Account", 1210);
        unitA.setFluidChange(2);
        unitB = new UnitWealth("Home loan", -260);
        try {
            unitA.addTag("Home");
            unitA.addTag("Bank");
        unitB.addTag("Loan");
        unitB.addTag("Home");
        } catch (DuplicateTagException e) {
            fail("Exception thrown");
        }
        
    }

    @Test
    void testReaderNonExistentFile() {
        JsonWealthReader reader = new JsonWealthReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (NoSuchUnitException e) {
            fail("wrong exception thrown");
        } catch (DuplicateTagException e) {
            fail("wrong exception thrown");
        }
    }

    @Test
    void testReaderEmptyWealthSet() {
        JsonWealthReader reader = new JsonWealthReader("./data/testReaderEmptyWealthSet.json");
        try {
            reader.read();
            assertEquals(true, ManagerSystem.getInstance().getAllUnits().isEmpty());

        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (NoSuchUnitException e) {
            fail("wrong exception thrown");
        } catch (DuplicateTagException e) {
            fail("wrong exception thrown");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonWealthReader reader = new JsonWealthReader("./data/testReaderGeneralWealthSet.json");
        try {
            reader.read();
            List<UnitWealth> units = system.getAllUnits();
            checkUnitsAreCorrect(units);
            checkTagManagerIsCorrect(units);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NoSuchUnitException e) {
            fail ("No such Unit exception is thrown");
        } catch (DuplicateTagException e) {
            fail("wrong exception thrown");
        }
    }

    //EFFECTS: helper method to check that TagManager matches with condition set in setUpGeneralTest
    private void checkTagManagerIsCorrect(List<UnitWealth> units) {
        Set<String> tags = system.getTagManager().getTagMap().keySet();
        assertEquals(3,tags.size());
        assertEquals(true,tags.contains("Loan"));
        assertEquals(true,tags.contains("Home"));
        assertEquals(true,tags.contains("Bank"));
        try {
            List<UnitWealth> tagUnits = system.getTagManager().getRelatedUnits("Bank");
            assertEquals(1,tagUnits.size());
            assertEquals(true, units.get(0).equals(unitA));
            tagUnits = system.getTagManager().getRelatedUnits("Home");
            assertEquals(2,tagUnits.size());
            assertEquals(true, units.get(0).equals(unitA));
            assertEquals(true, units.get(1).equals(unitB));
            tagUnits = system.getTagManager().getRelatedUnits("Loan");
            assertEquals(1,tagUnits.size());
            assertEquals(true, tagUnits.get(0).equals(unitB));
        } catch (TagNotInManagerException e) {
            fail("Tag not found exception thrown");
        }
    }

    
    //EFFECTS: helper method to check that allUnits matches with condition set in setUpGeneralTest
    private void checkUnitsAreCorrect(List<UnitWealth> units) {
        assertEquals(2, units.size());
        assertEquals(true, units.get(0).equals(unitA));
        assertEquals(true, units.get(1).equals(unitB));
    }
}
