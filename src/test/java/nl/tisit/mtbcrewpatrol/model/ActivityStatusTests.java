package nl.tisit.mtbcrewpatrol.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.tisit.MtbCrewPatrolApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class ActivityStatusTests {
	private Log log = LogFactory.getLog(ActivityStatusTests.class);
	private final static ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void shouldConstructActivityStatus()  {
		ActivityStatus activityStatus = new ActivityStatus();
		
		assertThat(activityStatus.getInfo()).isNull();
		Sector sector = new Sector();
		sector.setId(10);
		ActivityInfo activityInfo = new ActivityInfo();
		activityInfo.addSector(sector, LocalDateTime.now().withNano(0));
		activityStatus.setInfo(activityInfo);
		
		try {
			objectMapper.findAndRegisterModules();
			String string = objectMapper.writeValueAsString(activityInfo);
			log.debug("string:"+string);
		} catch (JsonProcessingException ex) {
			// or throw an error
		}
		log.debug("info:"+activityStatus.getInfoRaw());
	}

}
