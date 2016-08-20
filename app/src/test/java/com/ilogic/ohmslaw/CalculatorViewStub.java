package com.ilogic.ohmslaw;

import com.ilogic.ohmslaw.presenter.CalculatorPresenter;
import com.ilogic.ohmslaw.presenter.CalculatorPresenterImpl;
import com.ilogic.ohmslaw.view.CalculatorView;

/**
 * Created by G on 7/4/16.
 */
public class CalculatorViewStub implements CalculatorView {

    public CalculatorPresenter mCalcPresenter;
    public String mVoltageQty, mVoltagePrefixSymbol;
    public String mCurrentQty, mCurrentPrefixSymbol;
    public String mResistanceQty, mResistancePrefixSymbol;
    public String mPowerQty, mPowerPrefixSymbol;

    public boolean didSetVoltageEditText;
    public boolean didSetCurrentEditText;
    public boolean didSetResistanceEditText;
    public boolean didSetPowerEditText;


    public CalculatorViewStub() {
        mCalcPresenter = new CalculatorPresenterImpl(this);
    }

    @Override
    public CalculatorPresenter getPresenter() {
        return mCalcPresenter;
    }

    @Override
    public String getVoltageInput() {
        return null;
    }

    @Override
    public String getCurrentInput() {
        return null;
    }

    @Override
    public String getResistanceInput() {
        return null;
    }

    @Override
    public String getPowerInput() {
        return null;
    }


    @Override
    public void setVoltageEditText(String qty) {
        didSetVoltageEditText = true;
    }

    @Override
    public void setCurrentEditText(String qty) {
        didSetCurrentEditText = true;
    }

    @Override
    public void setResistanceEditText(String qty) {
        didSetResistanceEditText = true;
    }

    @Override
    public void setPowerEditText(String qty) {
        didSetPowerEditText = true;
    }

    @Override
    public void setVoltagePrefixText(String pfx) {

    }

    @Override
    public void setCurrentPrefixText(String pfx) {

    }

    @Override
    public void setResistancePrefixText(String pfx) {

    }

    @Override
    public void setPowerPrefixText(String pfx) {

    }

    @Override
    public void setVoltageViews(String qty, String pfx) {

    }

    @Override
    public void setCurrentViews(String qty, String pfx) {

    }

    @Override
    public void setResistanceViews(String qty, String pfx) {

    }

    @Override
    public void setPowerViews(String qty, String pfx) {

    }

    @Override
    public void setVoltageEditTextEnabled(boolean enabled) {

    }

    @Override
    public void setCurrentEditTextEnabled(boolean enabled) {

    }

    @Override
    public void setResistanceEditTextEnabled(boolean enabled) {

    }

    @Override
    public void setPowerEditTextEnabled(boolean enabled) {

    }

    @Override
    public void setVoltageEditViewColor(int color) {

    }

    @Override
    public void setCurrentEditViewColor(int color) {

    }

    @Override
    public void setResistanceEditViewColor(int color) {

    }

    @Override
    public void setPowerEditViewColor(int color) {

    }

    @Override
    public boolean isVoltageFocused() {
        return false;
    }

    @Override
    public boolean isCurrentFocused() {
        return false;
    }

    @Override
    public boolean isResistanceFocused() {
        return false;
    }

    @Override
    public boolean isPowerFocused() {
        return false;
    }

    @Override
    public void appendToVoltageEditText(String c) {

    }

    @Override
    public void appendToCurrentEditText(String c) {

    }

    @Override
    public void appendToResistanceEditText(String c) {

    }

    @Override
    public void appendToPowerEditText(String c) {

    }

    @Override
    public String get0() {
        return null;
    }

    @Override
    public String get1() {
        return null;
    }

    @Override
    public String get2() {
        return null;
    }

    @Override
    public String get3() {
        return null;
    }

    @Override
    public String get4() {
        return null;
    }

    @Override
    public String get5() {
        return null;
    }

    @Override
    public String get6() {
        return null;
    }

    @Override
    public String get7() {
        return null;
    }

    @Override
    public String get8() {
        return null;
    }

    @Override
    public String get9() {
        return null;
    }

    @Override
    public String getDecimalPoint() {
        return null;
    }

    @Override
    public String getNanoPrefix() {
        return null;
    }

    @Override
    public String getMicroPrefix() {
        return null;
    }

    @Override
    public String getMilliPrefix() {
        return null;
    }

    @Override
    public String getKiloPrefix() {
        return null;
    }

    @Override
    public String getMegaPrefix() {
        return null;
    }

    @Override
    public String getGigaPrefix() {
        return null;
    }

    @Override
    public String getVolsString() {
        return null;
    }

    @Override
    public String getAmpsString() {
        return null;
    }

    @Override
    public String getOhmsString() {
        return null;
    }

    @Override
    public String getWattsString() {
        return null;
    }

    @Override
    public int getComputedColor() {
        return 0;
    }

    @Override
    public int getDefaultColor() {
        return 0;
    }
}
