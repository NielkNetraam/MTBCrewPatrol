package nl.tisit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class FeatureTests {
//	private Log log = LogFactory.getLog(FeatureTests.class);
	
	@Test
	public void summerTimeTest() {
		assertThat(Feature.startSummerTime(2015)).isEqualTo(LocalDate.of(2015, 3, 29));
		assertThat(Feature.startSummerTime(2016)).isEqualTo(LocalDate.of(2016, 3, 27));
		assertThat(Feature.startSummerTime(2017)).isEqualTo(LocalDate.of(2017, 3, 26));
		assertThat(Feature.startSummerTime(2018)).isEqualTo(LocalDate.of(2018, 3, 25));
		assertThat(Feature.startSummerTime(2019)).isEqualTo(LocalDate.of(2019, 3, 31));

		assertThat(Feature.endSummerTime(2015)).isEqualTo(LocalDate.of(2015, 10, 25));
		assertThat(Feature.endSummerTime(2016)).isEqualTo(LocalDate.of(2016, 10, 30));
		assertThat(Feature.endSummerTime(2017)).isEqualTo(LocalDate.of(2017, 10, 29));
		assertThat(Feature.endSummerTime(2018)).isEqualTo(LocalDate.of(2018, 10, 28));
		assertThat(Feature.endSummerTime(2019)).isEqualTo(LocalDate.of(2019, 10, 27));

		assertThat(Feature.isSummerTime(LocalDate.of(2016, 1, 30))).isEqualTo(false);
		assertThat(Feature.isSummerTime(LocalDate.of(2016, 3, 26))).isEqualTo(false);
		assertThat(Feature.isSummerTime(LocalDate.of(2016, 3, 27))).isEqualTo(true);
		assertThat(Feature.isSummerTime(LocalDate.of(2016, 3, 28))).isEqualTo(true);
		assertThat(Feature.isSummerTime(LocalDate.of(2016, 8, 30))).isEqualTo(true);
		assertThat(Feature.isSummerTime(LocalDate.of(2016, 10, 29))).isEqualTo(true);
		assertThat(Feature.isSummerTime(LocalDate.of(2016, 10, 30))).isEqualTo(false);
		assertThat(Feature.isSummerTime(LocalDate.of(2016, 10, 31))).isEqualTo(false);
		assertThat(Feature.isSummerTime(LocalDate.of(2016, 12, 31))).isEqualTo(false);
	}
}
