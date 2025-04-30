package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.TagNotInManagerException;

public class TagManagerTest {
    private TagManager tagManager;
    private UnitWealth unit1;
    private UnitWealth unit2;
    private UnitWealth unit3;

    @BeforeEach
    void setUp() {
        tagManager = new TagManager();
        unit1 = new UnitWealth("Test1", 100, tagManager);
        unit2 = new UnitWealth("Test2", -100, tagManager);
        unit3 = new UnitWealth("Test3", 20.5, tagManager);

  
    }

    @Test
    void testgetRelatedUnitsEmpty() {
        try {
            tagManager.getRelatedUnits("positive");
            fail("No exception thrown");
        } catch (TagNotInManagerException e) {
            // expected
        }
    }

    @Test
    void testUpdateOneTrue() {
        tagManager.update(unit1, "Tag1", true);
        try {
            List<UnitWealth> units = tagManager.getRelatedUnits("Tag1");
            assertEquals(1, units.size());
            assertEquals(unit1, units.get(0));
        } catch (TagNotInManagerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void testUpdateDuplicateTrue() {
        tagManager.update(unit1, "Tag1", true);
        tagManager.update(unit1, "Tag1", true);
        try {
            List<UnitWealth> units = tagManager.getRelatedUnits("Tag1");
            assertEquals(1, units.size());
            assertEquals(unit1, units.get(0));
        } catch (TagNotInManagerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void testUpdateManyTrue() {
        tagManager.update(unit1, "Tag1", true);
        tagManager.update(unit1, "Tag2", true);
        tagManager.update(unit2, "Tag1", true);
        tagManager.update(unit3, "Tag3", true);
        try {
            List<UnitWealth> units = tagManager.getRelatedUnits("Tag1");
            assertEquals(2, units.size());
            assertEquals(unit1, units.get(0));
            assertEquals(unit2, units.get(1));
            units = tagManager.getRelatedUnits("Tag2");
            assertEquals(1, units.size());
            assertEquals(unit1, units.get(0));
            units = tagManager.getRelatedUnits("Tag3");
            assertEquals(1, units.size());
            assertEquals(unit3, units.get(0));
        } catch (TagNotInManagerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void testUpdateFalseEmpty() {
        tagManager.update(unit1, "Tag1", true);
        tagManager.update(unit1, "Tag1", false);
        try {
            tagManager.getRelatedUnits("Tag1");
            fail("No exception thrown");
        } catch (TagNotInManagerException e) {
            // expected
        }
    }

    @Test
    void testUpdateManyFalse() {
        tagManager.update(unit1, "Tag1", true);
        tagManager.update(unit2, "Tag1", true);
        tagManager.update(unit3, "Tag1", true);
        tagManager.update(unit2, "Tag1", false);
        try {
            List<UnitWealth> units = tagManager.getRelatedUnits("Tag1");
            assertEquals(2, units.size());
            assertEquals(unit1, units.get(0));
            assertEquals(unit3, units.get(1));
        } catch (TagNotInManagerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void testUpdateManyFalseNotExist() {
        tagManager.update(unit1, "Tag1", true);
        tagManager.update(unit2, "Tag1", true);
        tagManager.update(unit1, "Tag2", false);
        try {
            List<UnitWealth> units = tagManager.getRelatedUnits("Tag1");
            assertEquals(2, units.size());
            assertEquals(unit1, units.get(0));
            assertEquals(unit2, units.get(1));
        } catch (TagNotInManagerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void testgetRelatedUnitsAmountsMany() {
        tagManager.update(unit1, "Tag1", true);
        tagManager.update(unit2, "Tag1", true);
        tagManager.update(unit3, "Tag1", true);
        try {
            double amount = tagManager.getRelatedUnitsAmounts("Tag1");
            assertEquals(20.5, amount);
        } catch (TagNotInManagerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void testgetRelatedUnitsAmountsManyWithRemove() {
        tagManager.update(unit1, "Tag1", true);
        tagManager.update(unit2, "Tag1", true);
        tagManager.update(unit3, "Tag1", true);
        tagManager.update(unit2, "Tag1", false);
        try {
            double amount = tagManager.getRelatedUnitsAmounts("Tag1");
            assertEquals(120.5, amount);
        } catch (TagNotInManagerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void testgetRelatedUnitsAmountsOne() {
        tagManager.update(unit1, "Tag1", true);
        try {
            double amount = tagManager.getRelatedUnitsAmounts("Tag1");
            assertEquals(100, amount);
        } catch (TagNotInManagerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void testgetRelatedUnitsAmountsOneWithRemove() {
        tagManager.update(unit1, "Tag1", true);
        tagManager.update(unit1, "Tag1", false);
        try {
            tagManager.getRelatedUnitsAmounts("Tag1");
        } catch (TagNotInManagerException e) {
            fail("exception thrown");
        }
    }
}
