package com.daviddemartini.stratux.miltracker.geolocation;

import org.codehaus.jackson.map.ObjectMapper;
import org.apache.commons.math3.util.Precision;

import java.io.IOException;

/**
 * Determine the distance and bearing from local GPS location
 * to contract GPS location using *TBD* methodology
 */
public class DistanceBearing {

    private static final int earthRadiusMi = 3963;
    private final double fromLatitude;
    private final double fromLongitude;
    private final double fromLatitudeRadian;
    private final double fromLongitudeRadian;
    private float distanceFromPole;

    // exposed data structure
    public float bearing;
    public float distance;
    public boolean valid;

    /**
     * Constructor
     *
     * The starting (fixed) position from which distance and bearing will
     * be calcuated (req.
     *
     * @param latitude -- double latitude in degrees (e.g. 29.89401)
     * @param longitude -- double longitude in degrees (e.g. -98.20301)
     */
    public DistanceBearing(double latitude, double longitude){
        // set these values to calculate position from this location
        this.fromLatitude = latitude;
        this.fromLongitude = longitude;
        this.fromLatitudeRadian = Math.toRadians(latitude);
        this.fromLongitudeRadian = Math.toRadians(longitude);
        // calculate distance from pole, necessary to complete the bearing calculation
        this.distanceFromPole = computeDistance(90,0);
    }

    /**
     * Calculate the distance between the preset location and another
     * location defined by  latitude & longitude expressed in degrees
     *
     * @param latitude
     * @param longitude
     */
    public void calculate(double latitude, double longitude){
        // compute distance to the location
        distance = computeDistance(latitude,longitude);
        // report as valid as long as distance is greater than 0
        this.valid = (distance > 0);
    }

    /**
     * Compute distance between 2 points using the Great-Circle calculation method
     *
     * @param latitude
     * @param longitude
     * @return
     */
    private float computeDistance(double latitude, double longitude) {

        // compute the distance to contact from current position
        double cosDistance = (Math.sin(this.fromLatitudeRadian) * Math.sin(Math.toRadians(latitude)))
                + (Math.cos(this.fromLatitudeRadian) * Math.cos(Math.toRadians(latitude))
                    * Math.cos(Math.toRadians(longitude) - this.fromLongitudeRadian));

        // Calculate the distance between points (compliment poleLongitudeAngle)
        return (float) Precision.round(((earthRadiusMi * Math.acos(cosDistance)) + 0.5),2);
    }

    /**
     * Get the bearing value
     *
     * @return
     */
    public float getBearing() {
        return bearing;
    }

    /**
     * Get the distance from North Pole in miles
     *
     * @return
     */
    public float getDistanceFromPole() {
        return distanceFromPole;
    }

    /**
     * Get the distance value
     *
     * @return
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Get valid / invalid flag
     * @return
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Express self as a JSON string.
     *
     * @return
     */
    public String toJSON() throws IOException {
        // convert this data object to JSON
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
