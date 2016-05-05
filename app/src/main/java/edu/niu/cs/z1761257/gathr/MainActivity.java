package edu.niu.cs.z1761257.gathr;

import android.app.Activity;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends Activity {

    public static final String YOUR_APPLICATION_ID = "i18cYcUxV5dF0aoT0Ohg9g1pMcccVed6DBIgGneG";
    public static final String YOUR_CLIENT_KEY = "9mwRnblLdjQC7cBCAVPj7GYKdZ8vCrvf3DfyERRl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

        // Test creation of object
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
    }
}
