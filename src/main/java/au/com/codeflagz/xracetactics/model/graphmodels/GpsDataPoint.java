package au.com.codeflagz.xracetactics.model.graphmodels;

import java.io.Serializable;

//@Entity(tableName = "GpsDataPoint")
public class GpsDataPoint implements Serializable {

    //@PrimaryKey(autoGenerate = true)
    private int id;
    private double latitude;
    private double longitude;
    private long timeOfReading;
    private double sog;
    private double cog;

    public GpsDataPoint(double llat, double llong, long tor, double sog, double cog) {
        this.latitude = llat;
        this.longitude = llong;
        this.timeOfReading = tor;
        this.sog=sog;
        this.cog=cog;
    }
    public GpsDataPoint(double llat, double llong, long tor) {
        this.latitude = llat;
        this.longitude = llong;
        this.timeOfReading = tor;
        this.sog=sog;
        this.cog=cog;
    }

    public GpsDataPoint(){

    }

    public void setId(int id) {
        this.id = id;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimeofReading() {
        return timeOfReading;
    }

    public void setTimeofReading(long timeofReading) {
        this.timeOfReading = timeofReading;
    }

    public double getSog() {
        return sog;
    }

    public void setSog(double sog) {
        this.sog = sog;
    }

    public double getCog() {
        return cog;
    }

    public void setCog(double cog) {
        this.cog = cog;
    }
}
