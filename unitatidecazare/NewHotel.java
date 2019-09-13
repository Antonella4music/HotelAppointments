package com.example.antonellab.unitatidecazare;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by AntonellaB on 20-Oct-16.
 */

public class NewHotel extends AppCompatActivity implements View.OnClickListener {
    private HotelsDBHelper dbHelper ;
    EditText hotelNameEditText;
    EditText hotelAddressEditText;
    EditText hotelWebpageEditText;
    EditText hotelPhoneEditText;
    Button saveButton, goBackButton;
    int hotelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_hotel);

        hotelNameEditText = (EditText) findViewById(R.id.editTextName);
        hotelAddressEditText = (EditText) findViewById(R.id.editTextAddress);
        hotelWebpageEditText = (EditText) findViewById(R.id.editTextWebpage);
        hotelPhoneEditText = (EditText) findViewById(R.id.editTextPhone);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        goBackButton = (Button) findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewHotel.this, ListHotels.class);
                startActivity(intent);
            }
        });

        dbHelper = new HotelsDBHelper(this);

        if(hotelID > 0) {
            saveButton.setVisibility(View.GONE);
            goBackButton.setVisibility(View.GONE);

            Cursor rs = dbHelper.getHotel(hotelID);
            rs.moveToFirst();
            if (!rs.isClosed()) {
                rs.close();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:
                saveHotel();
                return;
        }
    }

    public void saveHotel() {
            if(dbHelper.insertHotel(hotelNameEditText.getText().toString(),
                    hotelAddressEditText.getText().toString(),
                    hotelWebpageEditText.getText().toString(),
                    Integer.parseInt(hotelPhoneEditText.getText().toString()))) {
                Toast.makeText(getApplicationContext(), "Hotel Inserted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Could not Insert hotel", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), ListHotels.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }