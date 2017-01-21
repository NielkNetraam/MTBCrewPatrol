package nl.tisit.weather.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "weather_hourly")
public class WeatherHourly {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer stn; //     LON(east)   LAT(north)     ALT(m)  NAME
	private Integer yyyymmdd; // Datum (YYYY; //jaar MM; //maand DD; //dag); 
	private Integer hh; // tijd (HH=uur, UT.12 UT=13 MET, 14 MEZT. Uurvak 05 loopt van 04.00 UT tot 5.00 UT 
	private Integer dd; //       = Windrichting (in graden) gemiddeld over de laatste 10 minuten van het afgelopen uur (360=noord, 90=oost, 180=zuid, 270=west, 0=windstil 990=veranderlijk. Zie http://www.knmi.nl/kennis-en-datacentrum/achtergrond/klimatologische-brochures-en-boeken; 
	private Integer fh; //       = Uurgemiddelde windsnelheid 
	private Integer ff; //       = Windsnelheid (in 0.1 m/s) gemiddeld over de laatste 10 minuten van het afgelopen uur; 
	private Integer fx; //       = Hoogste windstoot (in 0.1 m/s) over het afgelopen uurvak; 
	private Integer t; //        = Temperatuur (in 0.1 graden Celsius) op 1.50 m hoogte tijdens de waarneming; 
	private Integer t10n; //     = Minimumtemperatuur (in 0.1 graden Celsius) op 10 cm hoogte in de afgelopen 6 uur; 
	private Integer td; //       = Dauwpuntstemperatuur (in 0.1 graden Celsius) op 1.50 m hoogte tijdens de waarneming; 
	private Integer sq; //       = Duur van de zonneschijn (in 0.1 uren) per uurvak, berekend uit globale straling  (-1 for <0.05 uur); 
	private Integer q; //        = Globale straling (in J/cm2) per uurvak; 
	private Integer dr; //       = Duur van de neerslag (in 0.1 uur) per uurvak; 
	private Integer rh; //       = Uursom van de neerslag (in 0.1 mm) (-1 voor <0.05 mm); 
	private Integer p; //        = Luchtdruk (in 0.1 hPa) herleid naar zeeniveau, tijdens de waarneming; 
	private Integer vv; //       = Horizontaal zicht tijdens de waarneming (0=minder dan 100m, 1=100-200m, 2=200-300m,..., 49=4900-5000m, 50=5-6km, 56=6-7km, 57=7-8km, ..., 79=29-30km, 80=30-35km, 81=35-40km,..., 89=meer dan 70km); 
	private Integer n; //        = Bewolking (bedekkingsgraad van de bovenlucht in achtsten), tijdens de waarneming (9=bovenlucht onzichtbaar); 
	private Integer u; //        = Relatieve vochtigheid (in procenten) op 1.50 m hoogte tijdens de waarneming; 
	private Integer ww; //       = Weercode (00-99), visueel(WW) of automatisch(WaWa) waargenomen, voor het actuele weer of het weer in het afgelopen uur. Zie http://bibliotheek.knmi.nl/scholierenpdf/weercodes_Nederland; 
	private Integer ix; //       = Weercode indicator voor de wijze van waarnemen op een bemand of automatisch station (1=bemand gebruikmakend van code uit visuele waarnemingen, 2,3=bemand en weggelaten (geen belangrijk weersverschijnsel, geen gegevens), 4=automatisch en opgenomen (gebruikmakend van code uit visuele waarnemingen), 5,6=automatisch en weggelaten (geen belangrijk weersverschijnsel, geen gegevens), 7=automatisch gebruikmakend van code uit automatische waarnemingen); 
	private Integer m; //        = Mist 0=niet voorgekomen, 1=wel voorgekomen in het voorgaande uur en/of tijdens de waarneming; 
	private Integer r; //        = Regen 0=niet voorgekomen, 1=wel voorgekomen in het voorgaande uur en/of tijdens de waarneming; 
	private Integer s; //        = Sneeuw 0=niet voorgekomen, 1=wel voorgekomen in het voorgaande uur en/of tijdens de waarneming; 
	private Integer o; //        = Onweer 0=niet voorgekomen, 1=wel voorgekomen in het voorgaande uur en/of tijdens de waarneming; 
	private Integer y; //        = IJsvorming 0=niet voorgekomen, 1=wel voorgekomen in het voorgaande uur en/of tijdens de waarneming; 

	
	public WeatherHourly() {
		super();
	}
	
