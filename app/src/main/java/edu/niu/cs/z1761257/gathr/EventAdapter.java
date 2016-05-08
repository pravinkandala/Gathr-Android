package edu.niu.cs.z1761257.gathr;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.location.Address;

import com.parse.ParseGeoPoint;

public class EventAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Events> eventList = null;
    private ArrayList<Events> arraylist;

    public EventAdapter(Context context,
                           List<Events> eventList) {
        mContext = context;
        this.eventList = eventList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Events>();
        this.arraylist.addAll(eventList);
    }

    public class ViewHolder {
        TextView titleTV,
                 hostnameTV,
                 startTV,
                 endTV;
    }


    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Events getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, final ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.eventlist, null);
            // Locate the TextViews in listview_item.xml
            holder.titleTV = (TextView) view.findViewById(R.id.titleTextView);
            holder.hostnameTV = (TextView) view.findViewById(R.id.hostnameTextView);
            holder.startTV = (TextView) view.findViewById(R.id.startTextView);
            holder.endTV = (TextView) view.findViewById(R.id.endTextView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.titleTV.setText(eventList.get(position).getTitle());
        holder.hostnameTV.setText(eventList.get(position).getHostname());
        holder.startTV.setText(eventList.get(position).getStartDate());
        holder.endTV.setText(eventList.get(position).getEndDate());

//        Geocoder geocoder;
//        List<Address> addresses;
//        geocoder = new Geocoder(mContext, Locale.getDefault());
//
//        addresses = geocoder.getFromLocation() // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
//        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
//        String knownName = addresses.get(0).getFeatureName();

        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, SingleEventView.class);
                // Pass all title data
                intent.putExtra("title",
                        (eventList.get(position).getTitle()));

                // Pass all location data

                ParseGeoPoint parseGeoPoint = eventList.get(position).getGeoPoint();
                Double lat = parseGeoPoint.getLatitude();
                Double lng = parseGeoPoint.getLongitude();

                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);


                // Pass all start data
                intent.putExtra("start",
                        (eventList.get(position).getStartDate()));
                // Pass all host data
                intent.putExtra("hostname",(eventList.get(position).getHostname()));
                //Pass all end data
                intent.putExtra("end",(eventList.get(position).getEndDate()));

                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}