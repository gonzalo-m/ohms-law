import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
            = InstrumentationRegistry.getTargetContext().getPackageName();

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private static final String BUTTON = android.widget.Button.class.getName();

    protected UiDevice mDevice;

    /**
     * Initializes this device
     */
    public void initDevice() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    /**
     * Presses the home button
     */
    public void pressHome() {
        // press home button
        getDevice().pressHome();
    }

    /**
     * Launches the app
     */
    public void launchApp() {
        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);

        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /**
     * Waits for the launcher
     */
    public void waitForLauncher() {
        // Wait for launcher
        waitForLauncher(LAUNCH_TIMEOUT);
    }

    /**
     * @param launchTimeout
     */
    public void waitForLauncher(int launchTimeout) {
        // Wait for launcher
        final String launcherPackage = getDevice().getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        getDevice().wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                launchTimeout);
    }

    /**
     * Waits for the app to appear
     */
    public void waitForApp() {
        // Wait for the app to appear
        waitForLauncher(LAUNCH_TIMEOUT);
    }

    public void waitForApp(int appearTimeout) {
        // Wait for the app to appear
        getDevice().wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                appearTimeout);
    }

    public UiDevice getDevice() {
        return mDevice;
    }

    public void Test1() {

//        try {
//            UiObject x = getByIdAndClass("com.ilogic.ohmslaw:id/seven_button", android.widget.Button.class.getName());
//            x.click();
//            x.click();
//            x.click();
////            getByIdAndClass("com.ilogic.ohmslaw:id/eight_button", android.widget.Button.class.getName()).click();
////            getByIdAndClass("com.ilogic.ohmslaw:id/nine_button", android.widget.Button.class.getName()).click();
//        } catch (UiObjectNotFoundException e) {
//            e.printStackTrace();
//            fail();
//        }
    }

    public void rotateLeft() {
        rotateLeft(1);
    }

    public void rotateLeft(int numRotations) {
        try {
            for (int i = 0; i < numRotations; i++) {
                getDevice().setOrientationLeft();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void rotateRight() {
        rotateRight(1);
    }

    public void rotateRight(int numRotations) {
        try {
            for (int i = 0; i < numRotations; i++) {
                getDevice().setOrientationRight();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void rotateNatural() {
        try {
            getDevice().setOrientationNatural();
        } catch (RemoteException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void delayOneSec() {
        delay(1000);
    }

    public void delayFiveSec() {
        delay(5000);
    }

    public void delayThreeSec() {
        delay(3000);
    }

    public void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void clickButton(String withText) {
        click(withText, "Button");
    }
    public void click(String viewText, String widget) {
        try {
            UiObject x = getByTextAndClass(viewText, getClassName(widget));
            x.click();
//            x.click();
//            x.click();
//            getByIdAndClass("com.ilogic.ohmslaw:id/eight_button", android.widget.Button.class.getName()).click();
//            getByIdAndClass("com.ilogic.ohmslaw:id/nine_button", android.widget.Button.class.getName()).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void takeScreenshotSys(String name) {
        String s = Environment.getExternalStorageState();
        Log.i("GGGGGG",s);
        File dir =
                new File(s,
                        "app_screenshots");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.d("Screenshot Test", "Oops! Failed create directory");
            }
        }

        File file = new File(dir.getPath() + File.separator + name + ".jpg");

//        mDevice.takeScreenshot(file);
    }

    public void takeScreenshot() {
        getDevice().takeScreenshot(new File("/sdcard/shot1"));
//        Process process = null;
//        try {
//            process = Runtime.getRuntime().exec("adb shell screencap -p /sdcard/screen.png");
//            InputStream output = new BufferedInputStream(process.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            process.waitFor();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void pressPowerButton() {
        try {
            getDevice().sleep();
        } catch (RemoteException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public String getClassName(String widget) {
        switch(widget) {
            case "Button":
                return android.widget.Button.class.getName();

        }
        throw new IllegalArgumentException("Provide a valid class name. " +
                "A valid class name can be obtained by using the UiAutomatorViewer tool");
    }

    public UiObject getByTextAndClass(String text, String className) {
        return new UiObject(new UiSelector().text(text).className(className));
    }

    public UiObject getByIdAndClass(String text, String className) {
        return new UiObject(new UiSelector().className(android.widget.Button.class.getName())
                .resourceId(text));
    }
}
