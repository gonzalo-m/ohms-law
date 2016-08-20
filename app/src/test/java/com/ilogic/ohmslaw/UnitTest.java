package com.ilogic.ohmslaw;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ilogic.ohmslaw.model.Prefix;
import com.ilogic.ohmslaw.model.Type;
import com.ilogic.ohmslaw.model.Unit;

/**
 * @deprecated
 */
public class UnitTest {

//    @Test
//    public void testUnitContructor() {
//        Unit voltage = new Unit(2.0, Prefix.NANO, Type.VOLT);
//        Unit current = new Unit(5.0, Prefix.MICRO, Type.AMP);
//        Unit resistance = new Unit(10.0, Prefix.MILLI, Type.OHM);
//        Unit power = new Unit(20.0, Prefix.NONE, Type.WATT);
//        Unit voltage2 = new Unit(2.5, Prefix.KILO, Type.VOLT);
//        Unit current2 = new Unit(5.5, Prefix.MEGA, Type.AMP);
//        Unit resistance2 = new Unit(10.5, Prefix.GIGA, Type.OHM);
//
//        String expected = "2.0 nVOLT";
//        String actual = voltage.toString();
//        System.out.println(actual);
//        assertEquals(expected, actual);
//
//        expected = "5.0 \u03BCAMP";
//        actual = current.toString();
//        System.out.println(actual);
//        assertEquals(expected, actual);
//
//        expected = "10.0 mOHM";
//        actual = resistance.toString();
//        System.out.println(actual);
//        assertEquals(expected, actual);
//
//        expected = "20.0 -WATT";
//        actual = power.toString();
//        System.out.println(actual);
//        assertEquals(expected, actual);
//
//        expected = "2.5 kVOLT";
//        actual = voltage2.toString();
//        System.out.println(actual);
//        assertEquals(expected, actual);
//
//        expected = "5.5 MAMP";
//        actual = current2.toString();
//        System.out.println(actual);
//        assertEquals(expected, actual);
//
//        expected = "10.5 GOHM";
//        actual = resistance2.toString();
//        System.out.println(actual);
//        assertEquals(expected, actual);
//    }
}