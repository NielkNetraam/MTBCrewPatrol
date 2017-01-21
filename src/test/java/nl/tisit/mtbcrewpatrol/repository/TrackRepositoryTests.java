package nl.tisit.mtbcrewpatrol.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.mtbcrewpatrol.model.TrackType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
//@Sql({"/test-data.sql"})
public class TrackRepositoryTests {
	  @Autowired
	  protected TrackRepository trackRepository;
	  
	  @Test
	  @Transactional
	  public void shouldfindById() {
		  assertThat(this.trackRepository).isNotNull();
		  Track track = trackRepository.findOne(1);
		  assertThat(track.getName()).isEqualTo("Amerongen");
		  assertThat(track.getTrackType()).isEqualTo(TrackType.PERMANENT);
		  assertThat(track.getColor()).isEqualTo("#579D00");
	  }
}
