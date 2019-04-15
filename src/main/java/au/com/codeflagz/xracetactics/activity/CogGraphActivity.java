package au.com.codeflagz.xracetactics.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import au.com.codeflagz.xracetactics.R;
import au.com.codeflagz.xracetactics.model.graphmodels.GpsDataPoint;
import au.com.codeflagz.xracetactics.model.graphmodels.GpsGraphData;
import au.com.codeflagz.xracetactics.services.GpsLocationService;
import au.com.codeflagz.xracetactics.viewmodel.CogGraphViewModel;

public class CogGraphActivity extends AppCompatActivity {
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Button btn_start, btn_exit;
    TextView textCog, textSog;
    boolean boolean_permission;
    private static final int REQUEST_PERMISSIONS = 100;
    private CogGraphViewModel cogGraphViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cog_graph);

       cogGraphViewModel = ViewModelProviders.of(this).get(CogGraphViewModel.class);

        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();
        cogGraphViewModel.SetupPreferences(mPref);

        cogGraphViewModel.gpsGraphFormatter.gpsGraphImage = findViewById(R.id.cogGraph);
        cogGraphViewModel.gpsGraphFormatter.SetupGraph();

        btn_start = findViewById(R.id.btn_start);
        btn_exit = findViewById(R.id.btn_exit);
        textCog=findViewById(R.id.text_cog);
        textSog=findViewById(R.id.text_sog);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boolean_permission) {
                    if (mPref.getString("service", "").matches("")) {
                        medit.putString("service", "service").commit();
                        Intent intent = new Intent(getApplicationContext(), GpsLocationService.class);
                        startService(intent);
                        SwapButtons();

                    } else {
                        if (isMyServiceRunning(GpsLocationService.class)) {
                            Toast.makeText(getApplicationContext(), "Service is already running", Toast.LENGTH_SHORT).show();

                        } else {
                            Intent intent = new Intent(getApplicationContext(), GpsLocationService.class);
                            startService(intent);
                            SwapButtons();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enable the gps", Toast.LENGTH_SHORT).show();
                }
            }

        });
        fn_permission();
    }

    private void SwapButtons() {
        if (btn_start.getVisibility() == View.VISIBLE) {
            btn_start.setVisibility(View.GONE);
            btn_exit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GpsLocationService.str_receiver));
    }

    private BroadcastReceiver broadcastReceiver;
    {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                GpsGraphData gpsGraphData = (GpsGraphData) intent.getSerializableExtra("gpsGraphData");
                cogGraphViewModel.gpsGraphFormatter.UpdateSeries(gpsGraphData.dataPoint);
                UpdateTextValues(gpsGraphData.dataPoint);
            }
        };
    }

    private void UpdateTextValues(GpsDataPoint dataPoint) {
        textCog.setText(String.format("COG: %.0f", dataPoint.getCog()));
        textSog.setText(String.format("SOG: %.2f", dataPoint.getSog()));
    }


    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(CogGraphActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {
            } else {
                ActivityCompat.requestPermissions(CogGraphActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        REQUEST_PERMISSIONS);
            }
        } else {
            boolean_permission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.show_all_handicaps):
                Intent intent = new Intent(CogGraphActivity.this, ListHandicaps.class);
                startActivity(intent);
                return true;
            case (R.id.show_all_races):
                Intent raceIntent = new Intent(CogGraphActivity.this, ListRaces.class);
                startActivity(raceIntent);
                return true;
            default:
                return false;
            //onOptionsItemSelected(item);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                String var1 = serviceClass.getName();
                String var2 = service.service.getClassName();
                Log.e("serviceClass.getName()", var1);
                Log.e("service.getClassName()", var2);
                return true;
            }
        }
        return false;
    }
}
