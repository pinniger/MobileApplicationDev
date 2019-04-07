package com.example.gametracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import static java.lang.Math.toIntExact;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class new_game extends AppCompatActivity {
    private static final String TAG = "new_game";
    final Calendar myCalendar = Calendar.getInstance();
    private Context mContext;
    private Game mCurrentGame;
    private Toolbar mToolbar;
    private Spinner mFirstPlace;
    private Spinner mSecondPlace;
    private Spinner mThridPlace;
    private EditText mDateField;
    private boolean mIsNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        mContext = this;
        mToolbar = findViewById(R.id.toolbar);
        mFirstPlace = findViewById(R.id.spinnerFirstPlace);
        mSecondPlace = findViewById(R.id.spinnerSecondPlace);
        mThridPlace = findViewById(R.id.spinnerThirdPlace);
        mDateField = findViewById(R.id.editDate);

        initSpinners();

        int id = getIntent().getIntExtra("id", -1);
        mIsNewGame = id < 0;

        if (mIsNewGame) {
            mToolbar.setTitle("New Game");
            mCurrentGame = new Game();
            mCurrentGame.setId(id);
        } else {
            // edit game
            initEditGame(id);
        }

        setSupportActionBar(mToolbar);
        initDatePicker();

        initSaveButton();
        initCancelButton();
    }

    private void initEditGame(int id) {
        try {
            DataSourceHelper dsh = new DataSourceHelper(this);
            dsh.open();
            mCurrentGame = dsh.getGame(id);
            dsh.close();

            mDateField.setText(mCurrentGame.getDateString());
            setSpinnerValue(mFirstPlace, mCurrentGame.getFirstPlace());
            setSpinnerValue(mSecondPlace, mCurrentGame.getSecondPlace());
            setSpinnerValue(mThridPlace, mCurrentGame.getThirdPlace());

        } catch (Exception e) {
            Log.d(TAG, "initEditGame: " + e.getMessage());
        }
    }

    private void setSpinnerValue(Spinner spinner, int rowId) {
        for (int i = 0; i < spinner.getCount(); i++) {
            Cursor value = (Cursor) spinner.getItemAtPosition(i);
            long id = value.getLong(value.getColumnIndex("_id"));
            if (id == rowId) {
                spinner.setSelection(i);
            }
        }
    }

    private void initSpinners() {
        try {
            DataSourceHelper dsh = new DataSourceHelper(this);
            dsh.open();
            Cursor players = dsh.getAllCursor();
            String[] from = {"name"};
            int[] to = {android.R.id.text1};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    players, from, to, 0);

            mFirstPlace.setAdapter(adapter);
            mSecondPlace.setAdapter(adapter);
            mThridPlace.setAdapter(adapter);
            dsh.close();

        } catch (Exception e) {
            Log.d(TAG, "initSpinners: " + e.getMessage());
        }
    }

    private void initCancelButton() {
        Button cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDateField = findViewById(R.id.editDate);

                int firstPlaceId = toIntExact(mFirstPlace.getSelectedItemId());
                int secondPlaceId = toIntExact(mSecondPlace.getSelectedItemId());
                int thirdPlaceId = toIntExact(mThridPlace.getSelectedItemId());

                mCurrentGame.setDateString(mDateField.getText().toString());
                mCurrentGame.setFirstPlace(firstPlaceId);
                mCurrentGame.setSecondPlace(secondPlaceId);
                mCurrentGame.setThirdPlace(thirdPlaceId);

                try {
                    DataSourceHelper dsh = new DataSourceHelper(mContext);
                    dsh.open();
                    if (mIsNewGame) {
                        dsh.insertGame(mCurrentGame);
                    } else {
                        dsh.updateGame(mCurrentGame);
                    }

                    dsh.close();
                    finish();

                } catch (Exception e) {
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
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
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
