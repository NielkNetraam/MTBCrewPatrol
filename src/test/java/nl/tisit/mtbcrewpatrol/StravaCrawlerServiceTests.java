package nl.tisit.mtbcrewpatrol;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.geom.Path2D;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.CallCounter;
import nl.tisit.mtbcrewpatrol.model.CrawlStatus;
import nl.tisit.util.Polyline;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class StravaCrawlerServiceTests {
	  @Autowired
	  protected StravaCrawlerService stravaCrawlerService;
	  
	  @Test
	  public void shouldCollectAthletesFromSegment() {
		  assertThat(this.stravaCrawlerService).isNotNull();
		  Integer counter = this.stravaCrawlerService.collectAthletesFromSegment(12278575);

		  assertThat(counter).isEqualTo(47);

	  }

	  @Test
	  public void shouldCollectEffortsFromSegment() {
		  CallCounter pages = new CallCounter();
		  assertThat(this.stravaCrawlerService).isNotNull();
		  CrawlStatus counter = this.stravaCrawlerService.collectEffortsFromSegment(11397108, pages);

		  assertThat(counter).isEqualTo(CrawlStatus.Finished);

	  }
	   
	  @Test
	  public void shouldCollectEffortsFromActivity() {
		  assertThat(this.stravaCrawlerService).isNotNull();
		  Integer counter = this.stravaCrawlerService.collectEffortsFromActivity(603893258);

		  assertThat(counter).isEqualTo(47);

	  }
	  
	  @Test
	  public void shouldCollectMyActivities() {
		  assertThat(this.stravaCrawlerService).isNotNull();
		  CrawlStatus counter = this.stravaCrawlerService.collectMyActivities();

		  assertThat(counter).isEqualTo(CrawlStatus.Finished);
	  }
	  
	  @Test
	  public void shouldCollectDetailedActivities() {
		  assertThat(this.stravaCrawlerService).isNotNull();
		  CrawlStatus counter = this.stravaCrawlerService.collectDetailedActivities();

		  assertThat(counter).isEqualTo(CrawlStatus.Finished);
	  }
	  
	  @Test
	  public void shouldCollectSummaryActivities() {
		  assertThat(this.stravaCrawlerService).isNotNull();
		  CrawlStatus counter = this.stravaCrawlerService.collectSummaryActivities();

		  assertThat(counter).isEqualTo(CrawlStatus.Finished);

	  }
	  
	  @Test
	  public void shouldCollectSummaryAthletes() {
		  assertThat(this.stravaCrawlerService).isNotNull();
		  CrawlStatus counter = this.stravaCrawlerService.collectSummaryAthletes();

		  assertThat(counter).isEqualTo(CrawlStatus.Finished);

	  }

	  @Test
	  public void shouldCollectSegments() {
		  assertThat(this.stravaCrawlerService).isNotNull();
		  
		  Path2D.Double path = Polyline.polyline2path("cfn|Hmd_`@~xJqh^wsEucAavJ~tPw^~xLcrAvjJknB}S}c@n~GkeCd_@vk@p|HzwGdPvvCaaJhe@mxItzAojF");

		  CrawlStatus counter = this.stravaCrawlerService.collectSegments(path);

		  assertThat(counter).isEqualTo(CrawlStatus.Finished);

	  }
}