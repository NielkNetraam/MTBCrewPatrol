package nl.tisit.mtbcrewpatrol;

import java.awt.geom.Path2D;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.tisit.mtbcrewpatrol.model.CrawlStatus;
import nl.tisit.util.Polyline;

@Service
public class CrawlerServiceImpl implements CrawlerService {
	@Autowired
	protected StravaCrawlerService stravaCrawlerService;
	@Autowired
	protected CallCounterService callCounterService;
	@Autowired
	protected WildRideService wildRideService;

	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);

	public CrawlStatus crawl(String option) {
		Integer segmentCount = 600;
		
		if (option.equals("1"))
			segmentCount = 80;
		
		Path2D.Double path = Polyline.polyline2path("cfn|Hmd_`@~xJqh^wsEucAavJ~tPw^~xLcrAvjJknB}S}c@n~GkeCd_@vk@p|HzwGdPvvCaaJhe@mxItzAojF");

/*
 * cfn|Hmd_`@bcCqoJiuHmsEem@fyPj_FvI
 * cfn|Hmd_`@~xJqh^wsEucAavJ~tP_d@~_PxuFwg@
 * cfn|Hmd_`@~xJqh^wsEucAavJ~tPw^~xLcrAvjJknB}S}c@n~GkeCd_@vk@p|HzwGdPvvCaaJhe@mxItzAojF
 * 
		path.moveTo(5.46081, 52.0);
		path.lineTo(5.48201, 52.0);
		path.lineTo(5.48201, 52.02704);
		path.lineTo(5.46081, 52.02704);
		path.lineTo(5.46081, 52.0);

		cfn|Hmd_`@bcCqoJiuHmsEem@fyPj_FvI
		
		path.moveTo(5.40759, 52.01522);
		path.lineTo(5.46656, 51.99408);
		path.lineTo(5.50055, 52.04373);
		path.lineTo(5.40931, 52.05112);
		path.lineTo(5.40759, 52.01522);
		
		cfn|Hmd_`@bcCqoJiuHmsEem@fyPj_FvI
		
		cfn|Hmd_`@nkKam_@qpGlEiuHxcPov@huOzqFor@
		
		
 */
		log.info("Start crawl");
		
		CrawlStatus crawlStatus = CrawlStatus.Interrupted;
		CrawlStatus crawlStatusSegment = CrawlStatus.Interrupted;
		CrawlStatus crawlStatusActivity = CrawlStatus.Interrupted;
		CrawlStatus crawlStatusAthlete = CrawlStatus.Interrupted;

		Boolean checkSegment = !(option.equals("WR")||option.equals("FS"));
		Boolean checkActivity = !(option.equals("WR")||option.equals("FS"));
		Boolean checkAthlete = option.equals("2");
		Boolean checkWildRide = !(option.equals("FS"));
		Boolean checkMe = !(option.equals("WR")||option.equals("FS"));
		Boolean findSegment = option.equals("FS");
		
		
		if (checkMe) {
			while (crawlStatus == CrawlStatus.Interrupted) {
				callCounterService.waitIfNecessary();
				
				log.info("collectMyActivities start");
				crawlStatus = stravaCrawlerService.collectMyActivities();
				log.info("collectMyActivities end: " + crawlStatus);
			}
			
			crawlStatus = CrawlStatus.Interrupted;
			while (crawlStatus == CrawlStatus.Interrupted) {
				callCounterService.waitIfNecessary();

				log.info("collectDetailedActivities start");
				crawlStatus = stravaCrawlerService.collectDetailedActivities();
				log.info("collectDetailedActivities end: " + crawlStatus);
			}
		}

		
		int counter = 0;

		crawlStatusSegment = (checkSegment ? CrawlStatus.Interrupted : CrawlStatus.Finished);
		crawlStatusActivity = (checkActivity ? CrawlStatus.Interrupted : CrawlStatus.Finished);
		crawlStatusAthlete = (checkAthlete ? CrawlStatus.Interrupted : CrawlStatus.Finished);
		crawlStatus = CrawlStatus.Interrupted;
		while (counter < 20 && crawlStatus == CrawlStatus.Interrupted) {
			counter++;
			log.info("round " + counter);

			callCounterService.waitIfNecessary();

			if (checkSegment) {
				log.info("collectEffortsFromSegments start");
				crawlStatus = stravaCrawlerService.collectEffortsFromSegments(segmentCount, path);
				log.info("collectEffortsFromSegments end: " + crawlStatus);
				crawlStatusSegment = crawlStatus;
			}

			while (checkActivity && crawlStatusActivity == CrawlStatus.Interrupted) {
				callCounterService.waitIfNecessary();

				log.info("collectSummaryActivities start");
				crawlStatus = stravaCrawlerService.collectSummaryActivities();
				log.info("collectSummaryActivities end: " + crawlStatus);
				crawlStatusActivity = crawlStatus;
				
				if (option.equals("1"))
					break;
			}
			
			if (checkWildRide) {
				log.info("classifyActivities start");
				wildRideService.classifyActivities();
				log.info("classifyActivities end");
			}
			
			while (checkAthlete && crawlStatusAthlete == CrawlStatus.Interrupted) {
				callCounterService.waitIfNecessary();

				log.info("collectSummaryAthletes start");
				crawlStatus = stravaCrawlerService.collectSummaryAthletes();
				log.info("collectSummaryAthletes end: " + crawlStatus);
				crawlStatusAthlete = crawlStatus;
			}
			
			if (crawlStatusSegment == CrawlStatus.Interrupted || crawlStatusActivity == CrawlStatus.Interrupted || crawlStatusAthlete == CrawlStatus.Interrupted)
				crawlStatus = CrawlStatus.Interrupted;
			else 
				crawlStatus = CrawlStatus.Finished;
		}
		
		if (findSegment) {
			crawlStatus = this.stravaCrawlerService.collectSegments(path);
		}
		
		return crawlStatus;
	}
}
