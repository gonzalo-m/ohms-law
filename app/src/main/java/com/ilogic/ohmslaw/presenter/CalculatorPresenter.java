package com.ilogic.ohmslaw.presenter;

import com.ilogic.ohmslaw.model.OhmsLawCalculator;

/**
 * Created by G on 7/4/16.
 */
public interface CalculatorPresenter {

    void zeroButtonClicked();
    void oneButtonClicked();
    void twoButtonClicked();
    void threeButtonClicked();
    void fourButtonClicked();
    void fiveButtonClicked();
    void sixButtonClicked();
    void sevenButtonClicked();
    void eightButtonClicked();
    void nineButtonClicked();
    void decimalButtonClicked();
    void nanoPrefixButtonClicked();
    void microPrefixButtonClicked();
    void milliPrefixButtonClicked();
    void kiloPrefixButtonClicked();
    void megaPrefixButtonClicked();
    void gigaPrefixButtonClicked();
    void resetButtonClicked();

    void deleteButtonClicked();

    void voltageTextChanged(String s);
    void currentTextChanged(String s);
    void resistanceTextChanged(String s);
    void powerTextChanged(String s);

    OhmsLawCalculator getModel();

    void voltageLabelChanged(String s);
    void currentLabelChanged(String s);
    void resistanceLabelChanged(String s);
    void powerLabelChanged(String s);
}
