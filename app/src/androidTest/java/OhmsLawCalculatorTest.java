import android.support.annotation.IdRes;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ilogic.ohmslaw.R;
import com.ilogic.ohmslaw.model.Prefix;
import com.ilogic.ohmslaw.view.NewMainActivity;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by G on 4/26/16.
 */
@RunWith(AndroidJUnit4.class)
public class OhmsLawCalculatorTest {

    @Rule
    public ActivityTestRule<NewMainActivity> mActivityRule = new ActivityTestRule<>(NewMainActivity.class);

    @Test
    public void numericButtonsShouldAppendNumberToEditTextViews() {

        final String SEQUENCE = "0123456789";

        onView(withId(R.id.volts_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.volts_edittext)).check(matches(withText(SEQUENCE)));

        onView(withId(R.id.amps_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.amps_edittext)).check(matches(withText(SEQUENCE)));

        onView(withId(R.id.ohms_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.ohms_edittext)).check(matches(withText(SEQUENCE)));

        onView(withId(R.id.watts_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.watts_edittext)).check(matches(withText(SEQUENCE)));

        editTextViewsShouldRespondToClearButton();

    }


    public void editTextViewsShouldRespondToClearButton() {
        final String SEQUENCE = "0123456789";

        onView(withId(R.id.volts_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.volts_edittext)).check(matches(withText(SEQUENCE)));

        onView(withId(R.id.amps_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.amps_edittext)).check(matches(withText(SEQUENCE)));

        onView(withId(R.id.ohms_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.ohms_edittext)).check(matches(withText(SEQUENCE)));

        onView(withId(R.id.watts_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.watts_edittext)).check(matches(withText(SEQUENCE)));

        performClick(R.id.reset_button);
        onView(withId(R.id.volts_edittext)).check(matches(withText("")));
        onView(withId(R.id.amps_edittext)).check(matches(withText("")));
        onView(withId(R.id.ohms_edittext)).check(matches(withText("")));
        onView(withId(R.id.watts_edittext)).check(matches(withText("")));
    }

    @Test
    public void editTextViewsShouldRespondToDeleteButton() throws InterruptedException {

        onView(withId(R.id.volts_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.delete_button)).perform(LowLevelActions.pressAndHold());
        Thread.sleep(2000);
        onView(withId(R.id.delete_button)).perform(LowLevelActions.release());
        onView(withId(R.id.volts_edittext)).check(matches(withText("")));

        onView(withId(R.id.amps_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.delete_button)).perform(LowLevelActions.pressAndHold());
        Thread.sleep(2000);
        onView(withId(R.id.delete_button)).perform(LowLevelActions.release());
        onView(withId(R.id.amps_edittext)).check(matches(withText("")));

        onView(withId(R.id.ohms_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.delete_button)).perform(LowLevelActions.pressAndHold());
        Thread.sleep(2000);
        onView(withId(R.id.delete_button)).perform(LowLevelActions.release());
        onView(withId(R.id.ohms_edittext)).check(matches(withText("")));

        onView(withId(R.id.watts_edittext)).perform(click());
        clickZeroToNine();
        onView(withId(R.id.delete_button)).perform(LowLevelActions.pressAndHold());
        Thread.sleep(2000);
        onView(withId(R.id.delete_button)).perform(LowLevelActions.release());
        onView(withId(R.id.watts_edittext)).check(matches(withText("")));

    }


    @Test
    public void editTextViewsShouldDisplayOnlyOneDecimalPoint() throws InterruptedException {
        performClick(R.id.volts_edittext);
        performClick(R.id.decimal_point_button);
        performClick(R.id.decimal_point_button);
        onView(withId(R.id.volts_edittext)).check(matches(withText(".")));
        performClick(R.id.amps_edittext);
        performClick(R.id.decimal_point_button);
        performClick(R.id.decimal_point_button);
        onView(withId(R.id.amps_edittext)).check(matches(withText(".")));
        performClick(R.id.ohms_edittext);
        performClick(R.id.decimal_point_button);
        performClick(R.id.decimal_point_button);
        onView(withId(R.id.ohms_edittext)).check(matches(withText(".")));
        performClick(R.id.watts_edittext);
        performClick(R.id.decimal_point_button);
        performClick(R.id.decimal_point_button);
        onView(withId(R.id.watts_edittext)).check(matches(withText(".")));
    }

    @Test
    public void unitLabelViewsShouldBeUpdatedWhenPrefixButtonsAreClicked() throws InterruptedException {

        int[] prefixButtonIds = { R.id.nano_prefix_button, R.id.micro_prefix_button, R.id.milli_prefix_button,
                R.id.kilo_prefix_button, R.id.mega_prefix_button, R.id.giga_prefix_button };
        String[] prefixes = { Prefix.NANO.getSymbol(), Prefix.MICRO.getSymbol(), Prefix.MILLI.getSymbol(),
                Prefix.KILO.getSymbol(), Prefix.MEGA.getSymbol(), Prefix.GIGA.getSymbol() };

        int[] editTextIds = { R.id.volts_edittext, R.id.amps_edittext, R.id.ohms_edittext, R.id.watts_edittext };
        int[] textViewIds = { R.id.volts_textview, R.id.amps_textview, R.id.ohms_textview, R.id.watts_textview };
        String[] unitLabels = { "Volts", "Amps", "Ohms", "Watts" };


        for(int i = 0; i < editTextIds.length; i++) {
            performClick(editTextIds[i]);
            for (int j = 0; j < prefixButtonIds.length; j++) {
                onView(withId(prefixButtonIds[j])).perform(scrollTo()).perform(click());
                onView(withId(textViewIds[i])).check(matches(withText(prefixes[j] + "" + unitLabels[i])));
            }
        }
    }
    
    private void performClick(@IdRes int viewId) {
        onView(withId(viewId)).perform(click());
    }

    private void clickZeroToNine() {
        performClick(R.id.zero_button);
        performClick(R.id.one_button);
        performClick(R.id.two_button);
        performClick(R.id.three_button);
        performClick(R.id.four_button);
        performClick(R.id.five_button);
        performClick(R.id.six_button);
        performClick(R.id.seven_button);
        performClick(R.id.eight_button);
        performClick(R.id.nine_button);
    }
}
