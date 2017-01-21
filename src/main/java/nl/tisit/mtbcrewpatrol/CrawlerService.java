package nl.tisit.mtbcrewpatrol;

import nl.tisit.mtbcrewpatrol.model.CrawlStatus;

public interface CrawlerService {
	public CrawlStatus crawl(String option);
}
