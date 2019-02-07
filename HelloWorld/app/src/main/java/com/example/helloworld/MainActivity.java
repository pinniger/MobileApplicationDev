package com.example.helloworld;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDisplayButton();
        initClearButton();
    }

    private void initDisplayButton(){
        Button displayButton = (Button) findViewById(R.id.buttonDisplay);
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstName = (EditText) findViewById(R.id.editTextFirst);
                EditText lastName = (EditText) findViewById(R.id.editTextLast);
                TextView textDisplay = (TextView) findViewById(R.id.textViewDisplay);

                String nameToDisplay = firstName.getText().toString() + " " + lastName.getText().toString();
                Resources res = getResources();
                String displayString = res.getString(R.string.welcome_message, nameToDisplay);
                textDisplay.setText(displayString);
            }
        });
    }

    private void initClearButton(){
        Button clearButton = (Button) findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                EditText firstName = (EditText) findViewById(R.id.editTextFirst);
                firstName.setText("");

                EditText lastName = (EditText) findViewById(R.id.editTextLast);
                lastName.setText("");

                TextView textDisplay = (TextView) findViewById(R.id.textViewDisplay);
                Resources res = getResources();
                textDisplay.setText(res.getString(R.string.display_view_text));
            }
        });
    }
}
