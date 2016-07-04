import com.ilogic.ohmslaw.model.CalculatorPresenterImpl;
import com.ilogic.ohmslaw.model.Current;
import com.ilogic.ohmslaw.model.Power;
import com.ilogic.ohmslaw.model.Prefix;
import com.ilogic.ohmslaw.model.Resistance;
import com.ilogic.ohmslaw.model.Voltage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by G on 7/4/16.
 */
public class CalculatorPresenterImplTest {

    CalculatorViewStub mCalcView;

    @Before
    public void setup() {
        mCalcView = new CalculatorViewStub();
    }

    @Test
    public void viewShouldSetAllUnitsToEmpty() {
        mCalcView.getPresenter().clearClicked();
        assertEquals(mCalcView.mVoltageQty, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mVoltagePrefixSymbol, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mCurrentQty, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mCurrentPrefixSymbol, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mResistanceQty, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mResistancePrefixSymbol, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mPowerQty, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mPowerPrefixSymbol, CalculatorPresenterImpl.EMPTY_STR);
    }

    @Test
    public void viewShouldDisplayResistanceAndPower() {
        mCalcView.getPresenter().computeUnknownValues();
        assertEquals(mCalcView.mVoltageQty, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mVoltagePrefixSymbol, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mCurrentQty, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mCurrentPrefixSymbol, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mResistanceQty, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mResistancePrefixSymbol, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mPowerQty, CalculatorPresenterImpl.EMPTY_STR);
        assertEquals(mCalcView.mPowerPrefixSymbol, CalculatorPresenterImpl.EMPTY_STR);
    }

    @Test
    public void modelShouldSetAllUnitsToZero() {
        mCalcView.getPresenter().clearClicked();
        assertEquals(v().getQuantity(), 0.0d, 0.0d);
        assertEquals(v().getPrefix(), Prefix.NONE);
        assertEquals(i().getQuantity(), 0.0d, 0.0d);
        assertEquals(i().getPrefix(), Prefix.NONE);
        assertEquals(r().getQuantity(), 0.0d, 0.0d);
        assertEquals(r().getPrefix(), Prefix.NONE);
        assertEquals(p().getQuantity(), 0.0d, 0.0d);
        assertEquals(p().getPrefix(), Prefix.NONE);
    }

    private Voltage v() {
        return mCalcView.getPresenter().getOhmsLawCalculator().getVoltage();
    }

    private Current i() {
        return mCalcView.getPresenter().getOhmsLawCalculator().getCurrent();
    }

    private Resistance r() {
        return mCalcView.getPresenter().getOhmsLawCalculator().getResistance();
    }

    private Power p() {
        return mCalcView.getPresenter().getOhmsLawCalculator().getPower();
    }
}
