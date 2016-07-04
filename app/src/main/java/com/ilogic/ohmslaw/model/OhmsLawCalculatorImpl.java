package com.ilogic.ohmslaw.model;

import static com.ilogic.ohmslaw.model.UnitNew.divide;
import static com.ilogic.ohmslaw.model.UnitNew.multiply;
import static com.ilogic.ohmslaw.model.UnitNew.sqrt;

/**
 * Created by G on 7/4/16.
 */
public class OhmsLawCalculatorImpl implements OhmsLawCalculator {

    private OnComputationCompletedListener mListener;

    private Voltage mVoltage;
    private Current mCurrent;
    private Resistance mResistance;
    private Power mPower;

    public OhmsLawCalculatorImpl(OnComputationCompletedListener listener) {
        mListener = listener;
        clear();
    }

    @Override
    public void computeResistanceAndPower(Voltage v, Current i) {
        mResistance.setValue(divide(v, i)); // r = v / i
        mPower.setValue(multiply(v, i));    // p = v * i
        mListener.onResistanceAndPowerComputed(mResistance, mPower);
    }

    @Override
    public void computeCurrentAndPower(Voltage v, Resistance r) {
        mCurrent.setValue(divide(v, r));    // i = v / r;
        mPower.setValue(divide(multiply(v, v), r));     // p = v^2 / r
        mListener.onCurrentAndPowerComputed(mCurrent, mPower);
    }

    @Override
    public void computeCurrentAndResistance(Power p, Voltage v) {
        mCurrent.setValue(divide(p, v));    // i = p / v
        mResistance.setValue(divide(multiply(v, v), p));    // r = v^2 / p
        mListener.onCurrentAndResistanceComputed(mCurrent, mResistance);
    }

    @Override
    public void computeVoltageAndPower(Current i, Resistance r) {
        mVoltage.setValue(multiply(i, r));   // v = i * r
        mPower.setValue(multiply(multiply(i, i), r));   // p = i^2 * r
        mListener.onVoltageAndPowerComputed(mVoltage, mPower);
    }

    @Override
    public void computeVoltageAndResistance(Power p, Current i) {
        mVoltage.setValue(divide(p, i));    // v = p / i
        mResistance.setValue(divide(p, multiply(i, i)));    // r = p / i^2
        mListener.onVoltageAndResistanceComputed(mVoltage, mResistance);
    }

    @Override
    public void computeVoltageAndCurrent(Power p, Resistance r) {
        mVoltage.setValue(sqrt(multiply(p, r)));  // v = sqrt(p * r)
        mCurrent.setValue(sqrt(divide(p, r)));  // i = sqrt(p / r)
        mListener.onVoltageAndCurrentComputed(mVoltage, mCurrent);
    }

    @Override
    public void clear() {
        mVoltage = new Voltage(0.0d, Prefix.NONE);
        mCurrent = new Current(0.0d, Prefix.NONE);
        mResistance = new Resistance(0.0d, Prefix.NONE);
        mPower = new Power(0.0d, Prefix.NONE);
        mListener.onClear();
    }

    @Override
    public Voltage getVoltage() {
        return mVoltage;
    }

    @Override
    public Current getCurrent() {
        return mCurrent;
    }

    @Override
    public Resistance getResistance() {
        return mResistance;
    }

    @Override
    public Power getPower() {
        return mPower;
    }
}
