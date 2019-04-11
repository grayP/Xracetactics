package au.com.codeflagz.xracetactics.model;

import android.widget.TextView;

import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "handicap")
public class Handicap {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String boat;
    private int race;
    private double rating;
    private long elapsedtime;
    private long correctdtime;

    public Handicap(String boat, int race, double rating) {
        this.boat = boat;
        this.race = race;
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setElapsedtime(long elapsedtime) {
        this.elapsedtime = elapsedtime;
    }

    public void setCorrectdtime(long correctdtime) {
        this.correctdtime = correctdtime;
    }

    public int getId() {
        return id;
    }

    public String getBoat() {
        return boat;
    }

    public int getRace() {
        return race;
    }

    public double getRating() {
        return rating;
    }

    public long getElapsedtime() {
        return elapsedtime;
    }

    public long getCorrectdtime() {
        return correctdtime;
    }

    public String getDelta(int startHour, int startMinute, double benchRating) {

        Calendar c=Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int sec = c.get(Calendar.SECOND);

        int startSeconds= startHour*3600 + startMinute * 60;
        int secondsNow=hour * 3600 + min * 60 + sec;
        if (startSeconds==secondsNow ) return "";

        int delta =(int)((benchRating/getRating()-1.0)*(secondsNow-startSeconds));
        String suffix = delta<0 ? " in front" : " behind";
        return String.format("%02d:%02d %s", ((Math.abs(delta)/60)%60), (Math.abs(delta)%60), suffix);
    }
}
