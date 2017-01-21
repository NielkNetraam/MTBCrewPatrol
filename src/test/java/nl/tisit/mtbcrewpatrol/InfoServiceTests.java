package nl.tisit.mtbcrewpatrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class InfoServiceTests {
	  @Autowired
	  protected InfoService infoService;
	  
	  @Test
	  public void shouldSegmentOnTrack() {
		  assertThat(this.infoService).isNotNull();
		  
		  this.infoService.segmentOnTrack(50);
	  }

	  @Test
	  public void shouldSegmentNotOnTrack() {
		  assertThat(this.infoService).isNotNull();
		  
		  this.infoService.segmentNotOnTrack(300);
	  }

	  @Test
	  public void shouldSegmentWild() {
		  assertThat(this.infoService).isNotNull();
		  
		  this.infoService.segmentWild();
	  }

}