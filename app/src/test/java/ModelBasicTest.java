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
 * Created by G on 8/8/16.
 */
public class ModelBasicTest implements OhmsLawCalculator.OnCalculatorStateChangedListener {

    boolean didClear;
    boolean didSetResistanceAndPowerToZero;
    boolean didSetCurrentAndPowerToZero;
    boolean didSetCurrentAndResistanceToZero;
    boolean didSetVoltageAndPowerToZero;
    boolean didSetVoltageAndResistanceToZero;
    boolean didSetVoltageAndCurrentToZero;

    Voltage mV;
    Current mI;
    Resistance mR;
    Power mP;

    @Test
    public void shouldComputeResistanceAndPower() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setVoltageQuantity(12);
        ol.setCurrentQuantity(3);
        assertTrue(mR.equals(new Resistance(4, Prefix.NONE)));
        assertTrue(mP.equals(new Power(36, Prefix.NONE)));
    }

    @Override
    public void onResistanceAndPowerComputed(Resistance r, Power p) {
        mR = r;
        mP = p;
    }

    @Test
    public void shouldComputeCurrentAndPower() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setVoltageQuantity(12);
        ol.setResistanceQuantity(4);
        assertTrue(mI.equals(new Current(3, Prefix.NONE)));
        assertTrue(mP.equals(new Power(36, Prefix.NONE)));
    }

    @Override
    public void onCurrentAndPowerComputed(Current i, Power p) {
        mI = i;
        mP = p;
    }

    @Test
    public void shouldComputeCurrentAndResistance() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setVoltageQuantity(12);
        ol.setPowerQuantity(36);
        assertTrue(mI.equals(new Current(3, Prefix.NONE)));
        assertTrue(mR.equals(new Resistance(4, Prefix.NONE)));
    }

    @Override
    public void onCurrentAndResistanceComputed(Current i, Resistance r) {
        mI = i;
        mR = r;
    }

    @Test
    public void shouldComputeVoltageAndPower() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setCurrentQuantity(3);
        ol.setResistanceQuantity(4);
        assertTrue(mV.equals(new Voltage(12, Prefix.NONE)));
        assertTrue(mP.equals(new Power(36, Prefix.NONE)));
    }

    @Override
    public void onVoltageAndPowerComputed(Voltage v, Power p) {
        mV = v;
        mP = p;
    }

    @Test
    public void shouldComputeVoltageAndResistance() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setCurrentQuantity(3);
        ol.setPowerQuantity(36);
        assertTrue(mV.equals(new Voltage(12, Prefix.NONE)));
        assertTrue(mR.equals(new Resistance(4, Prefix.NONE)));
    }

    @Override
    public void onVoltageAndResistanceComputed(Voltage v, Resistance r) {
        mV = v;
        mR = r;
    }

    @Test
    public void shouldComputeVoltageAndCurrent() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setResistanceQuantity(4);
        ol.setPowerQuantity(36);
        assertTrue(mV.equals(new Voltage(12, Prefix.NONE)));
        assertTrue(mI.equals(new Current(3, Prefix.NONE)));
    }

    @Override
    public void onVoltageAndCurrentComputed(Voltage v, Current i) {
        mV = v;
        mI = i;
    }

    @Test
    public void shouldResetValuesToZero() {
        OhmsLawCalculator ol = new OhmsLawCalculatorImpl(this);
        ol.setVoltageQuantity(12);
        ol.setCurrentQuantity(3);
        ol.setCurrentQuantity(0);
        assertTrue(didSetResistanceAndPowerToZero);
        ol.setResistanceQuantity(4);
        ol.setResistanceQuantity(0);
        assertTrue(didSetCurrentAndPowerToZero);
        ol.setPowerQuantity(36);
        ol.setPowerQuantity(0);
        assertTrue(didSetCurrentAndResistanceToZero);
        ol.setVoltageQuantity(0);
        ol.setCurrentQuantity(3);
        ol.setResistanceQuantity(4);
        ol.setResistanceQuantity(0);
        assertTrue(didSetVoltageAndPowerToZero);
        ol.setPowerQuantity(36);
        ol.setPowerQuantity(0);
        assertTrue(didSetVoltageAndResistanceToZero);
        ol.setCurrentQuantity(0);
        ol.setResistanceQuantity(4);
        ol.setPowerQuantity(36);
        ol.setPowerQuantity(0);
        assertTrue(didSetVoltageAndCurrentToZero);
    }

    @Override
    public void onResistanceAndPowerSetToZero() {
        didSetResistanceAndPowerToZero = true;
    }

    @Override
    public void onCurrentAndPowerSetToZero() {
        didSetCurrentAndPowerToZero = true;
    }

    @Override
    public void onCurrentAndResistanceSetToZero() {
        didSetCurrentAndResistanceToZero = true;
    }

    @Override
    public void onVoltageAndPowerSetToZero() {
        didSetVoltageAndPowerToZero = true;
    }

    @Override
    public void onVoltageAndResistanceSetToZero() {
        didSetVoltageAndResistanceToZero = true;
    }

    @Override
    public void onVoltageAndCurrentSetToZero() {
        didSetVoltageAndCurrentToZero = true;
    }

    @Test
    public void shouldCallClearAfterInitializingCalculator() {
        new OhmsLawCalculatorImpl(this);
        assertTrue(didClear);
    }

    @Override
    public void onClear() {
        didClear = true;
    }
}