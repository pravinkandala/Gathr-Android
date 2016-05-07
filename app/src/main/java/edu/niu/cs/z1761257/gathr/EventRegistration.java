package edu.niu.cs.z1761257.gathr;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Geocoder;
import android.support.v4.app.Fragment;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventRegistration extends Fragment {


    ParseObject eventObject = new ParseObject("Events");

    private EditText titleET,
            hostNameET,
            startDetailsET,
            endDetailsET,
            locationET;

    private Button saveBtn, clearBtn;

//    Context thiscontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ParseApplication p = (ParseApplication)getActivity().getApplication();

//        thiscontext = container.getContext();


//        Parse.enableLocalDatastore(thiscontext);
//        Parse.initialize(thiscontext, getString(R.string.YOUR_APPLICATION_ID), getString(R.string.YOUR_CLIENT_KEY));


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_event_registration, container, false);


        // Execute RemoteDataTask AsyncTask

        titleET = (EditText) view.findViewById(R.id.titleEditText);
        hostNameET = (EditText) view.findViewById(R.id.hostNameEditText);
        startDetailsET = (EditText) view.findViewById(R.id.startDetailsEditText);
        endDetailsET = (EditText) view.findViewById(R.id.endDetailsEditText);
        locationET = (EditText) view.findViewById(R.id.locationEditText);
        saveBtn = (Button) view.findViewById(R.id.saveButton);
        clearBtn = (Button) view.findViewById(R.id.clearButton);

//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                titleET.setText("");
                hostNameET.setText("");
                startDetailsET.setText("");
                endDetailsET.setText("");
                locationET.setText("");
            }
        });



        eventObject.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }//end of onCreate



    public void getLatLongFromPlace(String place) {
        try {
            Geocoder selected_place_geocoder = new Geocoder(getActivity());
            List<Address> address;

            address = selected_place_geocoder.getFromLocationName(place, 5);

            if (address == null) {
               Toast.makeText(getActivity(),"Adderss cannot be null",Toast.LENGTH_SHORT).show();
            } else {
                Address location = address.get(0);

                ParseGeoPoint point = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
                eventObject.put("Location",point);

               // Toast.makeText(this,"something went into address",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            fetchLatLongFromService fetch_latlng_from_service_abc = new fetchLatLongFromService(
                    place.replaceAll("\\s+", ""));
            fetch_latlng_from_service_abc.execute();

        }

    }


//Sometimes happens that device gives location = null

    public class fetchLatLongFromService extends
            AsyncTask<Void, Void, StringBuilder> {
        String place;


        public fetchLatLongFromService(String place) {
            super();
            this.place = place;

        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
            this.cancel(true);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                HttpURLConnection conn = null;
                StringBuilder jsonResults = new StringBuilder();
                String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="
                        + this.place + "&sensor=false";

                URL url = new URL(googleMapUrl);
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(
                        conn.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                String a = "";
                return jsonResults;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());
                JSONArray resultJsonArray = jsonObj.getJSONArray("results");

                // Extract the Place descriptions from the results
                // resultList = new ArrayList<String>(resultJsonArray.length());

                JSONObject before_geometry_jsonObj = resultJsonArray
                        .getJSONObject(0);

                JSONObject geometry_jsonObj = before_geometry_jsonObj
                        .getJSONObject("geometry");

                JSONObject location_jsonObj = geometry_jsonObj
                        .getJSONObject("location");

                String lat_helper = location_jsonObj.getString("lat");
                double lat = Double.valueOf(lat_helper);


                String lng_helper = location_jsonObj.getString("lng");
                double lng = Double.valueOf(lng_helper);

                ParseGeoPoint point1 = new ParseGeoPoint(lat,lng);
                eventObject.put("Location",point1);

//                LatLng point = new LatLng(lat, lng);
//                ParseGeoPoint point = new ParseGeoPoint(30.0, -20.0);
//                ParseObject object = new ParseObject("PlaceObject");
//                object.put("location", point);
//                object.save();



            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }


}


