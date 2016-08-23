package com.ilogic.ohmslaw;

import com.ilogic.ohmslaw.model.Current;
import com.ilogic.ohmslaw.model.OhmsLawCalculator;
import com.ilogic.ohmslaw.model.OhmsLawCalculatorImpl;
import com.ilogic.ohmslaw.model.Power;
import com.ilogic.ohmslaw.model.Prefix;
import com.ilogic.ohmslaw.model.Resistance;
import com.ilogic.ohmslaw.model.Settings;
import com.ilogic.ohmslaw.model.Voltage;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by G on 8/13/16.
 */
public class ModelPrefixWithAutoRangeTest implements OhmsLawCalculator.OnCalculatorStateChangedListener {

    Voltage mV;
    Current mI;
    Resistance mR;
    Power mP;

    @Test
    public void shouldComputeResistanceAndPowerWithAutoRange() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setAutoRange(true);
        ol.setNumDecimalPlaces(Settings.MAX_DECIMAL_PLACES);
        ol.setVoltageQuantity(12);
        ol.setVoltagePrefix(Prefix.NANO);
        ol.setCurrentQuantity(3);
        ol.setCurrentPrefix(Prefix.NANO);
        Resistance expectedResistance = new Resistance(4, Prefix.NONE);
        Power expectedPower = new Power(0.000000036, Prefix.NANO);
        assertTrue(mR.equals(expectedResistance));
        assertTrue(mP.equals(expectedPower));

        ol.clear();
        ol.setVoltageQuantity(12);
        ol.setVoltagePrefix(Prefix.GIGA);
        ol.setCurrentQuantity(3);
        ol.setCurrentPrefix(Prefix.GIGA);
        expectedResistance = new Resistance(4, Prefix.NONE);
        expectedPower = new Power(36000000000d, Prefix.GIGA);
        assertTrue(mR.equals(expectedResistance));
        assertTrue(mP.equals(expectedPower));

        ol.clear();
        ol.setVoltageQuantity(5);
        ol.setVoltagePrefix(Prefix.NANO);
        ol.setCurrentQuantity(30);
        ol.setCurrentPrefix(Prefix.NANO);
        expectedResistance = new Resistance(166.666666667, Prefix.MILLI);
        expectedPower = new Power(0.00000015, Prefix.NANO);
        assertTrue(mR.equals(expectedResistance));
        assertTrue(mP.equals(expectedPower));
    }

    @Override
    public void onResistanceAndPowerComputed(Resistance r, Power p) {
        mR = r;
        mP = p;
    }

    @Test
    public void shouldComputeCurrentAndPower() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setAutoRange(false);
        ol.setVoltageQuantity(24);
        ol.setVoltagePrefix(Prefix.NONE);
        ol.setResistanceQuantity(20);
        ol.setResistancePrefix(Prefix.KILO);
        assertTrue(mI.equals(new Current(0.0012, Prefix.NONE)));
        assertTrue(mP.equals(new Power(0.028800000000000003, Prefix.NONE)));
    }

    @Override
    public void onCurrentAndPowerComputed(Current i, Power p) {
        mI = i;
        mP = p;
    }

    @Test
    public void shouldComputeCurrentAndResistance() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setAutoRange(false);
        ol.setVoltageQuantity(90);
        ol.setVoltagePrefix(Prefix.MILLI);
        ol.setPowerQuantity(27);
        ol.setPowerPrefix(Prefix.MILLI);
        assertTrue(mI.equals(new Current(0.300, Prefix.NONE)));
        assertTrue(mR.equals(new Resistance(0.300, Prefix.NONE)));
    }

    @Override
    public void onCurrentAndResistanceComputed(Current i, Resistance r) {
        mI = i;
        mR = r;
    }

    @Test
    public void shouldComputeVoltageAndPower() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setAutoRange(false);
        ol.setCurrentQuantity(30);
        ol.setCurrentPrefix(Prefix.MILLI);
        ol.setResistanceQuantity(166.66666666666667);
        ol.setResistancePrefix(Prefix.NONE);
        assertTrue(mV.equals(new Voltage(5, Prefix.NONE)));
        assertTrue(mP.equals(new Power(0.150, Prefix.NONE)));
    }

    @Override
    public void onVoltageAndPowerComputed(Voltage v, Power p) {
        mV = v;
        mP = p;
    }

    @Test
    public void shouldComputeVoltageAndResistance() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setAutoRange(false);
        ol.setCurrentQuantity(30);
        ol.setCurrentPrefix(Prefix.MILLI);
        ol.setPowerQuantity(150);
        ol.setPowerPrefix(Prefix.MICRO);
        assertTrue(mV.equals(new Voltage(0.005, Prefix.NONE)));
        assertTrue(mR.equals(new Resistance(0.16666666666666666, Prefix.NONE)));
    }

    @Override
    public void onVoltageAndResistanceComputed(Voltage v, Resistance r) {
        mV = v;
        mR = r;
    }

    @Test
    public void shouldComputeVoltageAndCurrent() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setAutoRange(true);
        ol.setNumDecimalPlaces(Settings.MAX_DECIMAL_PLACES);
        ol.setResistanceQuantity(6);
        ol.setResistancePrefix(Prefix.NONE);
        ol.setPowerQuantity(150);
        ol.setPowerPrefix(Prefix.KILO);
        assertTrue(mV.equals(new Voltage(948.683298051, Prefix.NONE)));
        assertTrue(mI.equals(new Current(158.113883008, Prefix.NONE)));
    }

    @Override
    public void onVoltageAndCurrentComputed(Voltage v, Current i) {
        mV = v;
        mI = i;
    }

    @Override
    public void onResistanceAndPowerSetToZero() {
    }

    @Override
    public void onCurrentAndPowerSetToZero() {
    }

    @Override
    public void onCurrentAndResistanceSetToZero() {
    }

    @Override
    public void onVoltageAndPowerSetToZero() {
    }

    @Override
    public void onVoltageAndResistanceSetToZero() {
    }

    @Override
    public void onVoltageAndCurrentSetToZero() {
    }

    @Override
    public void onClear() {
    }
}