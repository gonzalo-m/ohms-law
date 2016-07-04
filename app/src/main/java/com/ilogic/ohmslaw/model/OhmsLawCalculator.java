package com.ilogic.ohmslaw.model;

/**
 * Created by G on 7/4/16.
 */
public interface OhmsLawCalculator {

    interface OnComputationCompletedListener {
        void onResistanceAndPowerComputed(Resistance r, Power p);
        void onCurrentAndPowerComputed(Current i, Power p);
        void onCurrentAndResistanceComputed(Current i, Resistance r);
        void onVoltageAndPowerComputed(Voltage v, Power p);
        void onVoltageAndResistanceComputed(Voltage v, Resistance r);
        void onVoltageAndCurrentComputed(Voltage v, Current i);
        void onClear();
    }

    void computeResistanceAndPower(Voltage v, Current i);

    void computeCurrentAndPower(Voltage v, Resistance r);

    void computeCurrentAndResistance(Power p, Voltage v);

    void computeVoltageAndPower(Current i, Resistance r);

    void computeVoltageAndResistance(Power p, Current i);

    void computeVoltageAndCurrent(Power p, Resistance r);

    void clear();

    Voltage getVoltage();
    Current getCurrent();
    Resistance getResistance();
    Power getPower();
}
