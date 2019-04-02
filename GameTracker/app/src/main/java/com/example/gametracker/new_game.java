package com.example.gametracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import static java.lang.Math.toIntExact;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class new_game extends AppCompatActivity {
    private static final String TAG = "new_game";
    final Calendar myCalendar = Calendar.getInstance();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        mContext = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("New Game");
        setSupportActionBar(toolbar);
        initDatePicker();

        try {
            DataSourceHelper pds = new DataSourceHelper(this);
            pds.open();
            Cursor players = pds.getAllCursor();
            String[] from = { "name" };
            int[] to = { android.R.id.text1 };

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    players, from, to, 0);

            Spinner firstPlace = findViewById(R.id.spinnerFirstPlace);
            firstPlace.setAdapter(adapter);

            Spinner secondPlace = findViewById(R.id.spinnerSecondPlace);
            secondPlace.setAdapter(adapter);

            Spinner thirdPlace = findViewById(R.id.spinnerThirdPlace);
            thirdPlace.setAdapter(adapter);

            pds.close();

        } catch (Exception e){
            Log.d(TAG, "onCreate: " + e.getMessage());
        }

        initSaveButton();
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner firstPlace = findViewById(R.id.spinnerFirstPlace);
                Spinner secondPlace = findViewById(R.id.spinnerSecondPlace);
                Spinner thirdPlace = findViewById(R.id.spinnerThirdPlace);
                final EditText dateField = findViewById(R.id.editDate);

                int firstPlaceId = toIntExact(firstPlace.getSelectedItemId());
                int secondPlaceId = toIntExact(secondPlace.getSelectedItemId());
                int thirdPlaceId = toIntExact(thirdPlace.getSelectedItemId());

                Game game = new Game(
                        dateField.getText().toString(),
                        firstPlaceId,
                        secondPlaceId,
                        thirdPlaceId
                );

                try {
                    DataSourceHelper dsh = new DataSourceHelper(mContext);
                    dsh.open();
                    dsh.insertGame(game);
                    dsh.close();
                    finish();

                } catch (Exception e){
                    Log.d(TAG, "onClick: " + e.getMessage());
                }


            }
        });
    }

    private void initDatePicker() {
        final EditText dateField = findViewById(R.id.editDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(new_game.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


}
