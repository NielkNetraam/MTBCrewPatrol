package nl.tisit.util;

import java.util.ArrayList;
import java.util.List;

import com.google.maps.model.LatLng;

public class Geometry {
	/*
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    double distance = distance(lat1, lat2, lon1, lon2); // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
	public static double distance(LatLng latLng1, LatLng latLng2) {
	    return distance(latLng1.lat, latLng2.lat, latLng1.lng, latLng2.lng); // convert to meters
	}

	public static double distance(List<LatLng> latLngs) {
		double totalDistance = 0;
		LatLng previous = null;
		for (LatLng latLng : latLngs) {
			if (previous != null) {
				totalDistance += distance(previous, latLng);
			}
			previous = latLng;
		}
		
	    return totalDistance;
	}

	public static double maxDistance(List<LatLng> latLngs) {
		double maxDistance = 0;
		LatLng previous = null;
		for (LatLng latLng : latLngs) {
			if (previous != null) {
				double distance = distance(previous, latLng);
				if (distance > maxDistance) maxDistance = distance;
			}
			previous = latLng;
		}
		
	    return maxDistance;
	}

	public static double distance(double lat1, double lat2, double lon1, double lon2) {

	    final int R = 6371; // Radius of the earth

	    Double latDistance = Math.toRadians(lat2 - lat1);
	    Double lonDistance = Math.toRadians(lon2 - lon1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    
	    return R * c * 1000; // convert to meters
	}
	
	public static List<LatLng> interpolate(LatLng latLng1, LatLng latLng2, int maxDistance) {
		List<LatLng> result = new ArrayList<LatLng>();
		
		double distance = distance(latLng1, latLng2);
		
		if (distance > maxDistance) {

			int points = ((int)Math.ceil(distance / maxDistance)) - 1;
			double latDistance = (latLng2.lat - latLng1.lat)/(points+1);
			double lngDistance = (latLng2.lng - latLng1.lng)/(points+1);
			
			for (int i=1; i <= points; i++) {
				result.add(new LatLng(latLng1.lat+(i*latDistance), latLng1.lng+(i*lngDistance)));
			}
		}
		return result;
	}
	
//	
//	private static double getAngle(LatLng source, LatLng target) {
//		double angle = (float) Math.toDegrees(Math.atan2(target.lat - source.lat, target.lng - source.lng));
//
//	    if(angle < 0){
//	        angle += 360;
//	    }
//
//	    return angle;
//	}
//	
//	private static LatLng addStroke2LatLng(LatLng latLng, Double stroke, Double angle) {
//		double newStroke = Math.sqrt(2*Math.pow(stroke, 2));
//		return new LatLng(latLng.lat-Math.sin(angle)*newStroke , latLng.lng-Math.cos(angle)*newStroke);
//	}
//	
//	public static Path2D.Double pathWithStroke(String polyline, Double stroke) {
//		List<LatLng> latLngs = PolylineEncoding.decode(polyline);
//
//		Path2D.Double path = new Path2D.Double();
//		
//		Boolean first = true;
//		
//		LatLng previous = null;
//		LatLng newPrevious = null;
//		
//		for (LatLng latLng :latLngs) {
//			if (first) {
//				first = false;
//			}
//			else {
//				double angle = getAngle(previous, latLng);
//				
//				if (newPrevious == null) {			
//					newPrevious = addStroke2LatLng(previous, stroke, angle-135);
//					path.moveTo(newPrevious.lng, newPrevious.lat);
//				}
//				
//				LatLng newCurrent = addStroke2LatLng(latLng, stroke, angle-45);
//				path.lineTo(newCurrent.lng, newCurrent.lat);
//				
////				if (latLng.lng > previous.lng) 
////					if (latLng.lat > previous.lat) {
////						
////						
////					} else {
////						
////					}
////				else
////					if (latLng.lat > previous.lat) {
////						
////					} else {
////						
////					}
//				
////				path.lineTo(latLng.lng, latLng.lat);
//			}
//			
//			previous = latLng;
//		}
//		
//		return path;
//	}
}
