package com.alexsashkin.circle_manager;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

public class MemberModel implements Comparable<MemberModel>, Serializable {

    private UUID id;
    private String name;

    private double latitude;
    private double longitude;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public MemberModel(UUID id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int checkDistance(double latitude, double longitude, double distance) {
        double dist = DistanceUtils.distance(this.latitude, this.longitude, latitude, longitude);
        return Double.compare(dist, distance);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && this.id.compareTo(((MemberModel) o).id) == 0;
    }

    @Override
    public int compareTo(MemberModel o) {
        return id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "UUID: %s, name: %s, latitude: %.6f, longitude: %.6f",
                id.toString(),
                name,
                latitude,
                longitude
        );
    }
}
