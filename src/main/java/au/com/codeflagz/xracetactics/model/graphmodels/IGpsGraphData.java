package au.com.codeflagz.xracetactics.model.graphmodels;
import android.location.Location;

public interface IGpsGraphData {
    double GetDistance(double lat1, double lat2, double lon1,
                       double lon2, double el1, double el2);
    void AddNewPoint(Location location);
}
