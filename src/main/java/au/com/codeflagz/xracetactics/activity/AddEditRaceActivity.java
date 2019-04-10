package au.com.codeflagz.xracetactics.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import au.com.codeflagz.xracetactics.R;
import au.com.codeflagz.xracetactics.model.TimePickerFragment;

public class AddEditRaceActivity extends AppCompatActivity  implements TimePickerDialog.OnTimeSetListener {

    public static final String RACE_ID = "au.com.codeflagz.xracetactics.NEW_ID";
    public static final String RACE_RATING = "au.com.codeflagz.xracetactics.RACE_RATING";
    public static final String RACE_STARTHOUR = "au.com.codeflagz.xracetactics.RACE_STARTHOUR";
    public static final String RACE_STARTMINUTE = "au.com.codeflagz.xracetactics.RACE_STARTMINUTE";
    public static final String RACE_RACE = "au.com.codeflagz.xracetactics.RACE_RACE";
    private TextView textViewStartTime;
    private TextView textViewRating;
    private NumberPicker numberRace;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_race);

        textViewStartTime = findViewById(R.id.text_start_race_time);
        textViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time Picker");
            }
        });


        textViewRating = findViewById(R.id.text_view_rating);
        numberRace = findViewById(R.id.number_race);

        numberRace.setMinValue(1);
        numberRace.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(RACE_ID)) {
            setTitle("Edit Race");
            textViewStartTime.setText(String.format("%d:%d",intent.getIntExtra(RACE_STARTHOUR,1), intent.getIntExtra(RACE_STARTMINUTE,1)));
            textViewRating.setText(String.format("%.2f", intent.getDoubleExtra(RACE_RATING,1.0)));
            numberRace.setValue(intent.getIntExtra(RACE_RACE,1));
        }
        else
        {
            setTitle("Add Race");
        }
    }

    private void SaveRace() {
     //   String StartTime = textViewStartTme.getText().toString();
        String Rating = textViewRating.getText().toString();
        int Race = numberRace.getValue();
        if (hour==0 || Rating.trim().isEmpty()) {
            Toast.makeText(this, "Please insert Boat name and race", Toast.LENGTH_SHORT);
            return;
        }
        Intent data = new Intent();
        data.putExtra(RACE_STARTHOUR, hour);
        data.putExtra(RACE_STARTMINUTE, minute);
        data.putExtra(RACE_RATING, Double.parseDouble(Rating));
        data.putExtra(RACE_RACE, Race);
        int id = getIntent().getIntExtra(RACE_ID,-1);

        if (id!=-1){
            data.putExtra(RACE_ID,id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflator = getMenuInflater();
        menuInflator.inflate(R.menu.add_race_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_race:
                SaveRace();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
        hour=hourOfDay;
        minute=minuteOfDay;
        textViewStartTime.setText(String.format("%d:%d",hour,minute));
    }
}
