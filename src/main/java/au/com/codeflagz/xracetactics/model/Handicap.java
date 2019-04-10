package au.com.codeflagz.xracetactics.model;

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
}
