package android.maps.test;

import android.test.ActivityInstrumentationTestCase2;
import android.maps.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<RestClientActivity> {

    public HelloAndroidActivityTest() {
        super(RestClientActivity.class); 
    }

    public void testActivity() {
        RestClientActivity activity = getActivity();
        assertNotNull(activity);
    }
}

