package com.example.lsyas.georecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lsyas on 10-05-2017.
 */
public class PopUp extends Activity {
    DatabaseHelper myDB;
    double lat, longi;
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        comment = (EditText) findViewById(R.id.comments);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .4), (int) (height * .6));
        Intent mIntent = getIntent();
        myDB = new DatabaseHelper(this);
        lat = mIntent.getDoubleExtra("firstmsg",1);
        longi = mIntent.getDoubleExtra("secondmsg",1);
    }

    public void store(View view) {
        if (comment.getText().toString().trim().length() == 0) {
            Toast.makeText(PopUp.this, "Fill All Required Values", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInsertd = myDB.InsertProjectData(lat,longi,comment.getText().toString());
        if (isInsertd) {
            Toast.makeText(PopUp.this, "values recorded!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PopUp.this, "vallues not recorded", Toast.LENGTH_SHORT).show();
        }
    }
}
