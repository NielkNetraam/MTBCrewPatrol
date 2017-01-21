package nl.tisit.keyregister.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.keyregister.model.Holidays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
//@Sql({"/test-data.sql"})
public class HolidaysRepositoryTests {
	  @Autowired
	  protected HolidaysRepository repository;
	  
	  @Test
	  @Transactional
	  public void shouldfindAll() {
		  assertThat(this.repository).isNotNull();
		  List<Holidays> holidaysList = (List<Holidays>)repository.findAll();
		  assertThat(holidaysList.size()).isEqualTo(23);
	  }
}
