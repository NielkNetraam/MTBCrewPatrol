package nl.tisit.strava.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.strava.model.Activity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
//@Sql({"/test-data.sql"})
public class ActivityRepositoryTests {
	  @Autowired
	  protected ActivityRepository repository;
	  
	  @Test
	  public void shouldfindOne() {
		  assertThat(this.repository).isNotNull();
		  Activity activity = this.repository.findOne(760467549);

		  assertThat(activity.getId()).isEqualTo(760467549);
		  assertThat(activity.getStartDate()).isEqualTo(ZonedDateTime.parse("2016-10-30T10:29:49Z"));
//		  assertThat(activity.getStartDateLocalApi()).isEqualTo(ZonedDateTime.parse("2016-10-30T11:29:49+01:00"));		  
		  assertThat(activity.getStartDate().toLocalDateTime()).isEqualTo(LocalDateTime.parse("2016-10-30T11:29:49"));
		  assertThat(activity.getStartDateLocal()).isEqualTo(LocalDateTime.parse("2016-10-30T11:29:49"));		  
	  }
}
