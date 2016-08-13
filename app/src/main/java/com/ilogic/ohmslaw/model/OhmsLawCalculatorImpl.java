package com.ilogic.ohmslaw.model;

import java.util.Arrays;

import static com.ilogic.ohmslaw.model.UnitNew.divide;
import static com.ilogic.ohmslaw.model.UnitNew.multiply;
import static com.ilogic.ohmslaw.model.UnitNew.sqrt;

/**
 * Created by G on 7/4/16.
 */
public class OhmsLawCalculatorImpl implements OhmsLawCalculator {

    private OnCalculatorStateChangedListener mListener;
    private boolean[] mKnownUnits = new boolean[4]; // 6 possible combinations

    private Voltage mVoltage;
    private Current mCurrent;
    private Resistance mResistance;
    private Power mPower;

    public OhmsLawCalculatorImpl(OnCalculatorStateChangedListener listener) {
        mListener = listener;
        clear();
    }

    @Override
    public void setVoltageQuantity(double qty) {
        mVoltage.setQuantity(qty);
        mKnownUnits[0] = true;
        computeUnknownValues();
    }

    @Override
    public void setCurrentQuantity(double qty) {
        mCurrent.setQuantity(qty);
        mKnownUnits[1] = true;
        computeUnknownValues();
    }

    @Override
    public void setResistanceQuantity(double qty) {
        mResistance.setQuantity(qty);
        mKnownUnits[2] = true;
        computeUnknownValues();
    }

    @Override
    public void setPowerQuantity(double qty) {
        mPower.setQuantity(qty);
        mKnownUnits[3] = true;
        computeUnknownValues();
    }

    @Override
    public void setVoltagePrefix(Prefix pfx) {
        mVoltage.setPrefix(pfx);
        computeUnknownValues();
    }

    @Override
    public void setCurrentPrefix(Prefix pfx) {
        mCurrent.setPrefix(pfx);
        computeUnknownValues();
    }

    @Override
    public void setResistancePrefix(Prefix pfx) {
        mResistance.setPrefix(pfx);
        computeUnknownValues();
    }

    @Override
    public void setPowerPrefix(Prefix pfx) {
        mPower.setPrefix(pfx);
        computeUnknownValues();
    }

    @Override
    public void clear() {
        mVoltage = new Voltage();
        mCurrent = new Current();
        mResistance = new Resistance();
        mPower = new Power();
        mListener.onClear();
    }

    private boolean isInputVoltageAndCurrent() {
        return mKnownUnits[0] && mKnownUnits[1];
    }

    private boolean isInputVoltageAndResistance() {
        return mKnownUnits[0] && mKnownUnits[2];
    }

    private boolean isInputVoltageAndPower() {
        return mKnownUnits[0] && mKnownUnits[3];
    }

    private boolean isInputCurrentAndResistance() {
        return mKnownUnits[1] && mKnownUnits[2];
    }

    private boolean isInputCurrentAndPower() {
        return mKnownUnits[1] && mKnownUnits[3];
    }

    private boolean isInputResistanceAndPower() {
        return mKnownUnits[2] && mKnownUnits[3];
    }


    private void computeUnknownValues() {

        if (isInputVoltageAndCurrent()) {
            computeResistanceAndPower(mVoltage, mCurrent);
        } else if (isInputVoltageAndResistance()) {
            computeCurrentAndPower(mVoltage, mResistance);
        } else if (isInputVoltageAndPower()) {
            computeCurrentAndResistance(mPower, mVoltage);
        } else if (isInputCurrentAndResistance()) {
            computeVoltageAndPower(mCurrent, mResistance);
        } else if (isInputCurrentAndPower()) {
            computeVoltageAndResistance(mPower, mCurrent);
        } else if (isInputResistanceAndPower()) {
            computeVoltageAndCurrent(mPower, mResistance);
        }
    }

    private void computeResistanceAndPower(Voltage v, Current i) {
        mResistance.setValue(divide(v, i)); // r = v / i
        mPower.setValue(multiply(v, i));    // p = v * i
        mListener.onResistanceAndPowerComputed(mResistance, mPower);

        if (!v.isGreaterThanZero()) {
            mKnownUnits[0] = false;
        }

        if (!i.isGreaterThanZero()) {
            mKnownUnits[1] = false;
        }

        if (!mResistance.isGreaterThanZero() && !mPower.isGreaterThanZero()) {
            mListener.onResistanceAndPowerSetToZero();
        }
    }

