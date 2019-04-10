package au.com.codeflagz.xracetactics.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "race")
public class Race {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int race;
    private double rating;
    private int startHour;
    private int startMinute;
    private long finishTime;

    public Race(int race, double rating, int startHour, int startMinute) {
        this.race = race;
        this.rating = rating;
        this.startHour = startHour;
        this.startMinute = startMinute;
    }

    public void setId(int id) {this.id = id; }
    public void setRace(int race){ this.race=race;}
    public void setRating(double rating) {  this.rating = rating;  }
    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public int getId() { return id; }
    public int getRace() {
        return race;
    }
    public double getRating() {
        return rating;
    }
    public long getFinishTime() {
        return finishTime;
    }

    public int getStartHour() {return startHour; }
    public void setStartHour(int startHour) {this.startHour = startHour; }
    public int getStartMinute() {return startMinute;}
    public void setStartMinute(int startMinute) {this.startMinute = startMinute; }
}
