package nl.tisit.mtbcrewpatrol.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.Road;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
//@Sql({"/test-data.sql"})
public class RoadRepositoryTests {
	  @Autowired
	  protected RoadRepository repository;
	  
	  @Test
	  public void shouldfindOne() {
		  assertThat(this.repository).isNotNull();
		  
		  Road road = repository.findOne(1);
		  assertThat(road.getName()).isEqualTo("N226 Leersum");
	  }
	  
	
	  
}
