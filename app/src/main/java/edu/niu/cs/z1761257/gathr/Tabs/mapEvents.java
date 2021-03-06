package edu.niu.cs.z1761257.gathr.Tabs;
/**
 * Created by Pravin on 5/7/16.
 * Project: Gathr
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.niu.cs.z1761257.gathr.Activity.ShowMaps;
import edu.niu.cs.z1761257.gathr.R;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class mapEvents extends Fragment {

    private View vi;

    public mapEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if ( ContextCompat.checkSelfPermission(vi.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
//        }
        if (vi != null) {
            ViewGroup parent = (ViewGroup) vi.getParent();
            if (parent != null)
                parent.removeView(vi);
        }
        try {
            vi = inflater.inflate(R.layout.fragment_map_events, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }




        //Button for intent map events page
        Button button = (Button) vi.findViewById(R.id.show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ShowMaps.class);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return vi;
    }


}
