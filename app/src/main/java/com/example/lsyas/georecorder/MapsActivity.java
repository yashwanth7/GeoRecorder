package com.example.lsyas.georecorder;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    Marker marker,marker1;
    MarkerOptions mo,mo1;
    DatabaseHelper myDB;
    double lat=17.821436;
    double longi=83.341325;
    final static int  PERMISSION_ALL=1;
    final static String[] PERMISSIONS= {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        myDB = new DatabaseHelper(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        locationManager =(LocationManager) getSystemService(LOCATION_SERVICE);
        mo=new MarkerOptions().position(new LatLng(17,83)).title("my");
        //mo1=new MarkerOptions().position(new LatLng(0,0)).title("clg");
        //LatLng Myclg = new LatLng(17.821436, 83.341325);
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(Myclg));
        if(Build.VERSION.SDK_INT>=23 && !isPermissionGranted())
        {
            requestPermissions(PERMISSIONS,PERMISSION_ALL);
        }else
        {
            requestlocation();
        }
        if(!isLocationEnabled())
        {
            showAlert(1);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marker=mMap.addMarker(mo);
        //marker1=mMap.addMarker(mo1);
        //mMap.setMaxZoomPreference(14.0f);

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng Mycor = new LatLng(location.getLatitude(),location.getLongitude());
        this.lat=location.getLatitude();
        this.longi=location.getLongitude();
        LatLng Myclg = new LatLng(17.821436, 83.341325);

        marker.setPosition(Mycor);
        // marker1.setPosition(Myclg);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Mycor));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void requestlocation()
    {
        Criteria criteria=new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String Provider = locationManager.getBestProvider(criteria,true);
        locationManager.requestLocationUpdates(Provider,100,10,this);
    }
    private boolean isLocationEnabled()
    {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private boolean isPermissionGranted()
    {
        if(checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)==getPackageManager().PERMISSION_GRANTED||checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION)==getPackageManager().PERMISSION_GRANTED)
        {
            Log.v("mylog", "permission is granted");
            return true;
        }
        else
        {
            Log.v("mylog","permission not granted");
            return false;
        }
    }
    private void showAlert(final int status)
    {
        String msg,title,bntText;
        if(status==1)
        {
            msg="location set to off";
            title="enable location";
            bntText="location settings";
        }
        else
        {
            msg="allow app to access location";
            title="permission acccess";
            bntText="grant";
        }
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title).setMessage(msg);
        dialog.show();


    }
    public void onrecord(View view)
    {

        boolean isInsertd = myDB.InsertProjectData(lat,longi);
        if (isInsertd) {
            Toast.makeText(MapsActivity.this, "Project Created!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MapsActivity.this, "Project Name Allready Exists!", Toast.LENGTH_SHORT).show();
        }



    }
    public void onclick2(View view)
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
            //str.append("comments:"+res.getString(3)+"\n");

        }
        showmsg("data",str.toString());
    }
    public void showmsg(String title,String msg)
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();

    }
    public void onclick3(View view)
    {
        Intent i=new Intent(this,closing.class);
        startActivity(i);
    }
}
