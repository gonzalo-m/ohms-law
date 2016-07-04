package com.ilogic.ohmslaw.model;

/**
 * Created by G on 7/4/16.
 */
public interface CalculatorView {

    CalculatorPresenter getPresenter();

    void getVoltageInput();
    void getCurrentInput();
    void getResistanceInput();
    void getPowerInput();

    void setVoltage(String qty, String prefix);
    void setCurrent(String qty, String prefix);
    void setResistance(String qty, String prefix);
    void setPower(String qty, String prefix);

    void setVoltageEditTextEnabled(boolean enabled);
    void setCurrentEditTextEnabled(boolean enabled);
    void setResistanceEditTextEnabled(boolean enabled);
    void setPowerEditTextEnabled(boolean enabled);
}
