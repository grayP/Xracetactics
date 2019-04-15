package au.com.codeflagz.xracetactics.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import au.com.codeflagz.xracetactics.model.graphmodels.GpsGraphFormatter;
import au.com.codeflagz.xracetactics.model.graphmodels.GpsSettings;



public class CogGraphViewModel extends AndroidViewModel {
    public GpsGraphFormatter gpsGraphFormatter;
    public GpsSettings gpsSettings;


    public CogGraphViewModel(@NonNull Application application) {
        super(application);
        gpsGraphFormatter = new GpsGraphFormatter();
        gpsSettings = new GpsSettings();
    }

    public void SetupPreferences(SharedPreferences sharedPref) {
        gpsSettings.FastSog = sharedPref.getInt("FastSog", gpsSettings.FastSog);
        gpsSettings.FastCog = sharedPref.getInt("FastCog", gpsSettings.FastCog);
       gpsSettings.NumSeconds = sharedPref.getInt("numSeconds", gpsSettings.NumSeconds);
    }

}
