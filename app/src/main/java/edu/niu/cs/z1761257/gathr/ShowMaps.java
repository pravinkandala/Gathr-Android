package edu.niu.cs.z1761257.gathr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

public class ShowMaps extends AppCompatActivity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_maps);

        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.showMapFragment)).getMap();
        List<Events> mapEvent = EventAdapter.eventList;
        List<Marker> markers = new ArrayList<Marker>();
        for(Events m: mapEvent){
            ParseGeoPoint pg = m.getGeoPoint();


            Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(pg.getLatitude(),pg.getLongitude())).title(m.getTitle())); //...
            markers.add(marker);
        }
        markers.size();

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
                    .target(new LatLng(41.949408,-88.776559)).zoom(13).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goBack(View view){
        finish();
    }

}
