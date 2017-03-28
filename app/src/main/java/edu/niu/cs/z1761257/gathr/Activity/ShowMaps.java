package edu.niu.cs.z1761257.gathr.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;

import java.util.ArrayList;
import java.util.List;

import edu.niu.cs.z1761257.gathr.Adapter.EventAdapter;
import edu.niu.cs.z1761257.gathr.Model.Events;
import edu.niu.cs.z1761257.gathr.R;

public class ShowMaps extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

    private GoogleMap map;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude, longitude;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_map);

        buildGoogleApiClient();

        if(mGoogleApiClient!= null){
            mGoogleApiClient.connect();
        }
        else {
            Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();
        }


        configure_button();


        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.showMapFragment)).getMap();
        List<Events> mapEvent = EventAdapter.eventList;
        List<Marker> markers = new ArrayList<Marker>();
        for(Events m: mapEvent){
            ParseGeoPoint pg = m.getGeoPoint();


            Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(pg.getLatitude(),pg.getLongitude())).title(m.getTitle())); //...
            markers.add(marker);
        }
        markers.size();


    }


    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

    }

    public void goBack(View view){
        finish();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
       Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onConnected(Bundle arg0) {


        configure_button();
        if (mLastLocation != null) {

            //set latitude and longitude to current location
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();


            try{
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                map.getUiSettings().setZoomControlsEnabled(true);
                map.getUiSettings().setAllGesturesEnabled(true);
                map.getUiSettings().setCompassEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.getUiSettings().setRotateGesturesEnabled(true);
                map.getUiSettings().setTiltGesturesEnabled(true);
                map.getUiSettings().isScrollGesturesEnabled();



                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(latitude,longitude)).zoom(10).build();

                Toast.makeText(this, "Zoom out to find more events", Toast.LENGTH_LONG).show();


                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(this, "Connection suspended...", Toast.LENGTH_SHORT).show();

    }
}


