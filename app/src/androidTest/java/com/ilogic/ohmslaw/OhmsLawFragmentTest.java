package com.ilogic.ohmslaw;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import com.ilogic.ohmslaw.App;
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
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by G on 4/26/16.
 */
@RunWith(AndroidJUnit4.class)
public class OhmsLawFragmentTest {

    private static final int DEFAULT_COLOR_RES = R.color.black;
    private static final int COMPUTED_COLOR_RES = R.color.blue;

    private static final int PREFIX_BUTTON_IDS[] = { R.id.nano_prefix_button, R.id.micro_prefix_button,
        R.id.milli_prefix_button, R.id.kilo_prefix_button, R.id.mega_prefix_button, R.id.giga_prefix_button };
    private static final Prefix PREFIXES[] = { Prefix.NANO, Prefix.MICRO, Prefix.MILLI, Prefix.KILO, Prefix.MEGA, Prefix.GIGA };

    @Rule
    public ActivityTestRule<NewMainActivity> mActivityRule = new ActivityTestRule<>(NewMainActivity.class);

    @UiThreadTest
    @Before
    public void setUp() throws Throwable {
        final Activity activity = mActivityRule.getActivity();
        mActivityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KeyguardManager mKG = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock mLock = mKG.newKeyguardLock(Context.KEYGUARD_SERVICE);
                mLock.disableKeyguard();

                //turn the screen on
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
            }
        });
    }

    @Test
    public void RP_EditTextViews__ShouldBeDisabledAndEnabled__WhenValid__VI__IsEnteredAndDeleted() {
        enableDisableTestHelper(R.id.volts_edittext, R.id.amps_edittext, R.id.ohms_edittext, R.id.watts_edittext);
    }

    @Test
    public void IP_EditTextViews__ShouldBeDisabledAndEnabled__WhenValid__VR__IsEnteredAndDeleted() {
        enableDisableTestHelper(R.id.volts_edittext, R.id.ohms_edittext, R.id.amps_edittext, R.id.watts_edittext);
    }

    @Test
    public void IR_EditTextViews__ShouldBeDisabledAndEnabled__WhenValid__VP__IsEnteredAndDeleted() {
        enableDisableTestHelper(R.id.volts_edittext, R.id.watts_edittext, R.id.amps_edittext, R.id.ohms_edittext);
    }

    @Test
    public void VP_EditTextViews__ShouldBeDisabledAndEnabled__WhenValid__IR__IsEnteredAndDeleted() {
        enableDisableTestHelper(R.id.amps_edittext, R.id.ohms_edittext, R.id.volts_edittext, R.id.watts_edittext);
    }

    @Test
    public void VR_EditTextViews__ShouldBeDisabledAndEnabled__WhenValid__IP__IsEnteredAndDeleted() {
        enableDisableTestHelper(R.id.amps_edittext, R.id.watts_edittext, R.id.volts_edittext, R.id.ohms_edittext);
    }

    @Test
    public void VI_EditTextViews__ShouldBeDisabledAndEnabled__WhenValid__RP__IsEnteredAndDeleted() {
        enableDisableTestHelper(R.id.ohms_edittext, R.id.watts_edittext, R.id.volts_edittext, R.id.amps_edittext);
    }


    private void enableDisableTestHelper(int enable1, int enable2, int disable1, int disable2) {
        onView(withId(enable1)).check(matches(CustomMatcher.withTextColor(DEFAULT_COLOR_RES)));
        onView(withId(enable2)).check(matches(CustomMatcher.withTextColor(DEFAULT_COLOR_RES)));
        onView(withId(disable1)).check(matches(CustomMatcher.withTextColor(DEFAULT_COLOR_RES)));
        onView(withId(disable2)).check(matches(CustomMatcher.withTextColor(DEFAULT_COLOR_RES)));

        // computing
        onView(withId(enable1)).perform(click());
        onView(withId(R.id.five_button)).perform(click());
        onView(withId(enable2)).perform(click());
        onView(withId(R.id.two_button)).perform(click());

        onView(withId(disable1)).check(matches(not(isEnabled())));
        onView(withId(disable1)).check(matches(CustomMatcher.withTextColor(COMPUTED_COLOR_RES)));
        onView(withId(disable2)).check(matches(not(isEnabled())));
        onView(withId(disable2)).check(matches(CustomMatcher.withTextColor(COMPUTED_COLOR_RES)));

        // restoring
        onView(withId(enable2)).perform(click());
        onView(withId(R.id.delete_button)).perform(click());

        onView(withId(disable1)).check(matches(isEnabled()));
        onView(withId(disable1)).check(matches(CustomMatcher.withTextColor(DEFAULT_COLOR_RES)));
        onView(withId(disable2)).check(matches(isEnabled()));
        onView(withId(disable2)).check(matches(CustomMatcher.withTextColor(DEFAULT_COLOR_RES)));

        // computing
        onView(withId(enable2)).perform(click());
        onView(withId(R.id.two_button)).perform(click());

        onView(withId(disable1)).check(matches(not(isEnabled())));
        onView(withId(disable1)).check(matches(CustomMatcher.withTextColor(COMPUTED_COLOR_RES)));
        onView(withId(disable2)).check(matches(not(isEnabled())));
        onView(withId(disable2)).check(matches(CustomMatcher.withTextColor(COMPUTED_COLOR_RES)));

        // restoring
        onView(withId(enable1)).perform(click());
        onView(withId(R.id.delete_button)).perform(click());

        onView(withId(disable1)).check(matches(isEnabled()));
        onView(withId(disable1)).check(matches(CustomMatcher.withTextColor(DEFAULT_COLOR_RES)));
        onView(withId(disable2)).check(matches(isEnabled()));
        onView(withId(disable2)).check(matches(CustomMatcher.withTextColor(DEFAULT_COLOR_RES)));
    }

    @Test
    public void prefixButtons__ShouldUpdate__VoltageLabel() {
        String unit = mActivityRule.getActivity().getString(R.string.label_volts);
        prefixButtonsTestHelper(unit, R.id.volts_edittext, R.id.volts_textview);
    }

    @Test
    public void prefixButtons__ShouldUpdate__CurrentLabel() {
        String unit = mActivityRule.getActivity().getString(R.string.label_amps);
        prefixButtonsTestHelper(unit, R.id.amps_edittext, R.id.amps_textview);
    }

    @Test
    public void prefixButtons__ShouldUpdate__ResistanceLabel() {
        String unit = mActivityRule.getActivity().getString(R.string.label_ohms);
        prefixButtonsTestHelper(unit, R.id.ohms_edittext, R.id.ohms_textview);
    }

    @Test
    public void prefixButtons__ShouldUpdate__PowerLabel() {
        String unit = mActivityRule.getActivity().getString(R.string.label_watts);
        prefixButtonsTestHelper(unit, R.id.watts_edittext, R.id.watts_textview);
    }

    private void prefixButtonsTestHelper(String unit, int editTextId, int unitLabelId) {
        onView(withId(editTextId)).perform(click());
        for (int i = 0; i < PREFIXES.length; i++) {
            String label = PREFIXES[i].getSymbol() + unit;
            onView(withId(PREFIX_BUTTON_IDS[i])).perform(scrollTo()).perform(click());
            onView(withId(unitLabelId)).check(matches(withText(label)));
        }
    }


