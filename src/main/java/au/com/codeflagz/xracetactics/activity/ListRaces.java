package au.com.codeflagz.xracetactics.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import au.com.codeflagz.xracetactics.MainActivity;
import au.com.codeflagz.xracetactics.R;
import au.com.codeflagz.xracetactics.adapter.RaceAdapter;
import au.com.codeflagz.xracetactics.model.Race;
import au.com.codeflagz.xracetactics.viewmodel.HandicapViewModel;

public class ListRaces extends AppCompatActivity {

    public static final int ADD_RACE_REQUEST = 1002;
    public static final int EDIT_RACE_REQUEST = 1003;
    private HandicapViewModel handicapViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_races);

        FloatingActionButton buttonAddRace = findViewById(R.id.button_add_race);
        buttonAddRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListRaces.this, AddEditRaceActivity.class);
                startActivityForResult(intent, ADD_RACE_REQUEST);
            }
        });
        RecyclerView recycler = findViewById(R.id.races_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);
        final RaceAdapter adapter = new RaceAdapter();
        recycler.setAdapter(adapter);

        handicapViewModel = ViewModelProviders.of(this).get(HandicapViewModel.class);
        handicapViewModel.getAllRaces().observe(this, new Observer<List<Race>>() {
            @Override
            public void onChanged(List<Race> races) {
                adapter.submitList(races);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                handicapViewModel.deleteRace(adapter.getRaceAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ListRaces.this, "Race deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycler);


        adapter.setOnItemClickListener(new RaceAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Race race) {
                Intent intent = new Intent(ListRaces.this, AddEditRaceActivity.class);
                intent.putExtra(AddEditRaceActivity.RACE_ID, race.getId());
                intent.putExtra(AddEditRaceActivity.RACE_RATING, race.getRating());
                intent.putExtra(AddEditRaceActivity.RACE_RACE, race.getRace());
                intent.putExtra(AddEditRaceActivity.RACE_STARTHOUR, race.getStartHour());
                intent.putExtra(AddEditRaceActivity.RACE_STARTMINUTE, race.getStartMinute());
                startActivityForResult(intent, EDIT_RACE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_RACE_REQUEST && resultCode == RESULT_OK) {
            int startHour = data.getIntExtra(AddEditRaceActivity.RACE_STARTHOUR, 1);
            int startMinute = data.getIntExtra(AddEditRaceActivity.RACE_STARTMINUTE, 1);
            double rating = data.getDoubleExtra(AddEditRaceActivity.RACE_RATING, 1.0);
            int raceNum = data.getIntExtra(AddEditRaceActivity.RACE_RACE, 1);
            Race race = new Race(raceNum, rating, startHour, startMinute);
            handicapViewModel.insertRace(race);
            Toast.makeText(this, "Race saved", Toast.LENGTH_SHORT);
        } else if (requestCode == EDIT_RACE_REQUEST && resultCode == RESULT_OK) {
            int Id = data.getIntExtra(AddEditRaceActivity.RACE_ID, -1);
            if (Id == -1) {
                Toast.makeText(this, "Note not updated", Toast.LENGTH_SHORT);
            }
            int startHour = data.getIntExtra(AddEditRaceActivity.RACE_STARTHOUR, 1);
            int startMinute = data.getIntExtra(AddEditRaceActivity.RACE_STARTMINUTE, 1);
            double rating = data.getDoubleExtra(AddEditRaceActivity.RACE_RATING, 1.0);
            int raceNum = data.getIntExtra(AddEditRaceActivity.RACE_RACE, 1);

            Race race = new Race(raceNum, rating, startHour, startMinute);
            race.setId(Id);
            handicapViewModel.updateRace(race);

            Toast.makeText(this, "Race saved", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(this, "Race not saved", Toast.LENGTH_SHORT);
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
                Intent intent = new Intent(ListRaces.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }
}
