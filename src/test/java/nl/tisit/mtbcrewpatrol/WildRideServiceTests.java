package nl.tisit.mtbcrewpatrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.repository.RestrictedAreaRepository;
import nl.tisit.strava.repository.ActivityRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class WildRideServiceTests {
	@Autowired
	protected WildRideService wildRideService;
	@Autowired
	protected WildRideActivityService wildRideActivityService;
	@Autowired
	protected RestrictedAreaRepository restrictedAreaRepository;
	@Autowired
	protected ActivityRepository activityRepository;

	// @Test
	// public void shouldCheckWildRides() {
	// assertThat(this.wildRideService).isNotNull();
	//
	// this.wildRideService.checkWildRides();
	//
	//// assertThat(crawlStatus).isEqualTo(CrawlStatus.Finished);
	// }

	@Test
	public void shouldClassifyActivities() {
		assertThat(this.wildRideService).isNotNull();

		this.wildRideService.classifyActivities();
	}

	@Test
	public void shouldAggregateActivity() {
		assertThat(this.wildRideService).isNotNull();

		this.wildRideService.aggregateActivity();

	}

	// @Test
	// public void shouldCheckActivity() {
	// assertThat(this.wildRideService).isNotNull();
	// Activity activity = new Activity();
	// Map map = new Map();
	// Athlete athlete = new Athlete();
	// athlete.setId(1);
	// map.setSummaryPolyline("y`p|Havm`@iFbWrDxCh@~ExQhKsAbOrAlq@");
	// activity.setMap(map);
	// activity.setTrainer(false);
	// activity.setActivityType(ActivityType.RIDE.getName());
	// activity.setStartDateLocal(ZonedDateTime.now());
	// activity.setAthlete(athlete);
	// RestrictedArea restrictedArea = restrictedAreaRepository.findOne(2);
	//
	// WildRide wildRide = this.wildRideActivityService.checkActivity(activity,
	// restrictedArea);
	// assertThat(wildRide.getWild()).isEqualTo(true);
	//
	// activity = activityRepository.findOne(466307157);
	// }
	//
	// @Test
	// public void shouldCheckActivity2() {
	// assertThat(this.wildRideService).isNotNull();
	// Activity activity = activityRepository.findOne(466307157);
	// RestrictedArea restrictedArea = restrictedAreaRepository.findOne(1);
	//
	// WildRide wildRide = this.wildRideActivityService.checkActivity(activity,
	// restrictedArea);
	// assertThat(wildRide.getWild()).isEqualTo(true);
	// }
	//
	// @Test
	// @Transactional
	// public void shouldCheckActivity3() {
	// assertThat(this.wildRideService).isNotNull();
	// assertThat(this.activityRepository).isNotNull();
	//
	// Activity activity = activityRepository.findOne(537385247);
	// RestrictedArea restrictedArea = restrictedAreaRepository.findOne(13);
	//
	// WildRide wildRide = this.wildRideActivityService.checkActivity(activity,
	// restrictedArea);
	// assertThat(wildRide.getWild()).isEqualTo(true);
	// assertThat(wildRide.getSegments().size()).isEqualTo(2);
	// assertThat(wildRide.getSegments().get(0).getPolyline()).isEqualTo("");
	//
	//
	// }
}