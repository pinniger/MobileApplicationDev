package com.example.contactsdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.RandomAccess;

public class ContactSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_settings);
        initListButton();
        initMapButton();
        initSettingsButton();
        initSettings();
        initSortByClick();
        initSortOrderClick();
        initColorClick();
    }

    private void initColorClick() {
        RadioGroup rgColor = findViewById(R.id.radioGroupColor);
        rgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbYellow = findViewById(R.id.radioYellow);
                RadioButton rbGrey = findViewById(R.id.radioGrey);
                if (rbYellow.isChecked()) {
                    editSettings("color", "colorYellow");
                } else if(rbGrey.isChecked()) {
                    editSettings("color", "colorGrey");
                } else {
                    editSettings("color","colorWhite");
                }

            }
        });
    }

    private void editSettings(String type, String value){
        getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE)
                .edit()
                .putString(type,value).commit();
    }

    private void initSortByClick(){
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbName = findViewById(R.id.radioName);
                RadioButton rbCity = findViewById(R.id.radioCity);

                if (rbName.isChecked()){
                    editSettings("sortfield","contactname");

                } else if (rbCity.isChecked()){
                    editSettings("sortfield","city");
                } else {
                    editSettings("sortfield","birthday");
                }
            }
        });
    }

    private void initSortOrderClick(){
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortOrder);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbAscending = findViewById(R.id.radioAcending);

                if (rbAscending.isChecked()){
                    editSettings("sortorder","ASC");

                } else {
                    editSettings("sortorder","DESC");
                }
            }
        });
    }

    private void initSettings(){
        String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield","contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbName = findViewById(R.id.radioName);
        RadioButton rbCity = findViewById(R.id.radioCity);
        RadioButton rbBirthday = findViewById(R.id.radioBirthday);

        if (sortBy.equalsIgnoreCase("contactname")){
            rbName.setChecked(true);
        } else if(sortBy.equalsIgnoreCase("city")){
            rbCity.setChecked(true);
        } else {
            rbBirthday.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioAcending);
        RadioButton rbDescending = findViewById(R.id.radioDescending);

        if(sortOrder.equalsIgnoreCase("ASC")){
            rbAscending.setChecked(true);
        } else {
            rbDescending.setChecked(true);
        }

        String color = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("color","colorWhite");
        ScrollView sv = findViewById(R.id.scrollViewMain);
        if (color.equalsIgnoreCase("colorYellow")){
            sv.setBackgroundResource(R.color.colorYellow);
            RadioButton rbYellow = findViewById(R.id.radioYellow);
            rbYellow.setChecked(true);
        } else if(color.equalsIgnoreCase("colorGrey")){
            sv.setBackgroundResource(R.color.colorGrey);
            RadioButton rbYellow = findViewById(R.id.radioGrey);
            rbYellow.setChecked(true);
        } else {
            sv.setBackgroundResource(R.color.colorWhite);
            RadioButton rbWhite = findViewById(R.id.radioWhite);
            rbWhite.setChecked(true);
        }

    }
    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ContactSettingsActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMapButton() {
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ContactSettingsActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setEnabled(false);

    }
}