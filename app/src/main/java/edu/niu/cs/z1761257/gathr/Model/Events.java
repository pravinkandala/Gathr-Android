package edu.niu.cs.z1761257.gathr.Model;

import com.parse.ParseGeoPoint;

/**
 * Created by Pravin on 5/4/16.
 * Project: Gathr
 */


public class Events {


        private String title, hostname, startDate, endDate;

        private ParseGeoPoint geoPoint;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String getStartDate() {
        return startDate;
    }

        public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

        public String getEndDate() {
        return endDate;
    }

        public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

        public ParseGeoPoint getGeoPoint() {
        return geoPoint;
    }

        public void setGeoPoint(ParseGeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }


}
