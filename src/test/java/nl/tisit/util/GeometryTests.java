package nl.tisit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.strava.model.Activity;
import nl.tisit.strava.repository.ActivityRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class GeometryTests {
//	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);
	  @Autowired
	  protected ActivityRepository activityRepository;
	
	@Test
	public void distanceTest() {
		LatLng latLng1 = new LatLng(52.01568,5.47432);
		LatLng latLng2 = new LatLng(52.01505, 5.48365);
		
		assertThat(Geometry.distance(latLng1, latLng2)).isEqualTo(642.3293445617298);
		
		latLng2 = new LatLng(52.01568,5.47532);
		assertThat(Geometry.distance(latLng1, latLng2)).isEqualTo(68.43445049977927);
		
		latLng2 = new LatLng(52.01468,5.47432);
		assertThat(Geometry.distance(latLng1, latLng2)).isEqualTo(111.19492664508967);
		
		latLng2 = new LatLng(52.013877944087085,5.4814910888671875);
		assertThat(Geometry.distance(latLng1, latLng2)).isEqualTo(530.0912485328516);
		
		latLng1 = new LatLng(52.01540987952643,5.474023818969727);
		latLng2 = new LatLng(52.01410905839673,5.480525493621826);
		assertThat(Geometry.distance(latLng1, latLng2)).isEqualTo(530.0912485328516);
	}
	
	@Test
	public void maxDistanceTest() {
		Activity activity = activityRepository.findOne(737279002);
		
		List<LatLng> activityLatLngs = PolylineEncoding.decode(activity.getMap().getSummaryPolyline());
		assertThat(Geometry.maxDistance(activityLatLngs)).isEqualTo(6948.704626940388);
		
	}
}
