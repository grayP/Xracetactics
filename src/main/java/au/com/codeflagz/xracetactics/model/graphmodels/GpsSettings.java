package au.com.codeflagz.xracetactics.model.graphmodels;
import java.io.Serializable;

public class GpsSettings implements Serializable {
    public int FastSog;
    public int FastCog;
    public int NumSeconds;
    private int numDisplay;


    public GpsSettings(){
        FastCog=5;
        FastSog=5;
        NumSeconds=60;
    }

    public int getNumDisplay() {
        return numDisplay;
    }

    public void setNumDisplay(int numDisplay) {
        this.numDisplay = numDisplay;
    }
}
