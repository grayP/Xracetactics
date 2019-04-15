package au.com.codeflagz.xracetactics.model.graphmodels;

import android.location.Location;

import java.io.Serializable;
import java.util.LinkedList;


public class GpsGraphData implements Serializable, IGpsGraphData {

    public LinkedList<GpsDataPoint> dataPoints;
    private long lastUpdateTime;
    private GpsDataPoint lastDataPoint;
    public GpsDataPoint dataPoint;


    public GpsGraphData() {
        dataPoints = new LinkedList<>();
    }

    @Override
    public void AddNewPoint(Location location) {
        //  UpdateLastLocationIfNull(location);
        dataPoint = CreatePointFromLocation(location);
        lastDataPoint = getPointFrom30SecondsAgo(dataPoint);
        dataPoint.setCog(GetTheBearing(dataPoint, lastDataPoint));
        dataPoint.setSog(GetTheSpeed(dataPoint, lastDataPoint));

        AddPointToList(dataPoint);

    }

    GpsDataPoint getPointFrom30SecondsAgo(GpsDataPoint newPoint) {
        if (dataPoints.size() < 1) return newPoint;

        for (int i = dataPoints.size() - 1; i >= 0; i--) {
            if (newPoint.getTimeofReading() - dataPoints.get(i).getTimeofReading() > 30000) {
                return dataPoints.get(i);
            }
        }
        return dataPoints.get(0);
    }


    private void AddPointToList(GpsDataPoint newPoint) {
        if (dataPoints.size() > 60) {
            dataPoints.removeFirst();
        }
        dataPoints.addLast(newPoint);
    }

    private double GetTheSpeed(GpsDataPoint newPoint, GpsDataPoint oldPoint) {
        double distance = GetDistance(oldPoint.getLatitude(),
                newPoint.getLatitude(),
                oldPoint.getLongitude(),
                newPoint.getLongitude(), 0, 0);
        long time = (newPoint.getTimeofReading() - oldPoint.getTimeofReading()) / 1000;
        if (Math.abs(time) < 1.0 || distance < 1.0) return 0;
        return Math.round(distance / time * 1.94384 * 100) / 100.0;
    }

    private double GetTheBearing(GpsDataPoint firstPoint, GpsDataPoint secondPoint) {
        double latitude1 = Math.toRadians(firstPoint.getLatitude());
        double latitude2 = Math.toRadians(secondPoint.getLatitude());
        double longDiff = Math.toRadians(firstPoint.getLongitude() - secondPoint.getLongitude());
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        return Math.round((Math.toDegrees(Math.atan2(y, x)) + 360 - 11) % 360);
    }

//
//        protected double GetTheBearing(double lat1, double lon1, double lat2, double lon2) {
//
//        double latitude1 = Math.toRadians(lat1);
//        double latitude2 = Math.toRadians(lat2);
//        double longDiff = Math.toRadians(lon2 - lon1);
//        double y = Math.sin(longDiff) * Math.cos(latitude2);
//        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);
//
//        return (Math.toDegrees(Math.atan2(y, x)) + 360 - 11) % 360;
//    }


    private GpsDataPoint CreatePointFromLocation(Location location) {
        return new GpsDataPoint(
                location.getLatitude(),
                location.getLongitude(),
                location.getTime());
    }


    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    @Override
    public double GetDistance(double lat1, double lat2, double lon1,
                              double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        double height = el1 - el2;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
