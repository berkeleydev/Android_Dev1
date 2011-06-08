package com.timurb.mobsy;

public class GPSdist{
	protected static double distance ( double lat1, double lon1, double lat2, double lon2) {
			int R = 6371; // km
			double dLat = (lat2-lat1)/180*Math.PI;
			double dLon = (lon2-lon1)/180*Math.PI;
			double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			        Math.cos(lat1) * Math.cos(lat2) * 
			        Math.sin(dLon/2) * Math.sin(dLon/2); 
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			double d = R * c;
	return d;
		}
}
