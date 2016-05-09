package edu.niu.cs.z1761257.gathr;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.parse.Parse;


public class MainActivity extends AppCompatActivity {
//
//    public static final String YOUR_APPLICATION_ID = "i18cYcUxV5dF0aoT0Ohg9g1pMcccVed6DBIgGneG";
//    public static final String YOUR_CLIENT_KEY = "9mwRnblLdjQC7cBCAVPj7GYKdZ8vCrvf3DfyERRl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Parse.initialize(this, getString(R.string.YOUR_APPLICATION_ID), getString(R.string.YOUR_CLIENT_KEY));
//
//        Parse.enableLocalDatastore(this);

//        Parse.enableLocalDatastore(this);
//        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Register"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Maps"));
     
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);

        MyAdapter adapter = new MyAdapter(  getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }//end of onCreate
}