    private void computeCurrentAndPower(Voltage v, Resistance r) {
        mCurrent.setValue(divide(v, r));    // i = v / r;
        mPower.setValue(divide(multiply(v, v), r));     // p = v^2 / r
        mListener.onCurrentAndPowerComputed(mCurrent, mPower);

        if (!v.isGreaterThanZero()) {
            mKnownUnits[0] = false;
        }

        if (!r.isGreaterThanZero()) {
            mKnownUnits[2] = false;
        }

        if (!mCurrent.isGreaterThanZero() && !mPower.isGreaterThanZero()) {
            mListener.onCurrentAndPowerSetToZero();
        }
    }


    private void computeCurrentAndResistance(Power p, Voltage v) {
        mCurrent.setValue(divide(p, v));    // i = p / v
        mResistance.setValue(divide(multiply(v, v), p));    // r = v^2 / p
        mListener.onCurrentAndResistanceComputed(mCurrent, mResistance);

        if (!p.isGreaterThanZero()) {
            mKnownUnits[3] = false;
        }

        if (!v.isGreaterThanZero()) {
            mKnownUnits[0] = false;
        }

        if (!mCurrent.isGreaterThanZero() && !mResistance.isGreaterThanZero()) {
            mListener.onCurrentAndResistanceSetToZero();
        }
    }


    private void computeVoltageAndPower(Current i, Resistance r) {
        mVoltage.setValue(multiply(i, r));   // v = i * r
        mPower.setValue(multiply(multiply(i, i), r));   // p = i^2 * r
        mListener.onVoltageAndPowerComputed(mVoltage, mPower);

        if (!i.isGreaterThanZero()) {
            mKnownUnits[1] = false;
        }

        if (!r.isGreaterThanZero()) {
            mKnownUnits[2] = false;
        }

        if (!mVoltage.isGreaterThanZero() && !mPower.isGreaterThanZero()) {
            mListener.onVoltageAndPowerSetToZero();
        }
    }


    private void computeVoltageAndResistance(Power p, Current i) {
        mVoltage.setValue(divide(p, i));    // v = p / i
        mResistance.setValue(divide(p, multiply(i, i)));    // r = p / i^2
        mListener.onVoltageAndResistanceComputed(mVoltage, mResistance);

        if (!p.isGreaterThanZero()) {
            mKnownUnits[3] = false;
        }

        if (!i.isGreaterThanZero()) {
            mKnownUnits[1] = false;
        }

        if (!mVoltage.isGreaterThanZero() && !mResistance.isGreaterThanZero()) {
            mListener.onVoltageAndResistanceSetToZero();
        }
    }


    private void computeVoltageAndCurrent(Power p, Resistance r) {
        mVoltage.setValue(sqrt(multiply(p, r)));  // v = sqrt(p * r)
        mCurrent.setValue(sqrt(divide(p, r)));  // i = sqrt(p / r)
        mListener.onVoltageAndCurrentComputed(mVoltage, mCurrent);

        if (!p.isGreaterThanZero()) {
            mKnownUnits[3] = false;
        }

        if (!r.isGreaterThanZero()) {
            mKnownUnits[2] = false;
        }

        if (!mVoltage.isGreaterThanZero() && !mCurrent.isGreaterThanZero()) {
            mListener.onVoltageAndCurrentSetToZero();
        }
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Volts: ").append(mVoltage.toString()).append("\n");
//        sb.append("Amps: ").append(mCurrent.toString()).append("\n");
//        sb.append("Ohms: ").append(mResistance.toString()).append("\n");
//        sb.append("Watts: ").append(mPower.toString()).append("\n");
//        return sb.toString();
//    }


    @Override
    public String toString() {
        return "OhmsLawCalculatorImpl{" +
                "mKnownUnits=" + Arrays.toString(mKnownUnits) + "\n" +
                ", mVoltage=" + mVoltage + "\n" +
                ", mCurrent=" + mCurrent + "\n" +
                ", mResistance=" + mResistance + "\n" +
                ", mPower=" + mPower + "\n" +
                '}';
    }
}
