package nl.tisit.util;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;


public class Polyline {
//	public static List<LatLng> decode(final String encodedPath) {
//
//		int len = encodedPath.length();
//
//		final List<LatLng> path = new ArrayList<LatLng>(len / 2);
//		int index = 0;
//		int lat = 0;
//		int lng = 0;
//
//		while (index < len) {
//			int result = 1;
//			int shift = 0;
//			int b;
//			do {
//				b = encodedPath.charAt(index++) - 63 - 1;
//				result += b << shift;
//				shift += 5;
//			} while (b >= 0x1f);
//			lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);
//
//				result = 1;
//				shift = 0;
//				do {
//					b = encodedPath.charAt(index++) - 63 - 1;
//					result += b << shift;
//					shift += 5;
//				} while (b >= 0x1f);
//				lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);
//
//					path.add(new LatLng(new Float(lat * 1e-5), new Float(lng * 1e-5)));
//		}
//
//		return path;
//	}
	
	public static Path2D.Double polyline2path(String polyline) {
		List<LatLng> latLngs = PolylineEncoding.decode(polyline);

		Path2D.Double path = new Path2D.Double();
		
		Boolean first = true;
		
		for (LatLng latLng :latLngs) {
			if (first) {
				path.moveTo(latLng.lng, latLng.lat);
				first = false;
			}
			else 
				path.lineTo(latLng.lng, latLng.lat);
		}
		
		return path;
	}

	public static Line2D.Double polyline2line(String polyline) {
		List<LatLng> latLngs = PolylineEncoding.decode(polyline);

		Line2D.Double line = null;
		
		LatLng first = null;
		LatLng last = null;
		
		for (LatLng latLng :latLngs) {
			if (first == null) 
				first = latLng;
			last = latLng;
		}
		
		if (first != null && last != null)
			line = new Line2D.Double(first.lng, first.lat, last.lng, last.lat);
		
		return line;
	}

	public static List<LatLng> polyline2latLngsInterpolate(String polyline, int maxDistance) {
		List<LatLng> latLngs = PolylineEncoding.decode(polyline);
		List<LatLng> interpolatelatLngs = new ArrayList<LatLng>();
		Boolean first = true;
		LatLng previous = null;
		
		for (LatLng latLng :latLngs) {
			if (first) 
				first = false;
			else {
				interpolatelatLngs.addAll(Geometry.interpolate(previous, latLng, maxDistance));
			}

			interpolatelatLngs.add(latLng);
			previous = latLng;
		}
		
		return interpolatelatLngs;
	}

}
