package nl.tisit.mtbcrewpatrol.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.WildRide;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
//@Sql({"/test-data.sql"})
public class WildRideRepositoryTests {
	  @Autowired
	  protected WildRideRepository repository;
	  
	  @Test
	  public void shouldfindOne() {
		  assertThat(this.repository).isNotNull();
		  
		  WildRide wildRide = repository.findOne(28897392);
		  assertThat(wildRide.getActivityId()).isEqualTo(697896352);
	  }
	  
	
	  
}
