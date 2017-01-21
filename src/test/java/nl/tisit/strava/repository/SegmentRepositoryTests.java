package nl.tisit.strava.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.strava.model.Segment;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
@Sql({"/test-data.sql"})
@Transactional
public class SegmentRepositoryTests {
	  @Autowired
	  protected SegmentRepository segmentRepository;
	  
	  @Test
	  public void shouldfindOne() {
		  assertThat(this.segmentRepository).isNotNull();
		  Segment segment = this.segmentRepository.findOne(10347600);

		  assertThat(segment.getId()).isEqualTo(10347600);
	  }
	  
	  @Test
	  public void shouldSave() {
		  assertThat(this.segmentRepository).isNotNull();
		  
		  Segment segment = new Segment();
		  segment.setId(1);
		  
		  segment =this.segmentRepository.save(segment);

		  assertThat(segment.getId()).isEqualTo(1);
	  }

	  @Test
	  public void shouldUpdate() {
		  assertThat(this.segmentRepository).isNotNull();
		  Segment segment = this.segmentRepository.findOne(10347600);
		  
		  segment.setName("start marmottenbak onderaan de bergweg");
		  segment = this.segmentRepository.save(segment);

		  Segment segment2 = this.segmentRepository.findOne(10347600);
		  assertThat(segment2.getName()).isEqualTo("start marmottenbak onderaan de bergweg");
	  }
	  

}
