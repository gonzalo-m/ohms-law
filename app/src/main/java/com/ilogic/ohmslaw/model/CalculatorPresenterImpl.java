package com.ilogic.ohmslaw.model;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by G on 7/4/16.
 */
public class CalculatorPresenterImpl implements CalculatorPresenter, OhmsLawCalculator.OnComputationCompletedListener {

    private CalculatorView mView;
    private OhmsLawCalculator mCalc;

    private Queue<String> mQueue;

    public static final String EMPTY_STR = "";

    public CalculatorPresenterImpl(CalculatorView view) {
        mView = view;
        mCalc = new OhmsLawCalculatorImpl(this);
        mQueue = new LinkedList<>();
    }

    @Override
    public void onResistanceAndPowerComputed(Resistance r, Power p) {
        mView.setResistance(str(r.getQuantity()), str(r.getPrefixSymbol()));
        mView.setPower(str(p.getQuantity()), str(p.getPrefixSymbol()));
    }

    @Override
    public void onCurrentAndPowerComputed(Current i, Power p) {

    }

    @Override
    public void onCurrentAndResistanceComputed(Current i, Resistance r) {

    }

    @Override
    public void onVoltageAndPowerComputed(Voltage v, Power p) {

    }

    @Override
    public void onVoltageAndResistanceComputed(Voltage v, Resistance r) {

    }

    @Override
    public void onVoltageAndCurrentComputed(Voltage v, Current i) {

    }

    @Override
    public void onClear() {
        getCalculatorView().setVoltage(EMPTY_STR, EMPTY_STR);
        getCalculatorView().setCurrent(EMPTY_STR, EMPTY_STR);
        getCalculatorView().setResistance(EMPTY_STR, EMPTY_STR);
        getCalculatorView().setPower(EMPTY_STR, EMPTY_STR);
    }

    @Override
    public void computeUnknownValues() {

    }

    @Override
    public void clearClicked() {
        getOhmsLawCalculator().clear();
    }

    public CalculatorView getCalculatorView() {
        return mView;
    }

    @Override
    public OhmsLawCalculator getOhmsLawCalculator() {
        return mCalc;
    }

    private String str(double d) {
        return String.valueOf(d);
    }

    private String str(char c) {
        return String.valueOf(c);
    }
}
