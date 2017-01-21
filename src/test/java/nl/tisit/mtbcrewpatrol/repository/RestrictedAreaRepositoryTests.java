package nl.tisit.mtbcrewpatrol.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.RestrictedArea;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
//@Sql({"/test-data.sql"})
public class RestrictedAreaRepositoryTests {
	  @Autowired
	  protected RestrictedAreaRepository restrictedAreaRepository;
	  
	  @Test
	  @Transactional
	  public void shouldfindById() {
		  assertThat(this.restrictedAreaRepository).isNotNull();
		  RestrictedArea restrictedArea = restrictedAreaRepository.findOne(3);
		  assertThat(restrictedArea.getName()).isEqualTo("Hoge Ginkel 1");
		  assertThat(restrictedArea.getRestrictedAreaExceptions().size()).isEqualTo(0);

		  restrictedArea = restrictedAreaRepository.findOne(13);
		  assertThat(restrictedArea.getName()).isEqualTo("Amerongen 1");
//		  assertThat(restrictedArea.getRestrictedAreaExceptions().size()).isEqualTo(1);
		  assertThat(restrictedArea.getRestrictedAreaExceptions().get(0).getReason()).isEqualTo("Mountainbike Cyclo MIDDEN NEDERLAND");
		  assertThat(restrictedArea.getRestrictedAreaExceptions().get(0).getId()).isEqualTo(1);

		  restrictedArea = restrictedAreaRepository.findOne(19);
		  assertThat(restrictedArea.getName()).isEqualTo("Amerongen 7");
		  assertThat(restrictedArea.getRestrictedAreaExceptions().size()).isEqualTo(2);
		  assertThat(restrictedArea.getRestrictedAreaExceptions().get(0).getReason()).isEqualTo("Mountainbike Cyclo MIDDEN NEDERLAND");
		  assertThat(restrictedArea.getRestrictedAreaExceptions().get(1).getReason()).isEqualTo("Omleidingen ivm boswerkzaamheden");
	  }
}
