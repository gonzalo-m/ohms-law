package com.ilogic.ohmslaw.presenter;


import com.ilogic.ohmslaw.model.Current;
import com.ilogic.ohmslaw.model.OhmsLawCalculator;
import com.ilogic.ohmslaw.model.OhmsLawCalculatorImpl;
import com.ilogic.ohmslaw.model.Power;
import com.ilogic.ohmslaw.model.Prefix;
import com.ilogic.ohmslaw.model.Resistance;
import com.ilogic.ohmslaw.model.UnitNew;
import com.ilogic.ohmslaw.model.Voltage;
import com.ilogic.ohmslaw.view.CalculatorView;

/**
 * Created by G on 7/4/16.
 */
public class CalculatorPresenterImpl implements CalculatorPresenter, OhmsLawCalculator.OnCalculatorStateChangedListener {

    private CalculatorView mView;
    private OhmsLawCalculator mCalc;

    public static final String EMPTY_STR = "";

    public CalculatorPresenterImpl(CalculatorView view) {
        mView = view;
        mCalc = new OhmsLawCalculatorImpl(this);
    }

    @Override
    public void onResistanceAndPowerComputed(Resistance r, Power p) {
        getView().setResistanceEditTextEnabled(false);
        getView().setPowerEditTextEnabled(false);
        getView().setResistanceViews(str(r.getQuantity()), r.getPrefixSymbol() + getView().getOhmsString());
        getView().setPowerViews(str(p.getQuantity()), p.getPrefixSymbol() + getView().getWattsString());
    }

    @Override
    public void onCurrentAndPowerComputed(Current i, Power p) {
        getView().setCurrentEditTextEnabled(false);
        getView().setPowerEditTextEnabled(false);
        getView().setCurrentViews(str(i.getQuantity()), i.getPrefixSymbol() + getView().getAmpsString());
        getView().setPowerViews(str(p.getQuantity()), p.getPrefixSymbol() + getView().getWattsString());
    }

    @Override
    public void onCurrentAndResistanceComputed(Current i, Resistance r) {
        getView().setCurrentEditTextEnabled(false);
        getView().setResistanceEditTextEnabled(false);
        getView().setCurrentViews(str(i.getQuantity()), i.getPrefixSymbol() + getView().getAmpsString());
        getView().setResistanceViews(str(r.getQuantity()), r.getPrefixSymbol() + getView().getOhmsString());
    }

    @Override
    public void onVoltageAndPowerComputed(Voltage v, Power p) {
        getView().setVoltageEditTextEnabled(false);
        getView().setPowerEditTextEnabled(false);
        getView().setVoltageViews(str(v.getQuantity()), v.getPrefixSymbol() + getView().getVolsString());
        getView().setPowerViews(str(p.getQuantity()), p.getPrefixSymbol() + getView().getWattsString());
    }

    @Override
    public void onVoltageAndResistanceComputed(Voltage v, Resistance r) {
        getView().setVoltageEditTextEnabled(false);
        getView().setResistanceEditTextEnabled(false);
        getView().setVoltageViews(str(v.getQuantity()), v.getPrefixSymbol() + getView().getVolsString());
        getView().setResistanceViews(str(r.getQuantity()), r.getPrefixSymbol() + getView().getOhmsString());
    }

    @Override
    public void onVoltageAndCurrentComputed(Voltage v, Current i) {
        getView().setVoltageEditTextEnabled(false);
        getView().setCurrentEditTextEnabled(false);
        getView().setVoltageViews(str(v.getQuantity()), v.getPrefixSymbol() + getView().getVolsString());
        getView().setCurrentViews(str(i.getQuantity()), i.getPrefixSymbol() + getView().getAmpsString());
    }

    @Override
    public void onClear() {
        getView().setVoltageEditTextEnabled(true);
        getView().setCurrentEditTextEnabled(true);
        getView().setResistanceEditTextEnabled(true);
        getView().setPowerEditTextEnabled(true);
        getView().setVoltageViews(EMPTY_STR, getView().getVolsString());
        getView().setCurrentViews(EMPTY_STR, getView().getAmpsString());
        getView().setResistanceViews(EMPTY_STR, getView().getOhmsString());
        getView().setPowerViews(EMPTY_STR, getView().getWattsString());
    }

    @Override
    public void zeroButtonClicked() {
        updateCurrentQuantityView(getView().get0());
    }

    @Override
    public void oneButtonClicked() {
        updateCurrentQuantityView(getView().get1());
    }

    @Override
    public void twoButtonClicked() {
        updateCurrentQuantityView(getView().get2());
    }

    @Override
    public void threeButtonClicked() {
        updateCurrentQuantityView(getView().get3());
    }

    @Override
    public void fourButtonClicked() {
        updateCurrentQuantityView(getView().get4());
    }

    @Override
    public void fiveButtonClicked() {
        updateCurrentQuantityView(getView().get5());
    }

    @Override
    public void sixButtonClicked() {
        updateCurrentQuantityView(getView().get6());
    }

    @Override
    public void sevenButtonClicked() {
        updateCurrentQuantityView(getView().get7());
    }

    @Override
    public void eightButtonClicked() {
        updateCurrentQuantityView(getView().get8());
    }

    @Override
    public void nineButtonClicked() {
        updateCurrentQuantityView(getView().get9());
    }

