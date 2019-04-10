package au.com.codeflagz.xracetactics.activity;

import androidx.appcompat.app.AppCompatActivity;
import au.com.codeflagz.xracetactics.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;



public class AddEditHandicapActivity extends AppCompatActivity {
    public static final String NEW_ID = "au.com.codeflagz.xracetactics.NEW_ID";
    public static final String NEW_BOAT = "au.com.codeflagz.xracetactics.NEW_BOAT";
    public static final String NEW_RATING = "au.com.codeflagz.xracetactics.NEW_RATING";
    public static final String NEW_RACE = "au.com.codeflagz.xracetactics.NEW_RACE";
    private TextView textViewBoatName;
    private TextView textViewRating;
    private NumberPicker numberRace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_handicap);

        textViewBoatName = findViewById(R.id.text_view_boatname);
        textViewRating = findViewById(R.id.text_view_rating);
        numberRace = findViewById(R.id.number_race);

        numberRace.setMinValue(1);
        numberRace.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(NEW_ID)) {
            setTitle("Edit Handicap");
            textViewBoatName.setText(intent.getStringExtra(NEW_BOAT));
            textViewRating.setText(String.format("%.2f", intent.getDoubleExtra(NEW_RATING,1.0)));
            numberRace.setValue(intent.getIntExtra(NEW_RACE,1));


        }
            else
            {
        setTitle("Add handicap");}

    }

    private void SaveHandicap() {
        String BoatName = textViewBoatName.getText().toString();
        String Rating = textViewRating.getText().toString();
        int Race = numberRace.getValue();
        if (BoatName.trim().isEmpty() || Rating.trim().isEmpty()) {
            Toast.makeText(this, "Please insert Boat name and handicap", Toast.LENGTH_SHORT);
            return;
        }
        Intent data = new Intent();
        data.putExtra(NEW_BOAT, BoatName);
        data.putExtra(NEW_RATING, Double.parseDouble(Rating));
        data.putExtra(NEW_RACE, Race);

        int id = getIntent().getIntExtra(NEW_ID,-1);

        if (id!=-1){
            data.putExtra(NEW_ID,id);
        }
        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflator = getMenuInflater();
        menuInflator.inflate(R.menu.add_handicap_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_handicap:
                SaveHandicap();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
