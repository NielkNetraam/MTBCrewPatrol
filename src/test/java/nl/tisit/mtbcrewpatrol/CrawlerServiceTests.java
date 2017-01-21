package nl.tisit.mtbcrewpatrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.CrawlStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class CrawlerServiceTests {
	  @Autowired
	  protected CrawlerService crawlerService;
	  
	  @Test
	  public void shouldCrawl() {
		  assertThat(this.crawlerService).isNotNull();
		  
		  CrawlStatus crawlStatus = this.crawlerService.crawl("1");

		  assertThat(crawlStatus).isEqualTo(CrawlStatus.Finished);
	  }

	
}