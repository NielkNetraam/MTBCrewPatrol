package nl.tisit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nl.tisit.mtbcrewpatrol.CrawlerService;
import nl.tisit.mtbcrewpatrol.StravaCrawlerServiceImpl;

@SpringBootApplication
public class MtbCrewPatrolApplication {
	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);

	public static void main(String[] args) {
		SpringApplication.run(MtbCrewPatrolApplication.class, args);

	}
	
	@Bean
	public CommandLineRunner demo(CrawlerService crawlerService) {

		return (args) -> {
			log.info(args.length);

			if (args.length > 0) {
				crawlerService.crawl(args[0]);
			}

		};
	}
}