	public WeatherHourly(String parts[]) {
		super();
		int i = 0;
		stn = Integer.parseInt(parts[i++].trim());
		yyyymmdd = Integer.parseInt(parts[i++].trim()); 
		hh = Integer.parseInt(parts[i++].trim()); 
		dd = Integer.parseInt(parts[i++].trim());   
		fh = Integer.parseInt(parts[i++].trim());     
		ff = Integer.parseInt(parts[i++].trim());      
		fx = Integer.parseInt(parts[i++].trim());    
		t = Integer.parseInt(parts[i++].trim());     
		try { t10n = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { t10n = null; } 
		try { td = Integer.parseInt(parts[i++].trim());   } catch (NumberFormatException e)  { td = null; }   
		try { sq = Integer.parseInt(parts[i++].trim());   } catch (NumberFormatException e)  { sq = null; }        
		try { q = Integer.parseInt(parts[i++].trim());    } catch (NumberFormatException e)  { q = null; }      
		try { dr = Integer.parseInt(parts[i++].trim());   } catch (NumberFormatException e)  { dr = 0; }     
		try { rh = Integer.parseInt(parts[i++].trim());   } catch (NumberFormatException e)  { rh = null; }     
		try { p = Integer.parseInt(parts[i++].trim());    } catch (NumberFormatException e)  { p = null; }      
		try { vv = Integer.parseInt(parts[i++].trim());   } catch (NumberFormatException e)  { vv = null; }    
		try { n = Integer.parseInt(parts[i++].trim());    } catch (NumberFormatException e)  { n = null; }            
		try { u = Integer.parseInt(parts[i++].trim());    } catch (NumberFormatException e)  { u = null; }     
		try { ww = Integer.parseInt(parts[i++].trim());   } catch (NumberFormatException e)  { ww = null; }    
		try { ix = Integer.parseInt(parts[i++].trim());   } catch (NumberFormatException e)  { ix = null; } 
		try { m = Integer.parseInt(parts[i++].trim());    } catch (NumberFormatException e)  { m = null; }   
		try { r = Integer.parseInt(parts[i++].trim());    } catch (NumberFormatException e)  { r = null; }     
		try { s = Integer.parseInt(parts[i++].trim());    } catch (NumberFormatException e)  { s = null; }         
		try { o = Integer.parseInt(parts[i++].trim());    } catch (NumberFormatException e)  { o = null; }       
		try { y = Integer.parseInt(parts[i++].trim());    } catch (NumberFormatException e)  { y = null; }          
	}

	public LocalDate getDate() {
		return LocalDate.parse(yyyymmdd.toString(), DateTimeFormatter.BASIC_ISO_DATE);
	}

	public LocalDateTime getDateTime() {
		LocalDateTime date = LocalDateTime.of(yyyymmdd/10000, ((yyyymmdd/100)%100), yyyymmdd%100, hh-1, 0).truncatedTo(ChronoUnit.HOURS);
//		date.set(yyyymmdd/10000, ((yyyymmdd/100)%100)-1, yyyymmdd%100, hh-1, 0, 0);
//
//		Date dx = Date.from(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).truncatedTo(ChronoUnit.HOURS).toInstant());
		
		return date;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStn() {
		return stn;
	}

	public void setStn(Integer stn) {
		this.stn = stn;
	}

	public Integer getYyyymmdd() {
		return yyyymmdd;
	}

	public void setYyyymmdd(Integer yyyymmdd) {
		this.yyyymmdd = yyyymmdd;
	}

	public Integer getHh() {
		return hh;
	}

	public void setHh(Integer hh) {
		this.hh = hh;
	}

	public Integer getDd() {
		return dd;
	}

	public void setDd(Integer dd) {
		this.dd = dd;
	}

	public Integer getFh() {
		return fh;
	}

	public void setFh(Integer fh) {
		this.fh = fh;
	}

	public Integer getFf() {
		return ff;
	}

	public void setFf(Integer ff) {
		this.ff = ff;
	}

	public Integer getFx() {
		return fx;
	}

	public void setFx(Integer fx) {
		this.fx = fx;
	}

	public Integer getT() {
		return t;
	}

	public void setT(Integer t) {
		this.t = t;
	}

	public Integer getT10n() {
		return t10n;
	}

	public void setT10n(Integer t10n) {
		this.t10n = t10n;
	}

	public Integer getTd() {
		return td;
	}

	public void setTd(Integer td) {
		this.td = td;
	}

	public Integer getSq() {
		return sq;
	}

	public void setSq(Integer sq) {
		this.sq = sq;
	}

	public Integer getQ() {
		return q;
	}

	public void setQ(Integer q) {
		this.q = q;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public Integer getRh() {
		return rh;
	}

	public void setRh(Integer rh) {
		this.rh = rh;
	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public Integer getVv() {
		return vv;
	}

	public void setVv(Integer vv) {
		this.vv = vv;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public Integer getU() {
		return u;
	}

	public void setU(Integer u) {
		this.u = u;
	}

	public Integer getWw() {
		return ww;
	}

	public void setWw(Integer ww) {
		this.ww = ww;
	}

	public Integer getIx() {
		return ix;
	}

	public void setIx(Integer ix) {
		this.ix = ix;
	}

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public Integer getS() {
		return s;
	}

	public void setS(Integer s) {
		this.s = s;
	}

	public Integer getO() {
		return o;
	}

	public void setO(Integer o) {
		this.o = o;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "WeatherHourly [id=" + id + ", stn=" + stn + ", yyyymmdd=" + yyyymmdd + ", hh=" + hh + ", dd=" + dd
				+ ", fh=" + fh + ", ff=" + ff + ", fx=" + fx + ", t=" + t + ", t10n=" + t10n + ", td=" + td + ", sq="
				+ sq + ", q=" + q + ", dr=" + dr + ", rh=" + rh + ", p=" + p + ", vv=" + vv + ", n=" + n + ", u=" + u
				+ ", ww=" + ww + ", ix=" + ix + ", m=" + m + ", r=" + r + ", s=" + s + ", o=" + o + ", y=" + y + "]";
	}
	


}
