package nl.tisit.strava.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.strava.model.Athlete;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
//@Sql({"/test-data.sql"})
public class AthleteRepositoryTests {
	  @Autowired
	  protected AthleteRepository athleteRepository;
	  
	  @Test
	  public void shouldfindOne() {
		  assertThat(this.athleteRepository).isNotNull();
		  Athlete athlete = this.athleteRepository.findOne(6126748);

		  assertThat(athlete.getId()).isEqualTo(6126748);
		  assertThat(athlete.getFirstName()).isEqualTo("Maarten");
		  assertThat(athlete.getLastName()).isEqualTo("Klein");		  
	  }
	  
	  @Test
	  public void shouldSave() {
		  assertThat(this.athleteRepository).isNotNull();
		  
		  Athlete athlete = new Athlete();
		  athlete.setId(1);
		  athlete.setFirstName("YY");
		  athlete.setLastName("XX");
		  
		  athlete =this.athleteRepository.save(athlete);

		  assertThat(athlete.getFirstName()).isEqualTo("YY");
		  assertThat(athlete.getLastName()).isEqualTo("XX");		  
		  assertThat(athlete.getId()).isEqualTo(1);
	  }
}
