package au.com.codeflagz.xracetactics.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import au.com.codeflagz.xracetactics.R;
import au.com.codeflagz.xracetactics.adapter.RaceDeltaAdapter;
import au.com.codeflagz.xracetactics.model.Handicap;
import au.com.codeflagz.xracetactics.viewmodel.HandicapViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RaceDelta extends AppCompatActivity {
    private TextView textViewStartTime;
    private TextView textViewRating;
    private int hour;
    private int minute;
    private double myRating;
    private int raceNum;
    private HandicapViewModel handicapViewModel;
    private Timer autoUpdate;
    private RaceDeltaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_delta);

        textViewStartTime = findViewById(R.id.text_start_race_time);
        textViewRating = findViewById(R.id.text_view_rating);
        Intent intent = getIntent();
        if (intent.hasExtra(AddEditRaceActivity.RACE_ID)) {
            SetValues(intent);
        }

        RecyclerView recycler = findViewById(R.id.race_delta_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);
        adapter = new RaceDeltaAdapter(hour, minute, myRating);
        recycler.setAdapter(adapter);

        handicapViewModel = ViewModelProviders.of(this).get(HandicapViewModel.class);
        handicapViewModel.getHandicapsForRace(raceNum).observe(this, new Observer<List<Handicap>>() {
            @Override
            public void onChanged(List<Handicap> handicaps) {
                adapter.submitList(handicaps);
             }
        });
    }

    private void updateDeltas(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        updateDeltas();
                    }
                });
            }
        }, 0, 60000); // updates each 60 secs
    }

    private void SetValues(Intent intent) {
        hour=  intent.getIntExtra(AddEditRaceActivity.RACE_STARTHOUR, 1);
        minute=intent.getIntExtra(AddEditRaceActivity.RACE_STARTMINUTE, 1);
        raceNum= intent.getIntExtra(AddEditRaceActivity.RACE_RACE, 1);
        myRating=intent.getDoubleExtra(AddEditRaceActivity.RACE_RATING, 1.0);

        setTitle(String.format("View Race %d", raceNum));
        textViewStartTime.setText(String.format("%d:%d", hour,minute));
        textViewRating.setText(String.format("%.2f", myRating));
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
                Intent intent = new Intent(RaceDelta.this, ListHandicaps.class);
                startActivity(intent);
                return true;
            case (R.id.show_all_races):
                Intent raceIntent = new Intent(RaceDelta.this, ListRaces.class);
                startActivity(raceIntent);
                return true;
            default:
                return false;
                //onOptionsItemSelected(item);
        }
    }
}
