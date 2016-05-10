package edu.niu.cs.z1761257.gathr;

/**
 * Created by Pravin on 5/7/16.
 * Project: Gathr
 */


import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class EventRegistration extends Fragment {


    ParseObject eventObject = new ParseObject("Events");

    private EditText titleET,
                     hostNameET,
                     startDetailsET,
                     endDetailsET,
                     locationET;

    private Button saveBtn, clearBtn;

    private static View vi;

    private Boolean locState = false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (vi != null) {
            ViewGroup parent = (ViewGroup) vi.getParent();
            if (parent != null)
                parent.removeView(vi);
        }
        try {
            vi = inflater.inflate(R.layout.activity_event_registration, container, false);

        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        ParseApplication p = (ParseApplication)getActivity().getApplication();


        // Execute RemoteDataTask AsyncTask

        titleET = (EditText) vi.findViewById(R.id.titleEditText);
        hostNameET = (EditText) vi.findViewById(R.id.hostNameEditText);
        startDetailsET = (EditText) vi.findViewById(R.id.startDetailsEditText);
        endDetailsET = (EditText) vi.findViewById(R.id.endDetailsEditText);
        locationET = (EditText) vi.findViewById(R.id.locationEditText);
        saveBtn = (Button) vi.findViewById(R.id.saveButton);
        clearBtn = (Button) vi.findViewById(R.id.clearButton);

        //intent button to send single event details

        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(titleET.getText().toString().matches("")||hostNameET.getText().toString().matches("")||startDetailsET.getText().toString().matches("")||endDetailsET.getText().toString().matches("")||locationET.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Fields can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    eventObject.put("title", titleET.getText().toString());
                    eventObject.put("hostname", hostNameET.getText().toString());
                    eventObject.put("startDate", startDetailsET.getText().toString());

                    eventObject.put("endDate", endDetailsET.getText().toString());

                    getLatLongFromPlace(locationET.getText().toString().toLowerCase());

                    if(locState == false){

                    }else {
                        eventObject.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            }
        });

        //clear button
        clearBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                titleET.setText("");
                hostNameET.setText("");
                startDetailsET.setText("");
                endDetailsET.setText("");
                locationET.setText("");
                locState = false;
            }
        });

        return vi;

    }//end of onCreate


    //get latitude and longitude from address
    //The following code snippet is taken from stackoverflow -> http://stackoverflow.com/a/22909966/2078880

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
                if(locationET.getText().toString().matches("")){


                }
                else {
                    eventObject.put("Location", point);
                    locState = true;
                }

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

                if(locationET.getText().toString().matches("")){


                }
                else {
                    eventObject.put("Location", point1);
                    locState = true;
                }
//                LatLng point = new LatLng(lat, lng);
//                ParseGeoPoint point = new ParseGeoPoint(30.0, -20.0);
//                ParseObject object = new ParseObject("PlaceObject");
//                object.put("location", point);
//                object.save();



            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }//end of onPost
    }//end of fetchLatLongFromService


}//end of EventRegistration


