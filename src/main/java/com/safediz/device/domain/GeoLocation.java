package com.safediz.device.domain;

import com.maxmind.geoip.Location;

public class GeoLocation {

	private float latitude;
	private float longitude;

	public GeoLocation(float latitude, float longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static GeoLocation map(Location location) {
		return location == null ? null : new GeoLocation(location.latitude, location.longitude);
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

}
