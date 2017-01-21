package nl.tisit.mtbcrewpatrol.etl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class EtlServiceTests {
	@Autowired
	protected EtlService etlService;
	
	 @Test
	  public void shouldExportTracks() {
		 assertThat(this.etlService).isNotNull();
		 
		 this.etlService.exportTracks();;

	 }
	 
	 @Test
	  public void shouldExportWildRides() {
		 assertThat(this.etlService).isNotNull();
		 
		 this.etlService.exportWildRides();;

	 }
	 
	 @Test
	 public void shouldExportAggregateCsv() {
		 assertThat(this.etlService).isNotNull();
		 
		 this.etlService.exportAggregate(ExportFormat.CSV);
	 }

	 @Test
	 public void shouldExportAggregateArff() {
		 assertThat(this.etlService).isNotNull();
		 
		 this.etlService.exportAggregate(ExportFormat.ARFF);
	 }
}
