package nl.tisit.strava.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.CallCounterException;
import nl.tisit.strava.model.Activity;
import nl.tisit.strava.model.Athlete;
import nl.tisit.strava.model.AthleteType;
import nl.tisit.strava.model.DateRange;
import nl.tisit.strava.model.Gender;
import nl.tisit.strava.model.LatLng;
import nl.tisit.strava.model.LeaderBoard;
import nl.tisit.strava.model.Measurement;
import nl.tisit.strava.model.Segment;
import nl.tisit.strava.model.SegmentEffort;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class StravaServiceTests {
	@Autowired
	protected StravaService stravaService;

	@Test
	public void shouldfindAthlete() throws CallCounterException {
		assertThat(this.stravaService).isNotNull();
		Athlete athlete = this.stravaService.findAthlete(6126748);

		assertThat(athlete.getId()).isEqualTo(6126748);
		assertThat(athlete.getResourceState()).isEqualTo(3);
		assertThat(athlete.getFirstName()).isEqualTo("Maarten");
		assertThat(athlete.getLastName()).isEqualTo("Klein");
		assertThat(athlete.getProfileMedium())
				.isEqualTo("https://dgalywyr863hv.cloudfront.net/pictures/athletes/6126748/2150959/2/medium.jpg");
		assertThat(athlete.getProfile())
				.isEqualTo("https://dgalywyr863hv.cloudfront.net/pictures/athletes/6126748/2150959/2/large.jpg");
		assertThat(athlete.getCity()).isEqualTo("Amerongen");
		assertThat(athlete.getState()).isEqualTo("UT");
		assertThat(athlete.getCountry()).isEqualTo("The Netherlands");
		assertThat(athlete.getGender()).isEqualTo(Gender.MALE);
		assertThat(athlete.getPremium()).isEqualTo(false);
		assertThat(athlete.getFriend()).isNull();
		assertThat(athlete.getFollower()).isNull();
		assertThat(athlete.getCreatedAt().toString()).isEqualTo("2014-08-20T18:55:54Z[GMT]");
		assertThat(athlete.getUpdatedAt().toString()).isEqualTo("2016-06-04T13:52:56Z[GMT]");
		assertThat(athlete.getFollowerCount()).isEqualTo(8);
		assertThat(athlete.getFriendCount()).isEqualTo(10);
		assertThat(athlete.getMutualFriendCount()).isEqualTo(0);
		assertThat(athlete.getAthleteType()).isEqualTo(AthleteType.CYCLIST);
		assertThat(athlete.getDatePreference()).isEqualTo("%m/%d/%Y");
		assertThat(athlete.getMeasurementPreference()).isEqualTo(Measurement.METERS);
		assertThat(athlete.getEmail()).isEqualTo("maarten.klein@planet.nl");
		assertThat(athlete.getFtp()).isNull();
		assertThat(athlete.getWeight()).isEqualTo(new Float(80.0));
		assertThat(athlete.getUserName()).isNull();
		assertThat(athlete.getBadgeTypeId()).isEqualTo(0);
		assertThat(athlete.getClubs().size()).isEqualTo(2);
		assertThat(athlete.getBikes().size()).isEqualTo(2);
		assertThat(athlete.getShoes().size()).isEqualTo(0);
	}

	@Test
	public void shouldfindActivity() throws CallCounterException {
		assertThat(this.stravaService).isNotNull();

		Activity activity = this.stravaService.findActivity(760467549);
		 
		activity = this.stravaService.findActivity(603893258);

		assertThat(activity.getId()).isEqualTo(603893258);
		assertThat(activity.getResourceState()).isEqualTo(3);
		assertThat(activity.getName()).isEqualTo("Namiddagrit");
		assertThat(activity.getMap().getId()).isEqualTo("a603893258");
		assertThat(activity.getMap().getSummaryPolyline()).isEqualTo(
				"{oq}Hwd}_@m@rAbO}B`MrRdLmEt_@{r@hBH|@dCtOwAvE|HjHuJrJr@fEzCtDeKxB|@`AcEdDxCt@pFxIiDlGbG~Kk`@tj@{O`LkIzXiKx@dCjCu@zTfMfGmZLuUlCkV`Bcg@~Voc@zAwZ~Fqb@~EcNzDuDrGf@vKhIhnAjHxkBndAfe@sCt^so@jOiH`Jw]zD_HpHaBhPfApF}OvTw_@hDaNvBsS~BaDdIcAfCkCnCeTdJkS~AwNrCqF~GwAfTzEYdTlb@oWbj@yVpQwAbAo~@vo@vFz^sJxEj@hMlMjWzGrJbLzWzPlJlSbJfHtE~J`OdNl\\bQl_@dM|HlMSxC");

		activity = this.stravaService.findActivity(584089137);
		assertThat(activity.getId()).isEqualTo(584089137);

		activity = this.stravaService.findActivity(532816781);
		assertThat(activity.getId()).isEqualTo(532816781);

	}

	@Test
	public void shouldfindActivity2() throws CallCounterException {
		assertThat(this.stravaService).isNotNull();

		try {
			Activity activity = this.stravaService.findActivity(532816781);
			assertThat(activity.getId()).isEqualTo(532816781);
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		}
	}

	@Test
	public void shouldfindSegment() throws CallCounterException {
		assertThat(this.stravaService).isNotNull();
		Segment segment = this.stravaService.findSegment(10347600);

		assertThat(segment.getId()).isEqualTo(10347600);
		assertThat(segment.getName()).isEqualTo("start marmottenbak onderaan de bergweg");
	}

	@Test
	public void shouldFindSegmentLeaderBoard() throws CallCounterException {
		assertThat(this.stravaService).isNotNull();
		LeaderBoard leaderBoard = this.stravaService.findSegmentLeaderBoard(10347600);

		assertThat(leaderBoard.getEntryCount()).isGreaterThanOrEqualTo(4236);
	}

	@Test
	public void shouldFindSegmentLeaderBoardDateRange() throws CallCounterException {
		assertThat(this.stravaService).isNotNull();
		LeaderBoard leaderBoard = this.stravaService.findSegmentLeaderBoard(10347600, DateRange.YEAR);

		assertThat(leaderBoard.getEntryCount()).isGreaterThanOrEqualTo(2900);
	}

	@Test
	public void shouldFindSegmentEfforts() throws CallCounterException {
		assertThat(this.stravaService).isNotNull();
		List<SegmentEffort> segmentEffors = this.stravaService.findSegmentEfforts(10347600, null, null, null);

		assertThat(segmentEffors.size()).isEqualTo(200);
	}

	@Test
	public void shouldFindSegments() throws CallCounterException {
		assertThat(this.stravaService).isNotNull();

		LatLng sw = new LatLng(new Float(52.02704), new Float(5.46081));
		LatLng ne = new LatLng(new Float(52.03522), new Float(5.487590));
		List<Segment> segments = this.stravaService.findSegments(sw, ne);

		assertThat(segments.size()).isEqualTo(1);

		sw = new LatLng(new Float(52.05007), new Float(5.35605));
		ne = new LatLng(new Float(52.06666), new Float(5.39218));
		segments = this.stravaService.findSegments(sw, ne);

		assertThat(segments.size()).isEqualTo(1);

	}

	@Test
	public void shouldFindAthleteActivities() throws CallCounterException {
		assertThat(this.stravaService).isNotNull();

		List<Activity> activities = this.stravaService.findAthleteActivities(null, 10);
		assertThat(activities.size()).isEqualTo(10);

		activities = this.stravaService.findAthleteActivities(LocalDateTime.now(), 10);
		assertThat(activities.size()).isEqualTo(0);
	}

}
