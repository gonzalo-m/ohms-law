package com.ilogic.ohmslaw.view;

import com.ilogic.ohmslaw.presenter.CalculatorPresenter;

/**
 * Created by G on 7/4/16.
 */
public interface CalculatorView {

    CalculatorPresenter getPresenter();

    String getVoltageInput();
    String getCurrentInput();
    String getResistanceInput();
    String getPowerInput();

    void appendToVoltageEditText(String c);
    void appendToCurrentEditText(String c);
    void appendToResistanceEditText(String c);
    void appendToPowerEditText(String c);

    void setVoltageEditText(String qty);
    void setCurrentEditText(String qty);
    void setResistanceEditText(String qty);
    void setPowerEditText(String qty);

    void setVoltagePrefixText(String pfx);
    void setCurrentPrefixText(String pfx);
    void setResistancePrefixText(String pfx);
    void setPowerPrefixText(String pfx);

    void setVoltageViews(String qty, String pfx);
    void setCurrentViews(String qty, String pfx);
    void setResistanceViews(String qty, String pfx);
    void setPowerViews(String qty, String pfx);

    void setVoltageEditTextEnabled(boolean enabled);
    void setCurrentEditTextEnabled(boolean enabled);
    void setResistanceEditTextEnabled(boolean enabled);
    void setPowerEditTextEnabled(boolean enabled);

    boolean isVoltageEnabled();
    boolean isCurrentEnabled();
    boolean isResistanceEnabled();
    boolean isPowerEnabled();

    boolean isVoltageFocused();
    boolean isCurrentFocused();
    boolean isResistanceFocused();
    boolean isPowerFocused();

    String get0();
    String get1();
    String get2();
    String get3();
    String get4();
    String get5();
    String get6();
    String get7();
    String get8();
    String get9();
    String getDecimalPoint();
    String getNanoPrefix();
    String getMicroPrefix();
    String getMilliPrefix();
    String getKiloPrefix();
    String getMegaPrefix();
    String getGigaPrefix();
    String getVolsString();
    String getAmpsString();
    String getOhmsString();
    String getWattsString();
}
