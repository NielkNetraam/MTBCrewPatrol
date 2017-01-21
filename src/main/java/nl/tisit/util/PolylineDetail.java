package nl.tisit.util;

import java.awt.geom.Path2D;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

public class PolylineDetail {
	public double minLat = 360;	
	public double maxLat = -360;	
	public double minLng = 360;	
	public double maxLng = -360;	
	public List<LatLng> trackLatLngs;
	public Path2D.Double path;
	private String polyline;
	public int distance;
	public LocalDate startDate;
	public LocalDate endDate;
	
//	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);
	
	public PolylineDetail(String polyline, int distance, boolean setPath, LocalDate startDate, LocalDate endDate) {
		super();
		this.polyline = polyline;
		this.distance = distance;
		this.startDate = startDate;
		this.endDate = endDate;
		
		if (distance > 0)
			trackLatLngs = Polyline.polyline2latLngsInterpolate(polyline, distance);
		else 
			trackLatLngs = PolylineEncoding.decode(polyline);

		for (LatLng latLng : trackLatLngs) {
			if (latLng.lat < minLat) minLat = latLng.lat;
			if (latLng.lng < minLng) minLng = latLng.lng;
			if (latLng.lat > maxLat) maxLat = latLng.lat;
			if (latLng.lng > maxLng) maxLng = latLng.lng;
		}
		
		minLat -= 0.001;
		minLng -= 0.001;
		maxLat += 0.001;
		maxLng += 0.001;
		
		if (setPath)
			path = Polyline.polyline2path(polyline);
		
//		log.debug(minLat + " - " + maxLat + " : " +minLng + " - " + maxLng + " : " +  this.getPolyline());
	}
	
	public static boolean onLineList(LatLng latLng, List<PolylineDetail> polylineDetailList, int maxDistance, LocalDateTime referenceDate) {
		for (PolylineDetail polylineDetail : polylineDetailList) {
			if (polylineDetail.onLine(latLng, maxDistance, referenceDate))
				return true;
		}

		return false;
	}
	
	public static boolean inPathList(LatLng latLng, List<PolylineDetail> polylineDetailList, LocalDateTime referenceDate) {
		for (PolylineDetail polylineDetail : polylineDetailList) {
			if (polylineDetail.inPath(latLng, referenceDate))
				return true;
		}

		return false;
	}
	
	public String getPolyline() {
		return polyline;
	}

	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}

	public boolean onLine(LatLng latLng, int maxDistance, LocalDateTime referenceDate) {
//		if (endDate != null) 
//			log.debug("referenceDate" + referenceDate.toString() + "s: " 
//		+ (startDate == null || !referenceDate.before(startDate)) + ", e: " 
//					+ (endDate == null || !referenceDate.after(endDate)));
			
		if (latLng.lat >= this.minLat 
				&& latLng.lat <= this.maxLat 
				&& latLng.lng >= this.minLng 
				&& latLng.lng <= this.maxLng
				&& (startDate == null || !referenceDate.toLocalDate().isBefore(startDate))
				&& (endDate == null || !referenceDate.toLocalDate().isAfter(endDate))) {
//			log.debug(latLng.lat + " - " + latLng.lng + " : " +  this.getPolyline());//
			for (LatLng locLatLng : this.trackLatLngs) {
//				if (this.getPolyline().charAt(0)=='k') {
//					log.debug(locLatLng.lat + " - " + locLatLng.lng + " : " + Geometry.distance(latLng, locLatLng));
//				}
				if (Geometry.distance(latLng, locLatLng) < maxDistance) return true;
			}		
		}
		
		return false;
	}

	public boolean inPath(LatLng latLng, LocalDateTime referenceDate) {
		if (latLng.lat >= this.minLat 
				&& latLng.lat <= this.maxLat 
				&& latLng.lng >= this.minLng 
				&& latLng.lng <= this.maxLng
				&& (startDate == null || !referenceDate.toLocalDate().isBefore(startDate))
				&& (endDate == null || !referenceDate.toLocalDate().isAfter(endDate))) 
			return path.contains(latLng.lng, latLng.lat);
		
		return false;
	}
}
