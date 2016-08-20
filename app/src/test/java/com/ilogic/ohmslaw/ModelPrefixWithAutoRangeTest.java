package com.ilogic.ohmslaw;

import com.ilogic.ohmslaw.model.Current;
import com.ilogic.ohmslaw.model.OhmsLawCalculator;
import com.ilogic.ohmslaw.model.OhmsLawCalculatorImpl;
import com.ilogic.ohmslaw.model.Power;
import com.ilogic.ohmslaw.model.Prefix;
import com.ilogic.ohmslaw.model.Resistance;
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
    public void shouldComputeResistanceAndPower() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setVoltageQuantity(5);
        ol.setVoltagePrefix(Prefix.NONE);
        ol.setCurrentQuantity(300);
        ol.setCurrentPrefix(Prefix.MILLI);
        assertTrue(mR.equals(new Resistance(16.666666666666668, Prefix.NONE)));
        assertTrue(mP.equals(new Power(1.5, Prefix.NONE)));
    }

    @Override
    public void onResistanceAndPowerComputed(Resistance r, Power p) {
        mR = r;
        mP = p;
    }

    @Test
    public void shouldComputeCurrentAndPower() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
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
        ol.setResistanceQuantity(1.6666666666666667);
        ol.setResistancePrefix(Prefix.NONE);
        ol.setPowerQuantity(1.5);
        ol.setPowerPrefix(Prefix.MILLI);
        assertTrue(mV.equals(new Voltage(0.050, Prefix.NONE)));
        assertTrue(mI.equals(new Current(0.030, Prefix.NONE)));
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