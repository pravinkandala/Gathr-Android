package edu.niu.cs.z1761257.gathr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Pravin on 5/7/16.
 * Project: Gathr
 */
public class MyAdapter extends FragmentPagerAdapter {
    int numofTabs;
    public MyAdapter(FragmentManager fragmentManager, int numTabs){
        super(fragmentManager);
        numofTabs = numTabs;
    }//end of constructor

    @Override
    public Fragment getItem(int position) {
        //return null;
        switch (position){
            case 0:
                EventRegistration eventRegistration = new EventRegistration();
                return eventRegistration;

            case 1:
                DisplayEvents displayEvents = new DisplayEvents();
                return displayEvents;

            case 2:
                mapEvents mapEvents = new mapEvents();
                return mapEvents;
//
//            case 3:
//                DinnerFragment dinnerFragment = new DinnerFragment();
//                return dinnerFragment;

            default: return null;
        }
    }//end of getItem

    @Override
    public int getCount() {
        //return 0;
        return numofTabs;
    }//end of getCount
}//end of MyAdapter
