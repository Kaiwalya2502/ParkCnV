package com.example.parkcnv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RenterInformation extends AppCompatActivity {
    Button uploadRcButton , uploadDlButton , proceedToMapsButton;
    TextView rcFilePath , dlFilePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_information);


        rcFilePath = findViewById(R.id.rc_path);
        dlFilePath= findViewById(R.id.driver_license_path);
        proceedToMapsButton = findViewById(R.id.proceed_to_renter_maps_activity_button);
        proceedToMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RenterInformation.this,RenterMaps.class);
                startActivity(intent);
            }
        });
        uploadDlButton = findViewById(R.id.upload_dl_button);
        uploadDlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int READ_REQUEST_CODE = 42;
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });
        uploadRcButton= findViewById(R.id.upload_rc_button);
        uploadRcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int READ_REQUEST_CODE = 45;
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
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
                Log.i("hello", "Uri: " + uri.toString());
                dlFilePath.setText(""+uri.toString());

            }
        }
        if (requestCode == 45 && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("hello", "Uri: " + uri.toString());
                rcFilePath.setText(""+uri.toString());

            }
        }
    }
}
