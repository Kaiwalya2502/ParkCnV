package com.example.parkcnv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class DocsAndTime extends AppCompatActivity {
    Button rentalAgreementSelectionButton , nocSelectionButton,allDoneButton;
    int READ_REQUEST_CODE = 42;
    TextView rentalAgreementPath , nocPath;
    EditText spotStartTimePicker , spotEndTimePicker;
    int hour,minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs_and_time);

        rentalAgreementSelectionButton = findViewById(R.id.accept_rental_agreement_button);
        nocSelectionButton = findViewById(R.id.accept_noc_button);
        rentalAgreementPath = findViewById(R.id.file_name_for_rental_agreement);
        nocPath = findViewById(R.id.file_path_for_noc);
        rentalAgreementSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int READ_REQUEST_CODE = 42;
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });
        nocSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int READ_REQUEST_CODE = 45;
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });
        spotStartTimePicker = findViewById(R.id.start_time_for_spot);
        spotStartTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartTime();
            }
        });
        spotEndTimePicker =findViewById(R.id.end_time_for_spot);
        spotEndTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndTime();
            }
        });
        allDoneButton = findViewById(R.id.all_done_button);
        allDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocsAndTime.this,renteComplete.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 42 && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                rentalAgreementPath.setText(""+uri.toString());

            }
        }
        if (requestCode == 45 && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                nocPath.setText(""+uri.toString());

            }
        }
    }
    public void setStartTime(){
        Calendar c = Calendar.getInstance();
        hour= c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view,int hourofDay,int minute){

                spotStartTimePicker.setText(""+hourofDay+":"+minute);
            }
        },hour,minutes,false);
        timePickerDialog.show();
    }
    public void setEndTime(){
        Calendar c = Calendar.getInstance();
        hour= c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view,int hourofDay,int minute){

                spotStartTimePicker.setText(""+hourofDay+":"+minute);
            }
        },hour,minutes,false);
        timePickerDialog.show();
    }


}
