package nl.tisit.mtbcrewpatrol.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.ActivityInfo;
import nl.tisit.mtbcrewpatrol.model.ActivityStatus;
import nl.tisit.mtbcrewpatrol.model.ResourceStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
//@Sql({"/test-data.sql"})
public class ActivityStatusRepositoryTests {
	  @Autowired
	  protected ActivityStatusRepository activityStatusRepository;
	  
	  @Test
	  public void shouldfindById() {
		  assertThat(this.activityStatusRepository).isNotNull();
		  ActivityStatus activityStatus = activityStatusRepository.findOne(697896352);
		  assertThat(activityStatus.getStatus()).isEqualTo(ResourceStatus.SUMMARY);
	  }
	  
	  @Test
	  public void shouldfindByIdAndStatus() {
		  assertThat(this.activityStatusRepository).isNotNull();
		  List<ActivityStatus> activitiesStatus = activityStatusRepository.findByAthleteIdAndStatus(6126748, ResourceStatus.DETAILED);
		  assertThat(activitiesStatus.size()).isEqualTo(0);
		  activitiesStatus = activityStatusRepository.findByAthleteIdAndStatus(6126748, ResourceStatus.META);
		  assertThat(activitiesStatus.size()).isEqualTo(0);
		  activitiesStatus = activityStatusRepository.findByAthleteIdAndStatus(6126748, ResourceStatus.SUMMARY);			
		  assertThat(activitiesStatus.size()).isEqualTo(283);
		  activitiesStatus = (List<ActivityStatus>)activityStatusRepository.findAll();			
		  assertThat(activitiesStatus.size()).isEqualTo(283);
		  activitiesStatus = activityStatusRepository.findByAthleteId(6126748);			
		  assertThat(activitiesStatus.size()).isEqualTo(283);
	  }
	  
	  @Test
	  public void shouldfindByRestrictedArea() {	  
		  assertThat(this.activityStatusRepository).isNotNull();
		  List<ActivityStatus> activitiesStatus = activityStatusRepository.findByRestrictedArea(1);
		  assertThat(activitiesStatus.size()).isEqualTo(0);
	  }
	  
	  @Test
	  public void shouldUpdateInfo() {
		  assertThat(this.activityStatusRepository).isNotNull();
		  ActivityStatus activitiesStatus = activityStatusRepository.findOne(183053917);
		  assertThat(activitiesStatus).isNotNull();
		  
		  ActivityInfo info = new ActivityInfo();
		  info.setRoadDistance(new Float(1.0));
		  info.setRoadPoints(3);
		  activitiesStatus.setInfo(info);
		  activityStatusRepository.save(activitiesStatus); 
	  }
	  
}
