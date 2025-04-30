package persistent;

// import model.WealthSet;
import persistence.JsonWealthReader;
import persistence.JsonWealthWriter;
import model.ManagerSystem;
import model.TagManager;
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

class WriterTest {
    // private WealthSet testSet;
    private ManagerSystem system;
    private UnitWealth unitA;
    private UnitWealth unitB;

    @BeforeEach
    void setUp() {
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
    void testWriterInvalidFile() {
        try {
            JsonWealthWriter writer = new JsonWealthWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWealthSet() {
        try {
            JsonWealthWriter writer = new JsonWealthWriter("./data/testWriterEmptyWealthSet.json");
            writer.open();
            writer.write(system);
            writer.close();
            system.setDefaultState();
            JsonWealthReader reader = new JsonWealthReader("./data/testWriterEmptyWealthSet.json");
            reader.read();
            assertEquals(true, ManagerSystem.getInstance().getAllUnits().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NoSuchUnitException e) {
            fail("Error in reading the fail");
        } catch (DuplicateTagException e) {
            fail("Duplicate Exception thrown");
        }
    }

    @Test
    void testWriterGeneralWealthSet() {
        system.createUnit("RBC Account", 1210);
        system.createUnit("Home loan", -260);
        try {
            setUpGeneralTest();
            JsonWealthWriter writer = new JsonWealthWriter("./data/testWriterGeneralWealthSet.json");
            writer.open();
            writer.write(system);
            writer.close();

            system.setDefaultState();
            JsonWealthReader reader = new JsonWealthReader("./data/testWriterGeneralWealthSet.json");
            reader.read();
            List<UnitWealth> units = system.getAllUnits();
            checkUnitsAreCorrect(units);
            checkTagManagerIsCorrect(units);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NoSuchUnitException e) {
            fail ("No such Unit exception is thrown");
        } catch (DuplicateTagException e) {
            fail("Duplicate Exception thrown");
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

    //EFFECTS: setup the conditions for testWriterGeneralWealthSet
    private void setUpGeneralTest() throws NoSuchUnitException, DuplicateTagException {
        system.addTag("Home loan", "Loan");
        system.addTag("Home loan", "Home");
        system.addTag("RBC Account", "Home");
        system.addTag("RBC Account", "Bank");
        system.getUnit("RBC Account").setFluidChange(2);;
    }
}