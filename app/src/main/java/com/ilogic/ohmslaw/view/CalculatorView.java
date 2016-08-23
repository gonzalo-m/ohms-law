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

    void setVoltagePrefixText(String pfx, String unitName);
    void setCurrentPrefixText(String pfx, String unitName);
    void setResistancePrefixText(String pfx, String unitName);
    void setPowerPrefixText(String pfx, String unitName);

    void setVoltageViews(String qty, String pfx, String unitName);
    void setCurrentViews(String qty, String pfx, String unitName);
    void setResistanceViews(String qty, String pfx, String unitName);
    void setPowerViews(String qty, String pfx, String unitName);

    void setVoltageEditTextEnabled(boolean enabled);
    void setCurrentEditTextEnabled(boolean enabled);
    void setResistanceEditTextEnabled(boolean enabled);
    void setPowerEditTextEnabled(boolean enabled);

    void setVoltageEditViewColor(int color);
    void setCurrentEditViewColor(int color);
    void setResistanceEditViewColor(int color);
    void setPowerEditViewColor(int color);

    boolean isVoltageFocused();
    boolean isCurrentFocused();
    boolean isResistanceFocused();
    boolean isPowerFocused();


    //TODO move somewhere else
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
    String getVoltsString();
    String getAmpsString();
    String getOhmsString();
    String getWattsString();

    int getComputedColor();

    int getDefaultColor();
}
