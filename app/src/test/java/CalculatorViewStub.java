import com.ilogic.ohmslaw.model.CalculatorPresenter;
import com.ilogic.ohmslaw.model.CalculatorPresenterImpl;
import com.ilogic.ohmslaw.model.CalculatorView;

/**
 * Created by G on 7/4/16.
 */
public class CalculatorViewStub implements CalculatorView {

    public CalculatorPresenter mCalcPresenter;
    public String mVoltageQty, mVoltagePrefixSymbol;
    public String mCurrentQty, mCurrentPrefixSymbol;
    public String mResistanceQty, mResistancePrefixSymbol;
    public String mPowerQty, mPowerPrefixSymbol;


    public CalculatorViewStub() {
        mCalcPresenter = new CalculatorPresenterImpl(this);
    }

    @Override
    public CalculatorPresenter getPresenter() {
        return mCalcPresenter;
    }

    @Override
    public void getVoltageInput() {

    }

    @Override
    public void getCurrentInput() {

    }

    @Override
    public void getResistanceInput() {

    }

    @Override
    public void getPowerInput() {

    }

    @Override
    public void setVoltage(String qty, String prefix) {
        mVoltageQty = qty;
        mVoltagePrefixSymbol = prefix;
    }

    @Override
    public void setCurrent(String qty, String prefix) {
        mCurrentQty = qty;
        mCurrentPrefixSymbol = prefix;
    }

    @Override
    public void setResistance(String qty, String prefix) {
        mResistanceQty = qty;
        mResistancePrefixSymbol = prefix;
    }

    @Override
    public void setPower(String qty, String prefix) {
        mPowerQty = qty;
        mPowerPrefixSymbol = prefix;
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
}
