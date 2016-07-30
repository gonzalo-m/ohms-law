package com.ilogic.ohmslaw.model;

/**
 * Created by G on 7/4/16.
 */
public interface OhmsLawCalculator {

    interface OnCalculatorStateChangedListener {
        void onResistanceAndPowerComputed(Resistance r, Power p);
        void onCurrentAndPowerComputed(Current i, Power p);
        void onCurrentAndResistanceComputed(Current i, Resistance r);
        void onVoltageAndPowerComputed(Voltage v, Power p);
        void onVoltageAndResistanceComputed(Voltage v, Resistance r);
        void onVoltageAndCurrentComputed(Voltage v, Current i);

        void onClear();
    }

    void setVoltageQuantity(double qty);
    void setCurrentQuantity(double qty);
    void setResistanceQuantity(double qty);
    void setPowerQuantity(double qty);

    void setVoltagePrefix(Prefix pfx);
    void setCurrentPrefix(Prefix pfx);
    void setResistancePrefix(Prefix pfx);
    void setPowerPrefix(Prefix pfx);

    void clear();
}
