package nl.tisit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.StravaCrawlerServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class PolylineTests {
	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);
	
	@Test
	public void timeTest() {
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime nowz = ZonedDateTime.now();
		ZonedDateTime nowz2 = ZonedDateTime.parse("2014-08-20T18:55:54Z[GMT]");
		ZonedDateTime nowz3 = ZonedDateTime.parse("2016-06-09T15:35:43Z");
		ZonedDateTime nowz4 = ZonedDateTime.parse("2016-06-09T17:35:43Z");
		ZonedDateTime nowz5 = ZonedDateTime.parse("2016-06-09T17:35:43Z").withZoneSameLocal(ZoneId.of("GMT+01:00"));
		log.debug("now: "+now.toString());
		log.debug("nowz: "+nowz.toString());
		log.debug("now2: "+nowz2.toString());
		log.debug("now3: "+nowz3.toString());
		log.debug("now4: "+nowz4.toString());
		log.debug("now5: "+nowz5.toString());
		log.debug("now3l: "+nowz3.toLocalDateTime().toString());
		log.debug("now4l: "+nowz4.toLocalDateTime().toString());
		log.debug("now5l: "+nowz5.toLocalDateTime().toString());
		log.debug("now3s: "+nowz3.toEpochSecond());
		log.debug("now4s: "+nowz4.toEpochSecond());
		log.debug("now5s: "+nowz5.toEpochSecond());
	//	(GMT+01:00) Europe/Amsterdam	
		
		
	}
	
	
	@Test
	public void shouldDecodePolyline2() {
		List<LatLng> latLngList = new ArrayList<LatLng>();
		String ra;
		String path;
		
		ra = "Hoge Ginkel 1";
		latLngList.add(new LatLng(52.0268581,5.4593468));
		latLngList.add(new LatLng(52.0265016,5.4584241));
		latLngList.add(new LatLng(52.0254322,5.4652691));
		latLngList.add(new LatLng(52.0257755,5.4665995));
		latLngList.add(new LatLng(52.0257491,5.4697752));
		latLngList.add(new LatLng(52.0250097,5.4722214));
		latLngList.add(new LatLng(52.0247324,5.4752684));
		latLngList.add(new LatLng(52.0250889,5.4803753));
		latLngList.add(new LatLng(52.0277823,5.4842806));
		latLngList.add(new LatLng(52.0315844,5.487628));
		latLngList.add(new LatLng(52.034251,5.4887438));
		latLngList.add(new LatLng(52.0374455,5.4824352));
		latLngList.add(new LatLng(52.0379206,5.4794741));
		latLngList.add(new LatLng(52.0357031,5.4701614));
		latLngList.add(new LatLng(52.032931,5.4632092));
		latLngList.add(new LatLng(52.029789,5.4641533));
		latLngList.add(new LatLng(52.0268581,5.4593468));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Hoge Ginkel 2";
		latLngList.add(new LatLng(52.0242967,5.4651404));
		latLngList.add(new LatLng(52.0229763,5.4651403));
		latLngList.add(new LatLng(52.0210221,5.4688526));
		latLngList.add(new LatLng(52.020494,5.4706979));
		latLngList.add(new LatLng(52.0186518,5.4704779));
		latLngList.add(new LatLng(52.0167568,5.4698502));
		latLngList.add(new LatLng(52.0153966,5.4689383));
		latLngList.add(new LatLng(52.0141289,5.4685736));
		latLngList.add(new LatLng(52.0137855,5.4707623));
		latLngList.add(new LatLng(52.0189358,5.4738522));
		latLngList.add(new LatLng(52.0222369,5.4773283));
		latLngList.add(new LatLng(52.0240195,5.4729617));
		latLngList.add(new LatLng(52.0242967,5.4651404));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Leersum 1";
		latLngList.add(new LatLng(52.0145251,5.465827));
		latLngList.add(new LatLng(52.0201506,5.4669428));
		latLngList.add(new LatLng(52.0221841,5.4620934));
		latLngList.add(new LatLng(52.0242967,5.4621792));
		latLngList.add(new LatLng(52.0244816,5.4596901));
		latLngList.add(new LatLng(52.026198,5.457201));
		latLngList.add(new LatLng(52.0240855,5.4532099));
		latLngList.add(new LatLng(52.0229763,5.4543686));
		latLngList.add(new LatLng(52.0164796,5.4511929));
		latLngList.add(new LatLng(52.016955,5.4469013));
		latLngList.add(new LatLng(52.0198865,5.448103));
		latLngList.add(new LatLng(52.0202299,5.4456568));
		latLngList.add(new LatLng(52.0166116,5.4439831));
		latLngList.add(new LatLng(52.0161362,5.4453564));
		latLngList.add(new LatLng(52.0150533,5.4451418));
		latLngList.add(new LatLng(52.0165324,5.4478455));
		latLngList.add(new LatLng(52.0164267,5.4496908));
		latLngList.add(new LatLng(52.0157664,5.4532099));
		latLngList.add(new LatLng(52.0140232,5.4523516));
		latLngList.add(new LatLng(52.0124648,5.4548836));
		latLngList.add(new LatLng(52.0116988,5.4547548));
		latLngList.add(new LatLng(52.0114611,5.4553127));
		latLngList.add(new LatLng(52.0122535,5.4560423));
		latLngList.add(new LatLng(52.0119365,5.460248));
		latLngList.add(new LatLng(52.0130459,5.4630804));
		latLngList.add(new LatLng(52.0135214,5.46278));
		latLngList.add(new LatLng(52.0146043,5.4629946));
		latLngList.add(new LatLng(52.0145251,5.465827));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Leersum 2";
		latLngList.add(new LatLng(52.0268581,5.4277611));
		latLngList.add(new LatLng(52.0226066,5.4269886));
		latLngList.add(new LatLng(52.0234253,5.4316235));
		latLngList.add(new LatLng(52.0234253,5.4377174));
		latLngList.add(new LatLng(52.0239534,5.4450989));
		latLngList.add(new LatLng(52.0246928,5.4492188));
		latLngList.add(new LatLng(52.0245344,5.4518795));
		latLngList.add(new LatLng(52.0251681,5.453167));
		latLngList.add(new LatLng(52.0257755,5.4514074));
		latLngList.add(new LatLng(52.0289705,5.4494333));
		latLngList.add(new LatLng(52.0336702,5.4485321));
		latLngList.add(new LatLng(52.0349375,5.4470301));
		latLngList.add(new LatLng(52.0350431,5.4447985));
		latLngList.add(new LatLng(52.0334326,5.442996));
		latLngList.add(new LatLng(52.0337758,5.4415369));
		latLngList.add(new LatLng(52.0348055,5.4414511));
		latLngList.add(new LatLng(52.0356107,5.442524));
		latLngList.add(new LatLng(52.0360463,5.4417086));
		latLngList.add(new LatLng(52.0343962,5.4379106));
		latLngList.add(new LatLng(52.0329574,5.4375243));
		latLngList.add(new LatLng(52.0329706,5.4357433));
		latLngList.add(new LatLng(52.0295778,5.4347777));
		latLngList.add(new LatLng(52.0277955,5.4340911));
		latLngList.add(new LatLng(52.0273202,5.4331899));
		latLngList.add(new LatLng(52.0270826,5.4314733));
		latLngList.add(new LatLng(52.0271354,5.4278469));
		latLngList.add(new LatLng(52.0268581,5.4277611));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Leersum 3";
		latLngList.add(new LatLng(52.0082384,5.46278));
		latLngList.add(new LatLng(52.0099818,5.4661703));
		latLngList.add(new LatLng(52.0129667,5.4656124));
		latLngList.add(new LatLng(52.0108535,5.4604626));
		latLngList.add(new LatLng(52.0109856,5.4562569));
		latLngList.add(new LatLng(52.0096913,5.4547548));
		latLngList.add(new LatLng(52.0092158,5.4600763));
		latLngList.add(new LatLng(52.0082384,5.46278));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Leersum 4";
		latLngList.add(new LatLng(52.0090045,5.4534674));
		latLngList.add(new LatLng(52.009295,5.4525447));
		latLngList.add(new LatLng(52.0098101,5.4523945));
		latLngList.add(new LatLng(52.0122667,5.445013));
		latLngList.add(new LatLng(52.0131648,5.4447341));
		latLngList.add(new LatLng(52.0163211,5.4409575));
		latLngList.add(new LatLng(52.0190018,5.4419875));
		latLngList.add(new LatLng(52.0157664,5.4379749));
		latLngList.add(new LatLng(52.0131252,5.4411077));
		latLngList.add(new LatLng(52.0118573,5.4398632));
		latLngList.add(new LatLng(52.0077629,5.4475021));
		latLngList.add(new LatLng(52.005174,5.4439402));
		latLngList.add(new LatLng(52.002968,5.4473305));
		latLngList.add(new LatLng(52.0090045,5.4534674));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Leersum 5";
		latLngList.add(new LatLng(52.010946,5.4529095));
		latLngList.add(new LatLng(52.0126101,5.4527164));
		latLngList.add(new LatLng(52.0137855,5.4496908));
		latLngList.add(new LatLng(52.0150533,5.4511285));
		latLngList.add(new LatLng(52.0156212,5.4495835));
		latLngList.add(new LatLng(52.0148816,5.4470515));
		latLngList.add(new LatLng(52.0131516,5.446279));
		latLngList.add(new LatLng(52.010946,5.4529095));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Leersum 6";
		latLngList.add(new LatLng(52.0228707,5.4480171));
		latLngList.add(new LatLng(52.0221313,5.4345846));
		latLngList.add(new LatLng(52.0190678,5.4354));
		latLngList.add(new LatLng(52.0191999,5.4402494));
		latLngList.add(new LatLng(52.0228707,5.4480171));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Leersum 7";
		latLngList.add(new LatLng(52.0269109,5.4251003));
		latLngList.add(new LatLng(52.0270958,5.4208517));
		latLngList.add(new LatLng(52.0300267,5.417161));
		latLngList.add(new LatLng(52.0217088,5.4199934));
		latLngList.add(new LatLng(52.0227915,5.4244995));
		latLngList.add(new LatLng(52.0269109,5.4251003));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Leersum 8";
		latLngList.add(new LatLng(52.0179718,5.4206157));
		latLngList.add(new LatLng(52.0166248,5.4258084));
		latLngList.add(new LatLng(52.0188962,5.4281688));
		latLngList.add(new LatLng(52.0188697,5.429585));
		latLngList.add(new LatLng(52.0200582,5.4291558));
		latLngList.add(new LatLng(52.0204411,5.4245639));
		latLngList.add(new LatLng(52.0190414,5.4241776));
		latLngList.add(new LatLng(52.0181171,5.4232979));
		latLngList.add(new LatLng(52.0182227,5.4218602));
		latLngList.add(new LatLng(52.0186849,5.4209375));
		latLngList.add(new LatLng(52.0179718,5.4206157));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 1";
		latLngList.add(new LatLng(52.0066137,5.4700112));
		latLngList.add(new LatLng(52.0056627,5.4687452));
		latLngList.add(new LatLng(52.0023339,5.470891));
		latLngList.add(new LatLng(52.0005505,5.475204));
		latLngList.add(new LatLng(51.9994144,5.4775643));
		latLngList.add(new LatLng(52.0001674,5.479753));
		latLngList.add(new LatLng(51.999824,5.4829073));
		latLngList.add(new LatLng(52.0001806,5.4870915));
		latLngList.add(new LatLng(52.0003392,5.4861474));
		latLngList.add(new LatLng(52.0009468,5.4840446));
		latLngList.add(new LatLng(52.0023339,5.486083));
		latLngList.add(new LatLng(52.0042625,5.4847741));
		latLngList.add(new LatLng(52.0040512,5.4804182));
		latLngList.add(new LatLng(52.0044078,5.4793453));
		latLngList.add(new LatLng(52.0052532,5.4796886));
		latLngList.add(new LatLng(52.0057288,5.4765129));
		latLngList.add(new LatLng(52.0034435,5.4748821));
		latLngList.add(new LatLng(52.0038002,5.4726291));
		latLngList.add(new LatLng(52.0066137,5.4700112));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 2";
		latLngList.add(new LatLng(51.9922007,5.5053949));
		latLngList.add(new LatLng(51.9930992,5.5031204));
		latLngList.add(new LatLng(51.9938523,5.5013609));
		latLngList.add(new LatLng(51.9960455,5.4990005));
		latLngList.add(new LatLng(51.9952,5.4973698));
		latLngList.add(new LatLng(51.9955699,5.4965115));
		latLngList.add(new LatLng(51.995266,5.4961467));
		latLngList.add(new LatLng(51.9953057,5.4946446));
		latLngList.add(new LatLng(51.9961248,5.4940653));
		latLngList.add(new LatLng(51.9977366,5.4965329));
		latLngList.add(new LatLng(51.9974592,5.4949021));
		latLngList.add(new LatLng(51.9983707,5.4932714));
		latLngList.add(new LatLng(51.9968779,5.4918337));
		latLngList.add(new LatLng(51.9965344,5.4931211));
		latLngList.add(new LatLng(51.9950943,5.4943657));
		latLngList.add(new LatLng(51.9943015,5.4966831));
		latLngList.add(new LatLng(51.9922007,5.5053949));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 3";
		latLngList.add(new LatLng(51.9991238,5.4792595));
		latLngList.add(new LatLng(51.9950282,5.4792166));
		latLngList.add(new LatLng(51.9950282,5.477457));
		latLngList.add(new LatLng(51.9907208,5.4822636));
		latLngList.add(new LatLng(51.9942355,5.4848814));
		latLngList.add(new LatLng(51.9941033,5.4878426));
		latLngList.add(new LatLng(51.9985689,5.4920053));
		latLngList.add(new LatLng(51.9998108,5.4893017));
		latLngList.add(new LatLng(51.9989917,5.4818773));
		latLngList.add(new LatLng(51.9991238,5.4792595));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 4";
		latLngList.add(new LatLng(52.0018253,5.4918122));
		latLngList.add(new LatLng(51.9987341,5.4940975));
		latLngList.add(new LatLng(51.9982188,5.4949236));
		latLngList.add(new LatLng(51.9990181,5.4971123));
		latLngList.add(new LatLng(51.9990181,5.5012751));
		latLngList.add(new LatLng(51.998014,5.5026913));
		latLngList.add(new LatLng(51.9971157,5.4972839));
		latLngList.add(new LatLng(51.9962437,5.4971123));
		latLngList.add(new LatLng(51.9969836,5.4994726));
		latLngList.add(new LatLng(51.9965608,5.5007601));
		latLngList.add(new LatLng(51.9948697,5.5020046));
		latLngList.add(new LatLng(51.994579,5.5044079));
		latLngList.add(new LatLng(51.9938391,5.5044079));
		latLngList.add(new LatLng(51.9931785,5.506382));
		latLngList.add(new LatLng(51.9965608,5.5082273));
		latLngList.add(new LatLng(51.9985689,5.5090427));
		latLngList.add(new LatLng(52.0012903,5.5080986));
		latLngList.add(new LatLng(52.0012375,5.5055666));
		latLngList.add(new LatLng(52.002215,5.5045366));
		latLngList.add(new LatLng(52.0063364,5.504365));
		latLngList.add(new LatLng(52.0018253,5.4918122));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 5";
		latLngList.add(new LatLng(52.006759,5.50282));
		latLngList.add(new LatLng(52.0087403,5.5099869));
		latLngList.add(new LatLng(52.0091365,5.5075407));
		latLngList.add(new LatLng(52.0084761,5.5035925));
		latLngList.add(new LatLng(52.0107743,5.5020046));
		latLngList.add(new LatLng(52.006759,5.50282));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 6";
		latLngList.add(new LatLng(52.0074459,5.5084848));
		latLngList.add(new LatLng(52.0054117,5.5086994));
		latLngList.add(new LatLng(52.0057023,5.5138063));
		latLngList.add(new LatLng(52.0092686,5.5147076));
		latLngList.add(new LatLng(52.0074459,5.5084848));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 7";
		latLngList.add(new LatLng(52.0011054,5.4889369));
		latLngList.add(new LatLng(52.0029812,5.4930139));
		latLngList.add(new LatLng(52.0062307,5.5015755));
		latLngList.add(new LatLng(52.0107743,5.5006742));
		latLngList.add(new LatLng(52.0099554,5.4983139));
		latLngList.add(new LatLng(52.0104309,5.4973269));
		latLngList.add(new LatLng(52.0154495,5.4929924));
		latLngList.add(new LatLng(52.0147628,5.4902458));
		latLngList.add(new LatLng(52.0104573,5.4851818));
		latLngList.add(new LatLng(52.0098498,5.4834223));
		latLngList.add(new LatLng(52.009295,5.4813623));
		latLngList.add(new LatLng(52.0101667,5.473938));
		latLngList.add(new LatLng(52.0079742,5.473423));
		latLngList.add(new LatLng(52.0068383,5.4721785));
		latLngList.add(new LatLng(52.005174,5.474453));
		latLngList.add(new LatLng(52.0067062,5.4757833));
		latLngList.add(new LatLng(52.0062307,5.4787445));
		latLngList.add(new LatLng(52.0073402,5.4822206));
		latLngList.add(new LatLng(52.0068911,5.4835939));
		latLngList.add(new LatLng(52.0051476,5.4828644));
		latLngList.add(new LatLng(52.0048834,5.4856539));
		latLngList.add(new LatLng(52.0036945,5.4869413));
		latLngList.add(new LatLng(52.0021093,5.4871988));
		latLngList.add(new LatLng(52.0011054,5.4889369));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 8";
		latLngList.add(new LatLng(52.0106158,5.477221));
		latLngList.add(new LatLng(52.009995,5.4811049));
		latLngList.add(new LatLng(52.0109988,5.4842377));
		latLngList.add(new LatLng(52.015674,5.4899669));
		latLngList.add(new LatLng(52.0203355,5.4889154));
		latLngList.add(new LatLng(52.0190282,5.4847527));
		latLngList.add(new LatLng(52.0198469,5.4844308));
		latLngList.add(new LatLng(52.0200978,5.485096));
		latLngList.add(new LatLng(52.0227387,5.4823923));
		latLngList.add(new LatLng(52.0236761,5.4845595));
		latLngList.add(new LatLng(52.0236761,5.4861259));
		latLngList.add(new LatLng(52.0254058,5.4849458));
		latLngList.add(new LatLng(52.0231876,5.4816628));
		latLngList.add(new LatLng(52.0214579,5.480032));
		latLngList.add(new LatLng(52.0187245,5.4761052));
		latLngList.add(new LatLng(52.0153042,5.4738522));
		latLngList.add(new LatLng(52.0146307,5.4763412));
		latLngList.add(new LatLng(52.0129931,5.4751611));
		latLngList.add(new LatLng(52.0117781,5.4785299));
		latLngList.add(new LatLng(52.011012,5.4780149));
		latLngList.add(new LatLng(52.0106158,5.477221));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 9";
		latLngList.add(new LatLng(51.9934427,5.5086565));
		latLngList.add(new LatLng(51.9920157,5.5177546));
		latLngList.add(new LatLng(52.0007355,5.5295992));
		latLngList.add(new LatLng(52.0014488,5.5348349));
		latLngList.add(new LatLng(52.0027434,5.5384827));
		latLngList.add(new LatLng(52.0050155,5.538311));
		latLngList.add(new LatLng(52.0065477,5.5366802));
		latLngList.add(new LatLng(52.007578,5.5326033));
		latLngList.add(new LatLng(52.0077365,5.5302858));
		latLngList.add(new LatLng(52.0088195,5.5268526));
		latLngList.add(new LatLng(52.0100347,5.5240202));
		latLngList.add(new LatLng(52.0103517,5.5184412));
		latLngList.add(new LatLng(52.0097441,5.5166388));
		latLngList.add(new LatLng(52.0043814,5.5148792));
		latLngList.add(new LatLng(52.0042757,5.507412));
		latLngList.add(new LatLng(52.0052004,5.5070257));
		latLngList.add(new LatLng(52.0051211,5.5061674));
		latLngList.add(new LatLng(52.0023471,5.5069828));
		latLngList.add(new LatLng(52.0019508,5.509901));
		latLngList.add(new LatLng(51.9975649,5.510931));
		latLngList.add(new LatLng(51.9934427,5.5086565));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 10";
		latLngList.add(new LatLng(52.0110649,5.51651));
		latLngList.add(new LatLng(52.0164267,5.5207586));
		latLngList.add(new LatLng(52.0193055,5.5137634));
		latLngList.add(new LatLng(52.0153438,5.509429));
		latLngList.add(new LatLng(52.0151061,5.5064463));
		latLngList.add(new LatLng(52.0142345,5.5032921));
		latLngList.add(new LatLng(52.0121214,5.5027342));
		latLngList.add(new LatLng(52.0115139,5.5041933));
		latLngList.add(new LatLng(52.0100083,5.5045795));
		latLngList.add(new LatLng(52.0105101,5.5071545));
		latLngList.add(new LatLng(52.0101139,5.5133343));
		latLngList.add(new LatLng(52.0110649,5.51651));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 11";
		latLngList.add(new LatLng(52.0290233,5.4876709));
		latLngList.add(new LatLng(52.028152,5.487628));
		latLngList.add(new LatLng(52.0228443,5.4900312));
		latLngList.add(new LatLng(52.0198337,5.4912758));
		latLngList.add(new LatLng(52.0170606,5.4914904));
		latLngList.add(new LatLng(52.020045,5.4953527));
		latLngList.add(new LatLng(52.024772,5.4919195));
		latLngList.add(new LatLng(52.0290233,5.4876709));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Amerongen 12";
		latLngList.add(new LatLng(52.0184868,5.4959965));
		latLngList.add(new LatLng(52.0160042,5.4964685));
		latLngList.add(new LatLng(52.0126761,5.4994297));
		latLngList.add(new LatLng(52.0128082,5.5014896));
		latLngList.add(new LatLng(52.0149213,5.5014467));
		latLngList.add(new LatLng(52.0163475,5.5080128));
		latLngList.add(new LatLng(52.0207052,5.4973269));
		latLngList.add(new LatLng(52.0184868,5.4959965));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Kwintelooijen 1";
		latLngList.add(new LatLng(51.994764,5.5256081));
		latLngList.add(new LatLng(51.9850387,5.5458212));
		latLngList.add(new LatLng(51.9821313,5.5452204));
		latLngList.add(new LatLng(51.9808097,5.5426455));
		latLngList.add(new LatLng(51.9776377,5.5439758));
		latLngList.add(new LatLng(51.9792766,5.5521297));
		latLngList.add(new LatLng(51.9786422,5.5532885));
		latLngList.add(new LatLng(51.9803339,5.5549622));
		latLngList.add(new LatLng(51.9785893,5.5629873));
		latLngList.add(new LatLng(51.9837172,5.5635452));
		latLngList.add(new LatLng(51.9880252,5.560112));
		latLngList.add(new LatLng(51.9924386,5.5552197));
		latLngList.add(new LatLng(51.9916722,5.5538464));
		latLngList.add(new LatLng(51.9895845,5.5541897));
		latLngList.add(new LatLng(51.9880517,5.5516148));
		latLngList.add(new LatLng(51.9878402,5.547967));
		latLngList.add(new LatLng(51.9880781,5.546186));
		latLngList.add(new LatLng(51.9889634,5.5440831));
		latLngList.add(new LatLng(51.9905226,5.5441046));
		latLngList.add(new LatLng(51.9915665,5.5450058));
		latLngList.add(new LatLng(51.9930728,5.5439758));
		latLngList.add(new LatLng(51.9955831,5.5468941));
		latLngList.add(new LatLng(51.9985689,5.5422163));
		latLngList.add(new LatLng(52.0015281,5.540328));
		latLngList.add(new LatLng(51.9997843,5.5321312));
		latLngList.add(new LatLng(51.998199,5.5330753));
		latLngList.add(new LatLng(51.997697,5.5310583));
		latLngList.add(new LatLng(51.9978291,5.5283546));
		latLngList.add(new LatLng(51.994764,5.5256081));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Rhenen 1";
		latLngList.add(new LatLng(51.9770032,5.5484176));
		latLngList.add(new LatLng(51.9764481,5.5496407));
		latLngList.add(new LatLng(51.9765803,5.5534601));
		latLngList.add(new LatLng(51.9733815,5.5564213));
		latLngList.add(new LatLng(51.974201,5.55861));
		latLngList.add(new LatLng(51.9754039,5.5569577));
		latLngList.add(new LatLng(51.9773469,5.5567002));
		latLngList.add(new LatLng(51.9773601,5.5547047));
		latLngList.add(new LatLng(51.9769636,5.5509496));
		latLngList.add(new LatLng(51.9770032,5.5484176));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Rhenen 2";
		latLngList.add(new LatLng(51.9754039,5.5456281));
		latLngList.add(new LatLng(51.9729321,5.5472159));
		latLngList.add(new LatLng(51.9728395,5.5483961));
		latLngList.add(new LatLng(51.9691382,5.5524731));
		latLngList.add(new LatLng(51.9683846,5.5507565));
		latLngList.add(new LatLng(51.9640483,5.5577517));
		latLngList.add(new LatLng(51.9650134,5.5596614));
		latLngList.add(new LatLng(51.9646564,5.5618286));
		latLngList.add(new LatLng(51.9673931,5.56288));
		latLngList.add(new LatLng(51.9678823,5.5620217));
		latLngList.add(new LatLng(51.9655951,5.5605841));
		latLngList.add(new LatLng(51.9655026,5.5594683));
		latLngList.add(new LatLng(51.966005,5.5581164));
		latLngList.add(new LatLng(51.9679616,5.5565071));
		latLngList.add(new LatLng(51.9681995,5.556078));
		latLngList.add(new LatLng(51.9683317,5.5551338));
		latLngList.add(new LatLng(51.96931,5.5544043));
		latLngList.add(new LatLng(51.9702222,5.5525589));
		latLngList.add(new LatLng(51.9709757,5.5523443));
		latLngList.add(new LatLng(51.9729585,5.5546188));
		latLngList.add(new LatLng(51.9742539,5.5486965));
		latLngList.add(new LatLng(51.9754039,5.5456281));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Rhenen 3";
		latLngList.add(new LatLng(51.9693629,5.560112));
		latLngList.add(new LatLng(51.969548,5.5637169));
		latLngList.add(new LatLng(51.9716631,5.5633736));
		latLngList.add(new LatLng(51.9715044,5.5577087));
		latLngList.add(new LatLng(51.9693629,5.560112));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Doorn 1";
		latLngList.add(new LatLng(52.0182227,5.4142857));
		latLngList.add(new LatLng(52.0215503,5.4172897));
		latLngList.add(new LatLng(52.0315844,5.4136848));
		latLngList.add(new LatLng(52.0310036,5.4087067));
		latLngList.add(new LatLng(52.0327461,5.4030418));
		latLngList.add(new LatLng(52.0326405,5.3958321));
		latLngList.add(new LatLng(52.0404549,5.3769493));
		latLngList.add(new LatLng(52.0367591,5.3731728));
		latLngList.add(new LatLng(52.0308452,5.3906822));
		latLngList.add(new LatLng(52.0279935,5.3896523));
		latLngList.add(new LatLng(52.0182227,5.4142857));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Doorn 2";
		latLngList.add(new LatLng(52.0598792,5.3560925));
		latLngList.add(new LatLng(52.0508542,5.3521442));
		latLngList.add(new LatLng(52.0435169,5.3494835));
		latLngList.add(new LatLng(52.038343,5.3521442));
		latLngList.add(new LatLng(52.0359671,5.3521442));
		latLngList.add(new LatLng(52.034383,5.365448));
		latLngList.add(new LatLng(52.0383958,5.3674221));
		latLngList.add(new LatLng(52.0429362,5.3733444));
		latLngList.add(new LatLng(52.0453117,5.3793526));
		latLngList.add(new LatLng(52.0458924,5.3863907));
		latLngList.add(new LatLng(52.0464731,5.3889656));
		latLngList.add(new LatLng(52.0507487,5.3756618));
		latLngList.add(new LatLng(52.0539155,5.3663921));
		latLngList.add(new LatLng(52.0571349,5.3621864));
		latLngList.add(new LatLng(52.0604596,5.3597832));
		latLngList.add(new LatLng(52.0598792,5.3560925));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Doorn 3";
		latLngList.add(new LatLng(52.0470801,5.3476381));
		latLngList.add(new LatLng(52.0587181,5.3524017));
		latLngList.add(new LatLng(52.0535724,5.3404713));
		latLngList.add(new LatLng(52.0492443,5.3382826));
		latLngList.add(new LatLng(52.0470801,5.3476381));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Doorn 4";
		latLngList.add(new LatLng(52.0514612,5.3339911));
		latLngList.add(new LatLng(52.0563433,5.3422308));
		latLngList.add(new LatLng(52.0624121,5.3304291));
		latLngList.add(new LatLng(52.060961,5.3292704));
		latLngList.add(new LatLng(52.0579266,5.3280258));
		latLngList.add(new LatLng(52.0514612,5.3339911));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Maarn 1";
		latLngList.add(new LatLng(52.0663433,5.353732));
		latLngList.add(new LatLng(52.0685857,5.3543328));
		latLngList.add(new LatLng(52.0715666,5.3484105));
		latLngList.add(new LatLng(52.0729779,5.3499125));
		latLngList.add(new LatLng(52.0801917,5.3339053));
		latLngList.add(new LatLng(52.0781608,5.327704));
		latLngList.add(new LatLng(52.0743627,5.3251506));
		latLngList.add(new LatLng(52.0696145,5.3234983));
		latLngList.add(new LatLng(52.0671216,5.3204085));
		latLngList.add(new LatLng(52.0672798,5.3260303));
		latLngList.add(new LatLng(52.0660003,5.3283049));
		latLngList.add(new LatLng(52.0663433,5.353732));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Zeist 1";
		latLngList.add(new LatLng(52.080772,5.2929211));
		latLngList.add(new LatLng(52.0779763,5.2832222));
		latLngList.add(new LatLng(52.0769212,5.2779007));
		latLngList.add(new LatLng(52.0715402,5.269146));
		latLngList.add(new LatLng(52.0678998,5.2753258));
		latLngList.add(new LatLng(52.0667917,5.2840805));
		latLngList.add(new LatLng(52.0704323,5.290947));
		latLngList.add(new LatLng(52.0686384,5.2997875));
		latLngList.add(new LatLng(52.0667917,5.3015041));
		latLngList.add(new LatLng(52.0671083,5.3109455));
		latLngList.add(new LatLng(52.071382,5.3094006));
		latLngList.add(new LatLng(52.0759717,5.3019333));
		latLngList.add(new LatLng(52.080772,5.2929211));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Zeist 2";
		latLngList.add(new LatLng(52.0823543,5.2921486));
		latLngList.add(new LatLng(52.086231,5.2966547));
		latLngList.add(new LatLng(52.0872858,5.2939081));
		latLngList.add(new LatLng(52.0848333,5.2837372));
		latLngList.add(new LatLng(52.0853607,5.2785444));
		latLngList.add(new LatLng(52.0838839,5.2760124));
		latLngList.add(new LatLng(52.0821961,5.2789307));
		latLngList.add(new LatLng(52.0788203,5.2796173));
		latLngList.add(new LatLng(52.0823543,5.2921486));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Zeist 3";
		latLngList.add(new LatLng(52.0883933,5.2646828));
		latLngList.add(new LatLng(52.091742,5.2760983));
		latLngList.add(new LatLng(52.0920848,5.2781153));
		latLngList.add(new LatLng(52.0940623,5.2766562));
		latLngList.add(new LatLng(52.096356,5.2725792));
		latLngList.add(new LatLng(52.0910037,5.2631378));
		latLngList.add(new LatLng(52.0883933,5.2646828));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		ra = "Zeist 4";
		latLngList.add(new LatLng(52.0688231,5.3165674));
		latLngList.add(new LatLng(52.0713292,5.3193569));
		latLngList.add(new LatLng(52.0732021,5.3191853));
		latLngList.add(new LatLng(52.0757079,5.3118896));
		latLngList.add(new LatLng(52.0778971,5.3074694));
		latLngList.add(new LatLng(52.0816423,5.3079844));
		latLngList.add(new LatLng(52.085308,5.2994442));
		latLngList.add(new LatLng(52.081906,5.2954531));
		latLngList.add(new LatLng(52.0766575,5.3059673));
		latLngList.add(new LatLng(52.0749958,5.3075123));
		latLngList.add(new LatLng(52.0734395,5.3101301));
		latLngList.add(new LatLng(52.0723844,5.3124046));
		latLngList.add(new LatLng(52.0712237,5.31425));
		latLngList.add(new LatLng(52.0699311,5.3134775));
		latLngList.add(new LatLng(52.0688231,5.3165674));
		
		path = PolylineEncoding.encode(latLngList);
		log.debug(ra+" = \"" + path + "\";");
		latLngList = new ArrayList<LatLng>();
		
		/*
		Hoge Ginkel 1 = "{np|H}gi`@fAxDtEyi@eAiGD{RrCgNv@aRgA}^yOkWwV}SuO}E_Sjf@}ApQzLdy@hPlj@rR{DhQ~\";
		Hoge Ginkel 2 = "{~o|Hclj`@fG?fKeVhBqJnJj@xJ|BnGtD|FhAbAuLe_@iRsSwTcJhZw@zo@";
		Leersum 1 = "yan|Hmpj`@cb@}EuKh]gLQc@pNwIpNdL|W|EgFrg@zR_BxYiQoFcAfNrUnI|AsGxEj@gH}ORoJbC_U|IjDvHyNvCXn@oB}CqC|@iY}EuP}Az@wEi@LwP";
		Leersum 2 = "{np|Hobc`@pYxCcD}[?ce@gBcm@sCwX^sO_CaGyB~I}RjKk\rD}FjHS|L`IfJeAbHmEP_DuEwA`DhIvV~GlAAbJdT~DbJhC~ArDn@vIKrUv@P";
		Leersum 3 = "ozl|Hk}i`@{IeTuQnBfLd_@[fYbGlH|Ai`@bE{O";
		Leersum 4 = "g_m|Hech`@{@xDeB\kNbm@qDv@wRpVwOmEdSbXnOsR|FxFrXwn@dOfUvLeTud@ke@";
		Leersum 5 = "mkm|Hu_h`@kId@kF|Q{F_HqBtHrCxNxIxCvLmh@";
		Leersum 6 = "}uo|Hcag`@rC~rAbRcDYi]}Uqo@";
		Leersum 7 = "eop|H{qb`@e@pYiQ`V~r@uPwEe[wXwB";
		Leersum 8 = "iwn|H{ua`@lGm_@gMwMD{GmFtAkAv[vGjAvDnDS~G{AvDlC~@";
		Amerongen 1 = "ipl|Hqjk`@|DzFxSkLbJ}YbFwMwCuLdAwRgAcY_@zDyBdLsGwKaKdGh@dZgAtEgDcA_BzRhMdIgA`MqPjO";
		Amerongen 2 = "gvi|Hugr`@sDdMuC~IwLvMhDdIiAjDz@fAGlHaDpBcIkNv@dIuDdIhH~GdAaG~GyF|CmMbLmu@";
		Amerongen 3 = "oak|Hkdm`@pXF?~I|Y_]_UkOZoQ}ZaYwF|ObDjm@YjO";
		Amerongen 4 = "mrk|Hyro`@jRiMdBcD_DuL?aYhEyGpDx`@nD`@sCwMrAaGpIwFx@aNrC?bCiKcTqJqKaD_PzDHxNcElEwX`@d[nmA";
		Amerongen 5 = "gql|Hswq`@kKyk@oAhNbCtWiM|H`XcD";
		Amerongen 6 = "qul|H_{r`@vKk@y@}^iUsDjJ|e@";
		Amerongen 7 = "}mk|H{`o`@uJmXiSqt@k[tD`DvM}AbEk^bZhCbPzYt^xB~IlBzKmDjm@vLfB`FvFlIeMsHiG~AoQ}EwTxAqGzIpCt@mPlFaGzHs@fE{I";
		Amerongen 8 = "kim|Hswl`@zBgWgEsRe\yb@e\pEdG`YcD~@q@eCoO|O{DqL?yHyIjFzLpSxIdIbPnWjTbMdCqNfIjFpFaTxCfBlA|C";
		Amerongen 9 = "_~i|Hc|r`@zGyw@ou@aiAmCu_@aGyUgM`@qHdImEnX_@lMwEnTqFtP_Aza@xBfJn`@~IRtm@wDjANjDhPaDnAgQlZmEvXdM";
		Amerongen 10 = "clm|Hemt`@q`@qY_Qvj@xW`Zl@rQnDvRdLnBxBcHjHmAcBaOnAse@}D{R";
		Amerongen 11 = "k|p|H}xn`@lDFd`@_NxQyFhPi@uQcWo\lTqYpY";
		Amerongen 12 = "qzn|H_mp`@pN}AvSoQY{KeLF}G_h@gZvaAzLhG";
		Kwintelooijen 1 = "gfj|Hafv`@v{@i}BdQvBfG`OxRiGgI}q@~BgFqImIzIeq@a_@oB}YnTqZp]xCpG`LcApHbOh@vUo@bJoDdLwHCqEuDkHlEuNeQuQd\oQxJ|Ifr@zH}DbBrKY|OdRbP";
		Rhenen 1 = "gwf|Hstz`@lBsFY{V~RoQcDuLoFhIeKr@AnKnAlVGxN";
		Rhenen 2 = "gmf|Hecz`@lN}HPkFbVmXvCtI`Zuj@_E}JdAqLaPqEaBjDfM~GR|EeBlGeK`Io@tAY|DcEpCuDnJwCj@kKgMaG~c@eFdR";
		Rhenen 3 = "oge|Hu}|`@e@qUeLdA^jb@jL_N";
		Doorn 1 = "{xn|Hin``@ySwQu}@pUrB`^}Ilb@T`l@yo@~tB`VrV|c@mlBzPlE`|@_yC";
		Doorn 2 = "g}v|Hqbu_@lw@tWxl@rOj_@sOxM?|HerAcXiKk[_d@yMqd@sB_k@sBcOwYdrAyR|x@aSfYyS`NrB`V";
		Doorn 3 = "gmt|Hwms_@wgAw\d_@piA`ZtLnLoy@";
		Doorn 4 = "shu|Hmxp_@o]or@}d@vhA`HfF|QvFlg@gd@";
		Maarn 1 = "sex|Hyst_@aMwBsQ~c@yGkHal@~bBtKxe@vV|Nt\hIpNhR_@cb@~FeMcA}}C";
		Zeist 1 = "y_{|Hwwh_@lPr{@rEf`@r`@tu@vUse@|Euu@wU}i@dJgv@pJuI_Aaz@uYtHu[tm@_]hw@";
		Zeist 2 = "ui{|H}rh_@gWc[sEbPjNp~@iBn_@fHxNnIgQbTiCaUimA";
		Zeist 3 = "mo||Hggc_@}SkfAcAsKkKbHkMnXn`@~y@hOsH";
		Zeist 4 = "cux|Hqkm_@uNmPuJ`@uNrl@uLrZkVeB}Ujt@fT|Wx_@w`AjIsHvHkOrEeMfFqJ`GxC|EiR";
		*/
	}
	
	@Test
	public void shouldDecodePolyline() {
//		List<LatLng> latLng = Polyline.decode("{oq}Hwd}_@m@rAbO}B`MrRdLmEt_@{r@hBH|@dCtOwAvE|HjHuJrJr@fEzCtDeKxB|@`AcEdDxCt@pFxIiDlGbG~Kk`@tj@{O`LkIzXiKx@dCjCu@zTfMfGmZLuUlCkV`Bcg@~Voc@zAwZ~Fqb@~EcNzDuDrGf@vKhIhnAjHxkBndAfe@sCt^so@jOiH`Jw]zD_HpHaBhPfApF}OvTw_@hDaNvBsS~BaDdIcAfCkCnCeTdJkS~AwNrCqF~GwAfTzEYdTlb@oWbj@yVpQwAbAo~@vo@vFz^sJxEj@hMlMjWzGrJbLzWzPlJlSbJfHtE~J`OdNl\\bQl_@dM|HlM_@fD");		  
		List<LatLng> latLng = PolylineEncoding.decode("{oq}Hwd}_@m@rAbO}B`MrRdLmEt_@{r@hBH|@dCtOwAvE|HjHuJrJr@fEzCtDeKxB|@`AcEdDxCt@pFxIiDlGbG~Kk`@tj@{O`LkIzXiKx@dCjCu@zTfMfGmZLuUlCkV`Bcg@~Voc@zAwZ~Fqb@~EcNzDuDrGf@vKhIhnAjHxkBndAfe@sCt^so@jOiH`Jw]zD_HpHaBhPfApF}OvTw_@hDaNvBsS~BaDdIcAfCkCnCeTdJkS~AwNrCqF~GwAfTzEYdTlb@oWbj@yVpQwAbAo~@vo@vFz^sJxEj@hMlMjWzGrJbLzWzPlJlSbJfHtE~J`OdNl\\bQl_@dM|HlM_@fD");
		assertThat(latLng.size()).isEqualTo(80);
		latLng = PolylineEncoding.decode("_pp|Haqi`@TocCub@{a@cOfv@xQrjA~^nc@");
		latLng = PolylineEncoding.decode("mlr|Hiwk`@pk@wQxv@aG");
		latLng = PolylineEncoding.decode("mlr|Hiwk`@jcByY");
		latLng = PolylineEncoding.decode("chr|H_po`@bu@bf@fk@tt@");

		latLng = PolylineEncoding.decode("gqx|Hc|v`@lJ|C`Af^li@`]z^qRB|c@nLzDlGoDcEhuCpr@~Ffa@yJpPdOh`@rNyFmBd@b`ArFvRvKrEn@jJiInB`@pZbk@jBfDy_@lGjAWlm@sMD}AfVz@|DjSRhGxVuBdSrDpC`@jJwMjt@wU}Dr@dl@s~@}A~Mni@jt@fJvEfXbIlOsExNg\r_@pEpDNyHbDOvHvn@N_EjCz@yKdl@gL|@eRyZoEhILsHaQcSeJva@|NdSiAxFoKlWiVqD{CxZuPpk@sBx@gDkIeG`Ka\tuA~CtNeAzSpEzU|Xx]~MfFjD`[s|@wWGhR|DnFi@b]rH|[a|@hy@oH[{Nvb@kH`IxEdQySlRuAoG}LaDaT{WgS}CsSfO{Pbi@sRtWwRpu@R{DuJ}MzO|[xXfTzG{U~DiAlKee@dIiOfJoE`Lu`@`KrKhEsGjMf@HjtAeLjAoF~h@~@rH`QxRgAtx@rBl[s\to@ej@qdA{RiFgHxPkGSkGjIHjVcPv^{Yi~AaK^yM{`@MoI|HqBzBy]iK`D{GeN|Huv@~GsXlg@f[fDmvEpNev@fDm~ArCiaCaDav@nA}k@hKgg@yFsc@nWclDaAmg@gR}qB`SmxBgCcQ^sq@lQuqA`BezBrBg^nCM");

		Path2D.Double path = new Path2D.Double();
		path.moveTo(5.46081, 52.02704);
		path.lineTo(5.48201, 52.02704);
		path.lineTo(5.487590, 52.03264);
		path.lineTo(5.478750, 52.03522);
		path.lineTo(5.466650, 52.03221);
		path.lineTo(5.46081, 52.02704);
		
		// "_pp|Haqi`@?ocC_b@{a@cOfv@xQrjAh_@nc@"
		int count = 0;
		for(int i=0; i<latLng.size();i++) {
			if (path.contains(latLng.get(i).lng, latLng.get(i).lat)) count++;
		}
		assertThat(count).isEqualTo(6);
		
/*		
		 * 
[0]	LatLng  (id=90)	
	lat	52.02704	
	lng	5.46081	
[1]	LatLng  (id=93)	
	lat	52.02704	
	lng	5.48201	
[2]	LatLng  (id=94)	
	lat	52.03264	
	lng	5.487590	
[3]	LatLng  (id=95)	
	lat	52.03522	
	lng	5.478750	
[4]	LatLng  (id=96)	
	lat	52.03221	
	lng	5.466650	
[5]	LatLng  (id=97)	
	lat	52.02704	
	lng	5.46081	


punt in      "mlr|Hiwk`@pk@wQxv@aG"
[0]	LatLng  (id=91)	
	lat	52.03671000000001	
	lng	5.47205	
[1]	LatLng  (id=94)	
	lat	52.02958	
	lng	5.47505	
[2]	LatLng  (id=95)	
	lat	52.02065	
	lng	5.47634	

geen punt in "mlr|Hiwk`@jcByY"
[0]	LatLng  (id=91)	
	lat	52.03671000000001	
	lng	5.47205	
[2]	LatLng  (id=95)	
	lat	52.02065	
	lng	5.47634	
	
naast        "chr|H_po`@bu@bf@fk@tt@"
[0]	LatLng  (id=110)	
	lat	52.03602000000001	
	lng	5.49136	
[1]	LatLng  (id=111)	
	lat	52.02736	
	lng	5.4851	
[2]	LatLng  (id=112)	
	lat	52.02028000000001	
	lng	5.47651	

		 */

		
	
			
		assertThat(path.contains(5.47505, 52.02958)).isTrue();
		assertThat(path.contains(5.47205, 52.03671)).isFalse();
		
//		Line2D.Double route = new Line2D.Double(5.47205, 52.03671,5.47505, 52.02958);
		Path2D.Double route = new Path2D.Double();
		route.moveTo(5.47205, 52.03671);
	//	route.lineTo(5.47505, 52.02958);
		route.lineTo(5.47634, 52.02065);
		route.lineTo(5.47634, 52.02065000001);

		Area area = new Area(path);
		area.intersect(new Area(route));
		assertThat(area.isEmpty()).isFalse();

		route = new Path2D.Double();
		route.moveTo(5.49136, 52.03602);
		route.lineTo(5.4851, 52.02736);
		route.lineTo(5.47651, 52.02028);

		area = new Area(path);
		area.intersect(new Area(route));
		assertThat(area.isEmpty()).isTrue();
		
		route = new Path2D.Double();
		route.moveTo(5.47651, 52.02028);
		route.lineTo(5.4851, 52.02736);
		route.lineTo(5.49136, 52.03602);
		route.lineTo(5.45900, 52.03644);

		area = new Area(path);
		area.intersect(new Area(route));
		assertThat(area.isEmpty()).isTrue();
		
		
		Rectangle2D.Double rd = new Rectangle2D.Double(10, 10, 2, 2);
		assertThat(rd.outcode(10, 10)).isEqualTo(0);
		assertThat(rd.outcode(10, 12)).isEqualTo(0);
		assertThat(rd.outcode(12, 10)).isEqualTo(0);
		assertThat(rd.outcode(12, 12)).isEqualTo(0);
		assertThat(rd.outcode( 9,  9)).isEqualTo(Rectangle2D.OUT_LEFT|Rectangle2D.OUT_TOP);
		assertThat(rd.outcode( 9, 11)).isEqualTo(Rectangle2D.OUT_LEFT);
		assertThat(rd.outcode( 9, 13)).isEqualTo(Rectangle2D.OUT_LEFT|Rectangle2D.OUT_BOTTOM);
		assertThat(rd.outcode(11,  9)).isEqualTo(Rectangle2D.OUT_TOP);
		assertThat(rd.outcode(11, 11)).isEqualTo(0);
		assertThat(rd.outcode(11, 13)).isEqualTo(Rectangle2D.OUT_BOTTOM);
		assertThat(rd.outcode(13,  9)).isEqualTo(Rectangle2D.OUT_RIGHT|Rectangle2D.OUT_TOP);
		assertThat(rd.outcode(13, 11)).isEqualTo(Rectangle2D.OUT_RIGHT);
		assertThat(rd.outcode(13, 13)).isEqualTo(Rectangle2D.OUT_RIGHT|Rectangle2D.OUT_BOTTOM);
		;
	}
}
