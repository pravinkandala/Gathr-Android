package edu.niu.cs.z1761257.gathr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.ParseException;

import java.util.List;

public class EventRegistration extends Activity {

    public static final String YOUR_APPLICATION_ID = "i18cYcUxV5dF0aoT0Ohg9g1pMcccVed6DBIgGneG";
    public static final String YOUR_CLIENT_KEY = "9mwRnblLdjQC7cBCAVPj7GYKdZ8vCrvf3DfyERRl";

    private EditText titleET,
                     hostNameET,
                     startDetailsET,
                     endDetailsET,
                     locationET;

    private Button saveBtn, cancelBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

        //initialize
        titleET = (EditText)findViewById(R.id.titleEditText);
        hostNameET = (EditText)findViewById(R.id.hostNameEditText);
        startDetailsET = (EditText)findViewById(R.id.startDetailsEditText);
        endDetailsET = (EditText)findViewById(R.id.endDetailsEditText);
        locationET = (EditText)findViewById(R.id.locationEditText);

    }//end of onCreate

    //save event button
    public void saveEvent(View view){
     //   Toast.makeText(EventRegistration.this,"success",Toast.LENGTH_SHORT).show();

        ParseObject eventObject = new ParseObject("Events");
        eventObject.put("title",titleET.getText().toString());
        eventObject.put("hostname",hostNameET.getText().toString());
        eventObject.put("startDate",startDetailsET.getText().toString());

        eventObject.put("endDate",endDetailsET.getText().toString());

     //   eventObject.put("Location", locationET.getText());


        ParseGeoPoint point = new ParseGeoPoint(41.949408, -88.769288);
        eventObject.put("Location",point);

        eventObject.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(EventRegistration.this,"success",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EventRegistration.this,"fail",Toast.LENGTH_SHORT).show();
                }
            }
        });
//


    }//end of saveEvent

    public void cancelEvent(View view){


    }//cancelEvent
}//end of EventRegistration
