package au.com.codeflagz.xracetactics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import au.com.codeflagz.xracetactics.R;
import au.com.codeflagz.xracetactics.adapter.HandicapAdapter;
import au.com.codeflagz.xracetactics.model.Handicap;
import au.com.codeflagz.xracetactics.viewmodel.HandicapViewModel;

public class ListHandicaps extends AppCompatActivity {
    public static final int ADD_HANDICAP_REQUEST = 1000;
    public static final int EDIT_HANDICAP_REQUEST = 1001;

    private HandicapViewModel handicapViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddHandicap = findViewById(R.id.button_add_node);
        buttonAddHandicap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListHandicaps.this, AddEditHandicapActivity.class);
                startActivityForResult(intent, ADD_HANDICAP_REQUEST);
            }
        });
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);

        final HandicapAdapter adapter = new HandicapAdapter();
        recycler.setAdapter(adapter);

        handicapViewModel = ViewModelProviders.of(this).get(HandicapViewModel.class);
        handicapViewModel.getAllHandicaps().observe(this, new Observer<List<Handicap>>() {
            @Override
            public void onChanged(List<Handicap> handicaps) {
                adapter.submitList(handicaps);
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
                handicapViewModel.delete(adapter.getHandicapAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ListHandicaps.this, "Handicap deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycler);

        adapter.setOnItemClickListener(new HandicapAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Handicap handicap) {
                Intent intent = new Intent(ListHandicaps.this, AddEditHandicapActivity.class);
                intent.putExtra(AddEditHandicapActivity.NEW_ID, handicap.getId());
                intent.putExtra(AddEditHandicapActivity.NEW_BOAT, handicap.getBoat());
                intent.putExtra(AddEditHandicapActivity.NEW_RACE, handicap.getRace());
                intent.putExtra(AddEditHandicapActivity.NEW_RATING, handicap.getRating());
                startActivityForResult(intent, EDIT_HANDICAP_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_HANDICAP_REQUEST && resultCode == RESULT_OK) {
            String boatName = data.getStringExtra(AddEditHandicapActivity.NEW_BOAT);
            double rating = data.getDoubleExtra(AddEditHandicapActivity.NEW_RATING, 1.0);
            int race = data.getIntExtra(AddEditHandicapActivity.NEW_RACE, 1);
            Handicap handicap = new Handicap(boatName, race, rating);
            handicapViewModel.insert(handicap);
            Toast.makeText(this, "Handicap saved", Toast.LENGTH_SHORT);
        } else if (requestCode == EDIT_HANDICAP_REQUEST && resultCode == RESULT_OK) {
            int Id = data.getIntExtra(AddEditHandicapActivity.NEW_ID, -1);
            if (Id == -1) {
                Toast.makeText(this, "Note not updated", Toast.LENGTH_SHORT);
            }
            String boatName = data.getStringExtra(AddEditHandicapActivity.NEW_BOAT);
            double rating = data.getDoubleExtra(AddEditHandicapActivity.NEW_RATING, 1.0);
            int race = data.getIntExtra(AddEditHandicapActivity.NEW_RACE, 1);

            Handicap handicap = new Handicap(boatName, race, rating);
            handicap.setId(Id);
            handicapViewModel.update(handicap);

            Toast.makeText(this, "Handicap saved", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(this, "Handicap not saved", Toast.LENGTH_SHORT);
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
            case (R.id.show_all_races):
                Intent intent = new Intent(ListHandicaps.this, ListRaces.class);
                startActivity(intent);
                return true;
            case (R.id.show_cog_graph):
                Intent cogIntent = new Intent(ListHandicaps.this, CogGraphActivity.class);
                startActivity(cogIntent);
                return true;

            default:
                return onOptionsItemSelected(item);
        }


    }


}
