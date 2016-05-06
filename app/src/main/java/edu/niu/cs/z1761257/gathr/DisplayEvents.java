package edu.niu.cs.z1761257.gathr;

import android.app.ProgressDialog;
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

public class DisplayEvents extends AppCompatActivity {

    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    EventAdapter adapter;
    private List<Events> eventList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.activity_display_events);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.YOUR_APPLICATION_ID), getString(R.string.YOUR_CLIENT_KEY));

        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();


    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DisplayEvents.this);
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
            listview = (ListView) findViewById(R.id.listview12);
            // Pass the results into ListViewAdapter.java
            adapter = new EventAdapter(DisplayEvents.this,
                    eventList);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
