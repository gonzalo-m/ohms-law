import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by G on 8/7/16.
 */
public class CustomMatcher {

    public static Matcher<View> withTextColor(final int color) {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override public boolean matchesSafely(TextView textView) {
                int curr = textView.getTextColors().getDefaultColor();
                String strCurr = getHexColor(curr);
                int other = textView.getContext().getResources().getColor(color);
                String strOther = getHexColor(other);
                return (curr == other);
            }
            @Override public void describeTo(Description description) {
                description.appendText("has colors: " + getHexColor(color));
            }
        };
    }

    private static String getHexColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
