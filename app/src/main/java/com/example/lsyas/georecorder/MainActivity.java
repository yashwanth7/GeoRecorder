package com.example.lsyas.georecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        myDb = new DatabaseHelper(this);
    }
    public void onncreate(View view)
    {
        Intent i=new Intent(this,Oncreate1.class);
        startActivity(i);
    }
    public void onnopen(View view)
    {
        Intent i=new Intent(this,MapsActivity.class);
        startActivity(i);
    }
}
