package edu.niu.cs.z1761257.gathr;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SingleEventView extends AppCompatActivity {

    // Declare Variables
    String title,
           hostname,
           start,
           end;

    GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.activity_single_event_view);

        Intent i = getIntent();
        // Get the result of title
        title = i.getStringExtra("title");
        // Get the result of hostname
        hostname = i.getStringExtra("hostname");
        // Get the result of start
        start = i.getStringExtra("start");
        // Get the result of end
        end = i.getStringExtra("end");
        //Get the result of location

        //get latitude and longitude from parse - geopoint
        Double lat = i.getExtras().getDouble("lat");
        Double lng = i.getExtras().getDouble("lng");



        //Toast.makeText(SingleEventView.this, "location: "+ lat, Toast.LENGTH_SHORT).show();

        // Locate the TextViews in singleitemview.xml
        TextView titleTV = (TextView) findViewById(R.id.titleTextView);
        TextView hostnameTV = (TextView) findViewById(R.id.hostnameTextView);
        TextView startTV = (TextView) findViewById(R.id.startTextView);
        TextView endTV = (TextView) findViewById(R.id.endTextView);

        // Set results to the TextViews
        titleTV.setText("Title: "+title);
        hostnameTV.setText("Host: "+hostname);
        startTV.setText("Start: "+start);
        endTV.setText("End: "+end);

        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
        }

        try{

            //map settings
            map = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            MarkerOptions k = new MarkerOptions()
                    .position(new LatLng(lat,lng))
                    .title(title);
            map.addMarker(k);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(false);
            map.getUiSettings().setAllGesturesEnabled(false);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setRotateGesturesEnabled(true);
            map.getUiSettings().setTiltGesturesEnabled(true);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat,lng)).zoom(16).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }catch (Exception e){
            e.printStackTrace();
        }//end of try - catch

    }//end of onCreate

    //goBack button
    public void goBack(View view){
        finish();
    }//end of goBack

}//end of SingleEventView
