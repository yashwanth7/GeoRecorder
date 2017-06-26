package com.example.lsyas.georecorder;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class closing extends AppCompatActivity {

    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closing);
        myDB = new DatabaseHelper(this);
    }
    public void onclose(View view)
    {
        myDB.deletevalues();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void onview(View view)
    {
        Cursor res=myDB.GetTransactions();
        if(res.getCount()==0)
        {
            showmsg("error", "nothing found");
            return;
        }
        StringBuffer str= new StringBuffer();
        while(res.moveToNext())
        {
            str.append("id:"+res.getString(0)+"\n");
            str.append("longi:"+res.getString(1)+"\n");
            str.append("lati:"+res.getString(2)+"\n");
            str.append("comments:"+res.getString(3)+"\n");
            //str.append("comments:"+res.getString(3)+"\n");

        }
        showmsg("data", str.toString());
    }
    public void showmsg(String title,String msg)
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();

    }
    public void exported(View view)
    {
        if(!myDB.exportDatabase())
        Toast.makeText(closing.this, "Project exported", Toast.LENGTH_SHORT).show();
    }

}