    @Override
    public void decimalButtonClicked() {
        String decimalPt = getView().getDecimalPoint();
        if (getView().isVoltageFocused()) {
            if (!getView().getVoltageInput().contains(decimalPt)) {
                getView().appendToVoltageEditText(decimalPt);
            }
        } else if (getView().isCurrentFocused()) {
            if (!getView().getCurrentInput().contains(decimalPt)) {
                getView().appendToCurrentEditText(decimalPt);
            }
        } else if (getView().isResistanceFocused()) {
            if (!getView().getResistanceInput().contains(decimalPt)) {
                getView().appendToResistanceEditText(decimalPt);
            }
        } else if (getView().isPowerFocused()) {
            if (!getView().getPowerInput().contains(decimalPt)) {
                getView().appendToPowerEditText(decimalPt);
            }
        }
    }

    @Override
    public void nanoPrefixButtonClicked() {
        updateCurrentPrefixView(getView().getNanoPrefix());
    }

    @Override
    public void microPrefixButtonClicked() {
        updateCurrentPrefixView(getView().getMicroPrefix());
    }

    @Override
    public void milliPrefixButtonClicked() {
        updateCurrentPrefixView(getView().getMilliPrefix());
    }

    @Override
    public void kiloPrefixButtonClicked() {
        updateCurrentPrefixView(getView().getKiloPrefix());
    }

    @Override
    public void megaPrefixButtonClicked() {
        updateCurrentPrefixView(getView().getMegaPrefix());
    }

    @Override
    public void gigaPrefixButtonClicked() {
        updateCurrentPrefixView(getView().getGigaPrefix());
    }

    @Override
    public void resetButtonClicked() {
        getModel().clear();
    }


    @Override
    public void voltageTextChanged(String s) {
        if (getView().isVoltageFocused()) {
            getModel().setVoltageQuantity(parseInput(s));
        }
    }

    @Override
    public void currentTextChanged(String s) {
        if (getView().isCurrentFocused()) {
            getModel().setCurrentQuantity(parseInput(s));
        }
    }

    @Override
    public void resistanceTextChanged(String s) {
        if (getView().isResistanceFocused()) {
            getModel().setResistanceQuantity(parseInput(s));
        }
    }

    @Override
    public void powerTextChanged(String s) {
        if (getView().isPowerFocused()) {
            getModel().setPowerQuantity(parseInput(s));
        }
    }

    @Override
    public void voltageLabelChanged(String s) {
        if (getView().isVoltageFocused()) {
            getModel().setVoltagePrefix(Prefix.getPrefix(s.charAt(0)));
        }
    }

    @Override
    public void currentLabelChanged(String s) {
        if (getView().isCurrentFocused()) {
            getModel().setCurrentPrefix(Prefix.getPrefix(s.charAt(0)));
        }
    }

    @Override
    public void resistanceLabelChanged(String s) {
        if (getView().isResistanceFocused()) {
            getModel().setResistancePrefix(Prefix.getPrefix(s.charAt(0)));
        }
    }

    @Override
    public void powerLabelChanged(String s) {
        if (getView().isPowerFocused()) {
            getModel().setPowerPrefix(Prefix.getPrefix(s.charAt(0)));
        }
    }

    @Override
    public void deleteButtonClicked() {
        if (getView().isVoltageFocused()) {
            String input = getView().getVoltageInput();
            if (!input.isEmpty()) {
                getView().setVoltageEditText(input.substring(0, input.length() - 1));
            }
        } else if (getView().isCurrentFocused()) {
            String input = getView().getCurrentInput();
            if (!input.isEmpty()) {
                getView().setCurrentEditText(input.substring(0, input.length() - 1));
            }

        } else if (getView().isResistanceFocused()) {
            String input = getView().getResistanceInput();
            if (!input.isEmpty()) {
                getView().setResistanceEditText(input.substring(0, input.length() - 1));
            }
        } else if (getView().isPowerFocused()) {
            String input = getView().getPowerInput();
            if (!input.isEmpty()) {
                getView().setPowerEditText(input.substring(0, input.length() - 1));
            }
        }
    }

    @Override
    public OhmsLawCalculator getModel() {
        return mCalc;
    }

    private CalculatorView getView() {
        return  mView;
    }

    private String str(double d) {
        return String.valueOf(d);
    }

    private void updateCurrentQuantityView(String c) {
        if (getView().isVoltageFocused()) {
            getView().appendToVoltageEditText(c);
        } else if (getView().isCurrentFocused()) {
            getView().appendToCurrentEditText(c);
        } else if (getView().isResistanceFocused()) {
            getView().appendToResistanceEditText(c);
        } else if (getView().isPowerFocused()) {
            getView().appendToPowerEditText(c);
        }
    }

    private void updateCurrentPrefixView(String pfx) {
        if (getView().isVoltageFocused()) {
            getView().setVoltagePrefixText(pfx + getView().getVolsString());
        } else if (getView().isCurrentFocused()) {
            getView().setCurrentPrefixText(pfx + getView().getAmpsString());
        } else if (getView().isResistanceFocused()) {
            getView().setResistancePrefixText(pfx + getView().getOhmsString());
        } else if (getView().isPowerFocused()) {
            getView().setPowerPrefixText(pfx + getView().getWattsString());
        }
    }

    private double parseInput(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return UnitNew.ZERO;
        }
    }
}