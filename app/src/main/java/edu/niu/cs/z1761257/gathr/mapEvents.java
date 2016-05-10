package edu.niu.cs.z1761257.gathr;
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
                updateDetail();
            }
        });

        // Inflate the layout for this fragment
        return vi;
    }

    //intent function
    public void updateDetail()
    {
        Intent intent = new Intent(getActivity(), ShowMaps.class);
        startActivity(intent);
    }


}
