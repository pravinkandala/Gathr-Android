package edu.niu.cs.z1761257.gathr;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import edu.niu.cs.z1761257.gathr.Adapter.EventAdapter;
import edu.niu.cs.z1761257.gathr.Model.Events;


public class DisplayEvents extends Fragment {



    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    EventAdapter adapter;
    private List<Events> eventList = null;

    private View v;
//    Context thiscontext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.activity_display_events, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        ParseApplication p = (ParseApplication)getActivity().getApplication();
//        thiscontext = container.getContext();

        // Inflate the layout for this fragment
        new RemoteDataTask().execute();

        return v;
//        Parse.enableLocalDatastore(thiscontext);
//        Parse.initialize(thiscontext, getString(R.string.YOUR_APPLICATION_ID), getString(R.string.YOUR_CLIENT_KEY));

        // Execute RemoteDataTask AsyncTask


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Events");
            // Set progressdialog message
            mProgressDialog.setMessage("Updating...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            callfunction();
            return null;
        }

        public void callfunction(){
            // Create the array
            eventList = new ArrayList<Events>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Events");

                query.orderByAscending("startDate");
                ob = query.find();
                for (ParseObject country : ob) {
                    Events obj = new Events();
                    obj.setTitle((String) country.get("title"));
                    obj.setHostname((String) country.get("hostname"));
                    obj.setStartDate((String) country.get("startDate"));
                    obj.setEndDate((String) country.get("endDate"));

                    obj.setGeoPoint((ParseGeoPoint) country.get("Location"));

                    eventList.add(obj);
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) getView().findViewById(R.id.listview12);
            // Pass the results into ListViewAdapter.java
            adapter = new EventAdapter(getActivity(),
                    eventList);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
