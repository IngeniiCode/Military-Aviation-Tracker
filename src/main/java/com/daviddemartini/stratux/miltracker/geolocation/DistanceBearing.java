package com.daviddemartini.stratux.miltracker.geolocation;

import org.codehaus.jackson.map.ObjectMapper;
import org.apache.commons.math3.util.Precision;

import java.io.IOException;

/**
 * Determine the distance and bearing from local GPS location
 * to contract GPS location using *TBD* methodology
 */
public class DistanceBearing {

    private final double earthRadiusMi = 3963.0;
    private final double fromLatitude;
    private final double fromLongitude;

    // exposed data structure
    public float contactAzimuth;
    public float contactDistance;
    public boolean valid;

    /**
     * Constructor
     *
     * The starting (fixed) position from which distance and bearing will
     * be calculated (req.
     *
     * @param positionLatitude -- double latitude in degrees (e.g. 29.89401)
     * @param positionLongitude -- double longitude in degrees (e.g. -98.20301)
     */
    public DistanceBearing(double positionLatitude, double positionLongitude){
        // set these values to calculate position from this location
        this.fromLatitude = positionLatitude;
        this.fromLongitude = positionLongitude;
    }

    /**
     * Calculate the distance between the preset location and another
     * location defined by  latitude & longitude expressed in degrees
     *
     * @param targetLatitude
     * @param targetLongitude
     */
    public void calculate(double targetLatitude, double targetLongitude){
        // compute distance to the location and test for distance > 0
        this.contactDistance = computeDistance(this.fromLatitude, this.fromLongitude, targetLatitude, targetLongitude);
        this.contactAzimuth = computeTargetAzimuth(this.fromLatitude, this.fromLongitude, targetLatitude, targetLongitude);
        this.valid = (this.contactDistance > 0);
    }

    /**
     * Compute distance between 2 points using the Great-Circle calculation method
     *
     * Note: this is a compromise, using flat plane trig instead of a curved triangle
     * computation due to the expected short trange of these calculations of less than 250mi.
     *
     * For any calculations greater than that, this simplified algorithm will become less
     * accurate as distance increases.
     */
    private float computeDistance(double positionLatitude, double positionLongitude, double targetLatitude, double targetLongitude) {

        // compute the distance to contact from current position
        double cosDistance = (Math.sin(Math.toRadians(positionLatitude)) * Math.sin(Math.toRadians(targetLatitude)))
                + (Math.cos(Math.toRadians(positionLatitude)) * Math.cos(Math.toRadians(targetLatitude))
                    * Math.cos(Math.toRadians(targetLongitude) - Math.toRadians(positionLongitude)));

        // Calculate the distance between points (compliment poleLongitudeAngle)
        return (float) Precision.round(((this.earthRadiusMi * Math.acos(cosDistance)) + 0.5),2);
    }


    /**
     * Calculate bearing/angle between two points.
     *
     * @param lat1
     * @param long1
     * @param lat2
     * @param long2
     * @return
     */
    private float computeTargetAzimuth(double lat1, double long1, double lat2, double long2) {

        double computedAzimuth = (
                (Math.toDegrees(
                        Math.atan2(
                            Math.sin(long2 - long1) * Math.cos(lat2),
                                (Math.cos(lat1) * Math.sin(lat2)) - (Math.sin(lat1) * Math.cos(lat2) * Math.cos(long2 - long1))
                        ))
                ) + 360) % 360;

        return (float) Precision.round(computedAzimuth,2);
    }

    /**
     * Get the bearing value
     *
     * @return
     */
    public float getContactAzimuth() {
        return this.contactAzimuth;
    }

    /**
     * Get the distance value
     *
     * @return
     */
    public float getContactDistance() {
        return this.contactDistance;
    }

    /**
     * Get valid / invalid flag
     * @return
     */
    public boolean isValid() {
        return this.valid;
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
