package edu.niu.cs.z1761257.gathr;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DisplayEvents extends Fragment {



    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    EventAdapter adapter;
    private List<Events> eventList = null;
//    Context thiscontext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        ParseApplication p = (ParseApplication)getActivity().getApplication();
//        thiscontext = container.getContext();

        // Inflate the layout for this fragment
        new RemoteDataTask().execute();

        return inflater.inflate(R.layout.activity_display_events, container, false);
//        Parse.enableLocalDatastore(thiscontext);
//        Parse.initialize(thiscontext, getString(R.string.YOUR_APPLICATION_ID), getString(R.string.YOUR_CLIENT_KEY));

        // Execute RemoteDataTask AsyncTask


    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Get the view from listview_main.xml
//        setContentView(R.layout.activity_display_events);



//
//    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("List");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
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
                    obj.setEndDate((String)country.get("endDate"));
                    eventList.add(obj);
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
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
