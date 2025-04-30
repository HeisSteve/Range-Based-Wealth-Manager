package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUnitWealth {
    private UnitWealth posTestUW;
    private UnitWealth negTestUW;
    private UnitWealth thirdTestUW;
 

    @BeforeEach
    void runBefore() {
        TagManager tagManager = new TagManager();
        posTestUW = new UnitWealth("UnitA", 100, tagManager);
        negTestUW = new UnitWealth("UnitB", -120, tagManager);
        thirdTestUW = new UnitWealth("UnitC", 500, tagManager);

    }

    @Test
    void testPositiveConstructor() {
        assertEquals("UnitA", posTestUW.getName());
        assertEquals(100, posTestUW.getAmount());
        assertEquals(0, posTestUW.getFluidChange());
        assertEquals(true, posTestUW.getType());

    }

    @Test
    void testNegativeConstructor() {
        assertEquals("UnitB", negTestUW.getName());
        assertEquals(-120, negTestUW.getAmount());
        assertEquals(0, negTestUW.getFluidChange());
        assertEquals(false, negTestUW.getType());
      
    }

    @Test
    void testChangeName() {
        posTestUW.changeName("UnitT");
        assertEquals("UnitT", posTestUW.getName());
    }


    @Test
    void testChangePositiveAmount() {
        posTestUW.changeAmount(100);
        assertEquals(200, posTestUW.getAmount());
        assertEquals(true, posTestUW.getType());
    }

    @Test
    void testChangeManyPositiveAmount() {
        posTestUW.changeAmount(60);
        assertEquals(160, posTestUW.getAmount());
        assertEquals(true, posTestUW.getType());
        posTestUW.changeAmount(100);
        assertEquals(260, posTestUW.getAmount());
        assertEquals(true, posTestUW.getType());
        posTestUW.changeAmount(0);
        assertEquals(260, posTestUW.getAmount());
        assertEquals(true, posTestUW.getType());
        posTestUW.changeAmount(1.11);
        assertEquals(261.11, posTestUW.getAmount());
        assertEquals(true, posTestUW.getType());
    }

    @Test
    void testChangeZeroAmount() {
        posTestUW.changeAmount(-100);
        assertEquals(0, posTestUW.getAmount());
        assertEquals(true, posTestUW.getType());
    }

    @Test
    void testChangeOverNegativeAmount() {
        posTestUW.changeAmount(-200);
        assertEquals(-100, posTestUW.getAmount());
        assertEquals(false, posTestUW.getType());
    }

    @Test
    void testChangeManyNegativeAmount() {
        posTestUW.changeAmount(-10);
        assertEquals(90, posTestUW.getAmount());
        assertEquals(true, posTestUW.getType());
        posTestUW.changeAmount(-40);
        assertEquals(50, posTestUW.getAmount());
        assertEquals(true, posTestUW.getType());
    }

    @Test
    void testChangeMixAmount() {
        posTestUW.changeAmount(-120);
        assertEquals(-20, posTestUW.getAmount());
        assertEquals(false, posTestUW.getType());
        posTestUW.changeAmount(30);
        assertEquals(10, posTestUW.getAmount());
        assertEquals(true, posTestUW.getType());
        posTestUW.changeAmount(-100);
        assertEquals(-90, posTestUW.getAmount());
        assertEquals(false, posTestUW.getType());
    }

    @Test
    void testSetFluidChange() {
        posTestUW.setFluidChange(20);
        assertEquals(20, posTestUW.getFluidChange());
    }

    
    @Test
    void testToString() {
        assertEquals("UnitA", posTestUW.toString());
        assertEquals("UnitB", negTestUW.toString());
    }
}
