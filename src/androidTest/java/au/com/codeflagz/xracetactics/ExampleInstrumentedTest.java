package au.com.codeflagz.xracetactics;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import au.com.codeflagz.xracetactics.model.graphmodels.GpsGraphData;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("au.com.codeflagz.xracetactics", appContext.getPackageName());
    }

    @Test
    public void GpsPointsAddedCorrectly() {

        GpsGraphData graphData = new GpsGraphData();

        for (int i = 0; i < 100; i++) {

            Location fakeLocation = new Location(LocationManager.GPS_PROVIDER);
            fakeLocation.setLongitude(153.25+i/10000.0);
            fakeLocation.setLatitude(-27.7+i/9000.0);
            fakeLocation.setTime(100000+i*10000);
            graphData.AddNewPoint(fakeLocation);
        }
        assertEquals(graphData.dataPoints.size(),12);


    }
}
