import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by G on 2/6/16.
 */
@RunWith(JUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class SampleTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = InstrumentationRegistry.getTargetContext().getPackageName();;
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {

        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // press home button
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);

        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);

        try {
            UiObject x = getByIdAndClass("com.ilogic.ohmslaw:id/seven_button", android.widget.Button.class.getName());
            x.click();
            x.click();
            x.click();
//            getByIdAndClass("com.ilogic.ohmslaw:id/eight_button", android.widget.Button.class.getName()).click();
//            getByIdAndClass("com.ilogic.ohmslaw:id/nine_button", android.widget.Button.class.getName()).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            fail();
        }

//        while (true);
//        fail();
    }

    @Test
    public void Test1() {

    }

    private UiObject getByTextAndClass(String text, String className) {
        return new UiObject(new UiSelector().text(text).className(className));
    }

    private UiObject getByIdAndClass(String text, String className) {
        return new UiObject(new UiSelector().className(android.widget.Button.class.getName())
                .resourceId(text));
    }
}
