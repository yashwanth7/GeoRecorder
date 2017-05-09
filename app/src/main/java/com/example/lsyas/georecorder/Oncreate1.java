package com.example.lsyas.georecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Oncreate1 extends AppCompatActivity {

    Button submit;
    EditText pname;
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oncreate1);
        myDB = new DatabaseHelper(this);
        pname = (EditText) findViewById(R.id.Project_Name);

    }
    public void createproject(View view)
    {
        try {
            if (pname.getText().toString().trim().length() == 0) {
                Toast.makeText(Oncreate1.this, "Fill All Required Values", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInsertd = myDB.InsertDataIntoProject(pname.getText().toString());
            if (!isInsertd) {
                Toast.makeText(Oncreate1.this, "Project Created!", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(this,MapsActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(Oncreate1.this, "Project Name Allready Exists!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(Oncreate1.this, "Fill Project  With Valid Data!", Toast.LENGTH_SHORT).show();
        }

    }

}
