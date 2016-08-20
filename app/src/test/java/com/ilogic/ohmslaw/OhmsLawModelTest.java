package com.ilogic.ohmslaw;

import static org.junit.Assert.*;

import java.text.DecimalFormat;

import org.junit.Test;

import com.ilogic.ohmslaw.model.OhmsLawModel;
import com.ilogic.ohmslaw.model.Prefix;
import com.ilogic.ohmslaw.model.Type;
import com.ilogic.ohmslaw.model.Unit;

/**
 * @deprecated
 */
public class OhmsLawModelTest {

//    @Test
//    public void testDefaultConstructor() {
//        OhmsLawModel ol = new OhmsLawModel();
//        assertEquals("OhmsLawModel [voltage=0 -VOLT, current=0 -AMP, resistance=0 -OHM, power=0 -WATT]",
//                ol.toString());
//    }
//
//    @Test
//    public void testCalculateUnknowValuesNoPrefix() {
//        OhmsLawModel ol = new OhmsLawModel();
//
//        ol.setVoltage(new Unit(12, Prefix.NONE, Type.VOLT));
//        ol.setCurrent(new Unit(3, Prefix.NONE, Type.AMP));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.AMP.getId());
//        assertEquals("OhmsLawModel [voltage=12 -VOLT, current=3 -AMP, " +
//                "resistance=4 -OHM, power=36 -WATT]", ol.toString());
//
//        ol.reset();
//        assertFalse(ol.hasTwoValidValues());
//        ol.setVoltage(new Unit(12, Prefix.NONE, Type.VOLT));
//        assertFalse(ol.hasTwoValidValues());
//        ol.setResistance(new Unit(4, Prefix.NONE, Type.OHM));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.OHM.getId());
//        assertEquals("OhmsLawModel [voltage=12 -VOLT, current=3 -AMP, " +
//                "resistance=4 -OHM, power=36 -WATT]", ol.toString());
//
//        ol.reset();
//        assertFalse(ol.hasTwoValidValues());
//        ol.setVoltage(new Unit(12, Prefix.NONE, Type.VOLT));
//        assertFalse(ol.hasTwoValidValues());
//        ol.setPower(new Unit(36, Prefix.NONE, Type.WATT));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.WATT.getId());
//        assertEquals("OhmsLawModel [voltage=12 -VOLT, current=3 -AMP, " +
//                "resistance=4 -OHM, power=36 -WATT]", ol.toString());
//
//        ol.reset();
//        assertFalse(ol.hasTwoValidValues());
//        ol.setCurrent(new Unit(3, Prefix.NONE, Type.AMP));
//        assertFalse(ol.hasTwoValidValues());
//        ol.setResistance(new Unit(4, Prefix.NONE, Type.OHM));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.AMP.getId() * Type.OHM.getId());
//        assertEquals("OhmsLawModel [voltage=12 -VOLT, current=3 -AMP, " +
//                "resistance=4 -OHM, power=36 -WATT]", ol.toString());
//
//        ol.reset();
//        assertFalse(ol.hasTwoValidValues());
//        ol.setCurrent(new Unit(3, Prefix.NONE, Type.AMP));
//        assertFalse(ol.hasTwoValidValues());
//        ol.setPower(new Unit(36, Prefix.NONE, Type.WATT));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.AMP.getId() * Type.WATT.getId());
//        assertEquals("OhmsLawModel [voltage=12 -VOLT, current=3 -AMP, " +
//                "resistance=4 -OHM, power=36 -WATT]", ol.toString());
//
//        ol.reset();
//        assertFalse(ol.hasTwoValidValues());
//        ol.setResistance(new Unit(4, Prefix.NONE, Type.OHM));
//        assertFalse(ol.hasTwoValidValues());
//        ol.setPower(new Unit(36, Prefix.NONE, Type.WATT));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.OHM.getId() * Type.WATT.getId());
//        assertEquals("OhmsLawModel [voltage=12 -VOLT, current=3 -AMP, " +
//                "resistance=4 -OHM, power=36 -WATT]", ol.toString());
//
//        ol.reset();
//        assertFalse(ol.hasTwoValidValues());
//        ol.setVoltage(new Unit(12, Prefix.NONE, Type.VOLT));
//        assertFalse(ol.hasTwoValidValues());
//        ol.setPower(new Unit(36, Prefix.NONE, Type.WATT));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.WATT.getId());
//        assertEquals("OhmsLawModel [voltage=12 -VOLT, current=3 -AMP, " +
//                "resistance=4 -OHM, power=36 -WATT]", ol.toString());
//
//        ol.reset();
//        ol.setPower(new Unit(4000, Prefix.NONE, Type.WATT));
//        ol.setVoltage(new Unit(120, Prefix.NONE, Type.VOLT));
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.WATT.getId() * Type.VOLT.getId());
//        System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=120 -VOLT, current=33.333333333 -AMP, " +
//                        "resistance=3.6 -OHM, power=4000 -WATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setCurrent(new Unit(0.300, Prefix.NONE, Type.AMP));
//        ol.setVoltage(new Unit(5, Prefix.NONE, Type.VOLT));
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.AMP.getId() * Type.VOLT.getId());
//        assertEquals("OhmsLawModel [voltage=5 -VOLT, current=0.3 -AMP, " +
//                        "resistance=16.666666667 -OHM, power=1.5 -WATT]",
//                ol.toString());
//
//
//    }
//
//    @Test
//    public void testCalculateUnknowValuesWithPrefix() {
//		/* it also tests for auto-conversion */
//        OhmsLawModel ol = new OhmsLawModel();
//        ol.setVoltage(new Unit(5, Prefix.NONE, Type.VOLT));
//        ol.setCurrent(new Unit(300, Prefix.MILLI, Type.AMP));
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.AMP.getId());
//        //System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=5 -VOLT, current=300 mAMP, " +
//                        "resistance=16.666666667 -OHM, power=1.5 -WATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setVoltage(new Unit(240, Prefix.NONE, Type.VOLT));
//        ol.setPower(new Unit(5, Prefix.KILO, Type.WATT));
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.WATT.getId());
//        assertEquals("OhmsLawModel [voltage=240 -VOLT, current=20.833333333 -AMP, " +
//                        "resistance=11.52 -OHM, power=5 kWATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setVoltage(new Unit(500, Prefix.MILLI, Type.VOLT));
//        ol.setCurrent(new Unit(30, Prefix.MILLI, Type.AMP));
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.AMP.getId());
//        System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=500 mVOLT, current=30 mAMP, " +
//                        "resistance=16.666666667 -OHM, power=15 mWATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setVoltage(new Unit(3, Prefix.NONE, Type.VOLT));
//        ol.setCurrent(new Unit(3, Prefix.KILO, Type.AMP));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.AMP.getId());
//        //System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=3 -VOLT, current=3 kAMP, " +
//                        "resistance=1 mOHM, power=9 kWATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setCurrent(new Unit(300, Prefix.MILLI, Type.AMP));
//        ol.setResistance(new Unit(300, Prefix.MILLI, Type.OHM));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.AMP.getId() * Type.OHM.getId());
//        //System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=90 mVOLT, current=300 mAMP, " +
//                        "resistance=300 mOHM, power=27 mWATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setVoltage(new Unit(5, Prefix.NONE, Type.VOLT));
//        ol.setCurrent(new Unit(30, Prefix.MILLI, Type.AMP));
//        assertTrue(ol.hasTwoValidValues());
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.AMP.getId());
//        //System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=5 -VOLT, current=30 mAMP, " +
//                        "resistance=166.666666667 -OHM, power=150 mWATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setVoltage(new Unit(5, Prefix.MILLI, Type.VOLT));
//        ol.setCurrent(new Unit(30, Prefix.MILLI, Type.AMP));
//        assertTrue(ol.hasTwoValidValues());
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.AMP.getId());
//        //System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=5 mVOLT, current=30 mAMP, " +
//                        "resistance=166.666666667 mOHM, power=150 \u03BCWATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setCurrent(new Unit(30, Prefix.MILLI, Type.AMP));
//        ol.setPower(new Unit(1.5, Prefix.MILLI, Type.WATT));
//        assertTrue(ol.hasTwoValidValues());
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        ol.calculateUnknownValues(Type.AMP.getId() * Type.WATT.getId());
//        System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=50 mVOLT, current=30 mAMP, " +
//                        "resistance=1.666666667 -OHM, power=1.5 mWATT]",
//                ol.toString());
//
//
//    }
//
//    @Test
//    public void testCalculateUnkownValuesExtremeCases() {
//        OhmsLawModel ol = new OhmsLawModel();
//        ol.setVoltage(new Unit(12, Prefix.NANO, Type.VOLT));
//        ol.setCurrent(new Unit(3, Prefix.NANO, Type.AMP));
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        System.out.println(ol);
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.AMP.getId());
//        System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=12 nVOLT, current=3 nAMP, " +
//                        "resistance=4 -OHM, power=0.000000036 nWATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setVoltage(new Unit(12, Prefix.GIGA, Type.VOLT));
//        ol.setCurrent(new Unit(3, Prefix.GIGA, Type.AMP));
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.AMP.getId());
//        System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=12 GVOLT, current=3 GAMP, " +
//                        "resistance=4 -OHM, power=36000000000 GWATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setVoltage(new Unit(5, Prefix.NANO, Type.VOLT));
//        ol.setCurrent(new Unit(30, Prefix.NANO, Type.AMP));
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.VOLT.getId() * Type.AMP.getId());
//        System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=5 nVOLT, current=30 nAMP, " +
//                        "resistance=166.666666667 mOHM, power=0.00000015 nWATT]",
//                ol.toString());
//
//        ol.reset();
//        ol.setPower(new Unit(150, Prefix.KILO, Type.WATT));
//        ol.setResistance(new Unit(6, Prefix.NONE, Type.OHM));
//        Unit.setDecimalPlaces(new DecimalFormat("#0.#########"));
//        assertTrue(ol.hasTwoValidValues());
//        ol.calculateUnknownValues(Type.WATT.getId() * Type.OHM.getId());
//        System.out.println(ol);
//        assertEquals("OhmsLawModel [voltage=948.683298051 -VOLT, current=158.113883008 -AMP, " +
//                        "resistance=6 -OHM, power=150 kWATT]",
//                ol.toString());
//    }
}