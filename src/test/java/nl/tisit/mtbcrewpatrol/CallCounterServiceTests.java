package nl.tisit.mtbcrewpatrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.CallCounter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class CallCounterServiceTests {
	  @Autowired
	  protected CallCounterService callCounterService;
	  
	  @Test
	  public void shouldFindCounter() {
		  assertThat(this.callCounterService).isNotNull();
		  CallCounter counter = this.callCounterService.findCounter();

		  assertThat(counter.getTotalCount()).isEqualTo(0);
		  assertThat(counter.getDayCount()).isEqualTo(0);
		  assertThat(counter.getQuarterCount()).isEqualTo(0);
	  }

	  @Test
	  public void shouldIncreaseCounter() {
		  assertThat(this.callCounterService).isNotNull();
		  
		  CallCounter counterL = this.callCounterService.findCounter();

		  this.callCounterService.increaseCounter();
		  
		  CallCounter counter = this.callCounterService.findCounter();
		  assertThat(counter.getTotalCount()).isEqualTo(counterL.getTotalCount()+1);
		  assertThat(counter.getDayCount()).isEqualTo(counterL.getDayCount()+1);
		  assertThat(counter.getQuarterCount()).isEqualTo(counterL.getQuarterCount()+1);
	  }
	  
	  @Test
	  public void shouldIncreaseAllowed() {
		  assertThat(this.callCounterService).isNotNull();
		  
		  assertThat(this.callCounterService.increaseAllowed()).isTrue();

		  CallCounter counter = this.callCounterService.findCounter();
		  counter.setQuarterCount(400);

		  assertThat(this.callCounterService.increaseAllowed()).isFalse();
	  }
}