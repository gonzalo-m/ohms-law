import android.support.test.filters.SdkSuppress;

import org.junit.After;
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
public class SubSampleTest extends SampleTest {


    @Before
    public void startFromHomeScreen() {
        initDevice();
        pressHome();
        launchApp();
        waitForLauncher();
        waitForApp();
    }

    @Test
    public void rotationTests() {
//        for(int i = 0; i < 10; i++) {
//            rotateRight();
        String withText = "7";
            clickButton(withText);
        takeScreenshot();
//        pressPowerButton();
            delayThreeSec();
//        try {
//            getDevice().wakeUp();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//            rotateNatural();
//            delayThreeSec();
//        }
    }

    @After
    public void doSomethingAfter() {

    }
}