//    @Test
//    public void numericButtonsShouldAppendNumberToEditTextViews() {
//
//        final String SEQUENCE = "0123456789";
//
//        onView(withId(R.id.volts_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.volts_edittext)).check(matches(withText(SEQUENCE)));
//
//        onView(withId(R.id.amps_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.amps_edittext)).check(matches(withText(SEQUENCE)));
//
//        onView(withId(R.id.ohms_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.ohms_edittext)).check(matches(withText(SEQUENCE)));
//
//        onView(withId(R.id.watts_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.watts_edittext)).check(matches(withText(SEQUENCE)));
//
//        editTextViewsShouldRespondToClearButton();
//
//    }
//
//
//    public void editTextViewsShouldRespondToClearButton() {
//        final String SEQUENCE = "0123456789";
//
//        onView(withId(R.id.volts_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.volts_edittext)).check(matches(withText(SEQUENCE)));
//
//        onView(withId(R.id.amps_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.amps_edittext)).check(matches(withText(SEQUENCE)));
//
//        onView(withId(R.id.ohms_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.ohms_edittext)).check(matches(withText(SEQUENCE)));
//
//        onView(withId(R.id.watts_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.watts_edittext)).check(matches(withText(SEQUENCE)));
//
//        performClick(R.id.reset_button);
//        onView(withId(R.id.volts_edittext)).check(matches(withText("")));
//        onView(withId(R.id.amps_edittext)).check(matches(withText("")));
//        onView(withId(R.id.ohms_edittext)).check(matches(withText("")));
//        onView(withId(R.id.watts_edittext)).check(matches(withText("")));
//    }
//
//    @Test
//    public void editTextViewsShouldRespondToDeleteButton() throws InterruptedException {
//
//        onView(withId(R.id.volts_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.delete_button)).perform(LowLevelActions.pressAndHold());
//        Thread.sleep(2000);
//        onView(withId(R.id.delete_button)).perform(LowLevelActions.release());
//        onView(withId(R.id.volts_edittext)).check(matches(withText("")));
//
//        onView(withId(R.id.amps_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.delete_button)).perform(LowLevelActions.pressAndHold());
//        Thread.sleep(2000);
//        onView(withId(R.id.delete_button)).perform(LowLevelActions.release());
//        onView(withId(R.id.amps_edittext)).check(matches(withText("")));
//
//        onView(withId(R.id.ohms_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.delete_button)).perform(LowLevelActions.pressAndHold());
//        Thread.sleep(2000);
//        onView(withId(R.id.delete_button)).perform(LowLevelActions.release());
//        onView(withId(R.id.ohms_edittext)).check(matches(withText("")));
//
//        onView(withId(R.id.watts_edittext)).perform(click());
//        clickZeroToNine();
//        onView(withId(R.id.delete_button)).perform(LowLevelActions.pressAndHold());
//        Thread.sleep(2000);
//        onView(withId(R.id.delete_button)).perform(LowLevelActions.release());
//        onView(withId(R.id.watts_edittext)).check(matches(withText("")));
//
//    }
//
//
//    @Test
//    public void editTextViewsShouldDisplayOnlyOneDecimalPoint() throws InterruptedException {
//        performClick(R.id.volts_edittext);
//        performClick(R.id.decimal_point_button);
//        performClick(R.id.decimal_point_button);
//        onView(withId(R.id.volts_edittext)).check(matches(withText(".")));
//        performClick(R.id.amps_edittext);
//        performClick(R.id.decimal_point_button);
//        performClick(R.id.decimal_point_button);
//        onView(withId(R.id.amps_edittext)).check(matches(withText(".")));
//        performClick(R.id.ohms_edittext);
//        performClick(R.id.decimal_point_button);
//        performClick(R.id.decimal_point_button);
//        onView(withId(R.id.ohms_edittext)).check(matches(withText(".")));
//        performClick(R.id.watts_edittext);
//        performClick(R.id.decimal_point_button);
//        performClick(R.id.decimal_point_button);
//        onView(withId(R.id.watts_edittext)).check(matches(withText(".")));
//    }
//
//    @Test
//    public void unitLabelViewsShouldBeUpdatedWhenPrefixButtonsAreClicked() throws InterruptedException {
//
//        int[] prefixButtonIds = { R.id.nano_prefix_button, R.id.micro_prefix_button, R.id.milli_prefix_button,
//                R.id.kilo_prefix_button, R.id.mega_prefix_button, R.id.giga_prefix_button };
//        String[] prefixes = { Prefix.NANO.getSymbol(), Prefix.MICRO.getSymbol(), Prefix.MILLI.getSymbol(),
//                Prefix.KILO.getSymbol(), Prefix.MEGA.getSymbol(), Prefix.GIGA.getSymbol() };
//
//        int[] editTextIds = { R.id.volts_edittext, R.id.amps_edittext, R.id.ohms_edittext, R.id.watts_edittext };
//        int[] textViewIds = { R.id.volts_textview, R.id.amps_textview, R.id.ohms_textview, R.id.watts_textview };
//        String[] unitLabels = { "Volts", "Amps", "Ohms", "Watts" };
//
//
//        for(int i = 0; i < editTextIds.length; i++) {
//            performClick(editTextIds[i]);
//            for (int j = 0; j < prefixButtonIds.length; j++) {
//                onView(withId(prefixButtonIds[j])).perform(scrollTo()).perform(click());
//                onView(withId(textViewIds[i])).check(matches(withText(prefixes[j] + "" + unitLabels[i])));
//            }
//        }
//    }
//
//    private void performClick(@IdRes int viewId) {
//        onView(withId(viewId)).perform(click());
//    }
//
//    private void clickZeroToNine() {
//        performClick(R.id.zero_button);
//        performClick(R.id.one_button);
//        performClick(R.id.two_button);
//        performClick(R.id.three_button);
//        performClick(R.id.four_button);
//        performClick(R.id.five_button);
//        performClick(R.id.six_button);
//        performClick(R.id.seven_button);
//        performClick(R.id.eight_button);
//        performClick(R.id.nine_button);
//    }
}
