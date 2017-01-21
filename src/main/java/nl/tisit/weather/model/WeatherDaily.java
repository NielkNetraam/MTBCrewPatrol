package nl.tisit.weather.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "weather_daily")
public class WeatherDaily {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer stn; //     LON(east)   LAT(north)     ALT(m)  NAME
	private Integer yyyymmdd; // Datum (YYYY; //jaar MM; //maand DD; //dag); 
	private Integer ddvec; // Vectorgemiddelde windrichting in graden (360; noord, 90; oost, 180; zuid, 270; west, 0; windstil/variabel). 
	private Integer fhvec; // Vectorgemiddelde windsnelheid (in 0.1 m/s). 
	private Integer fg; // Etmaalgemiddelde windsnelheid (in 0.1 m/s); 
	private Integer fhx; // Hoogste uurgemiddelde windsnelheid (in 0.1 m/s); 
	private Integer fhxh; // Uurvak waarin FHX is gemeten; 
	private Integer fhn; // Laagste uurgemiddelde windsnelheid (in 0.1 m/s); 
	private Integer fhnh; // Uurvak waarin FHN is gemeten; 
	private Integer fxx; // Hoogste windstoot (in 0.1 m/s); 
	private Integer fxxh; // Uurvak waarin FXX is gemeten; 
	private Integer tg; // Etmaalgemiddelde temperatuur (in 0.1 graden Celsius); 
	private Integer tn; // Minimum temperatuur (in 0.1 graden Celsius); 
	private Integer tnh; // Uurvak waarin TN is gemeten; 
	private Integer tx; // Maximum temperatuur (in 0.1 graden Celsius); 
	private Integer txh; // Uurvak waarin TX is gemeten; 
	private Integer t10n; // Minimum temperatuur op 10 cm hoogte (in 0.1 graden Celsius); 
	private Integer t10nh; // 6-uurs tijdvak waarin T10N is gemeten; 6; 0-6 UT, 12; 6-12 UT, 18; 12-18 UT, 24; 18-24 UT
	private Integer sq; // Zonneschijnduur (in 0.1 uur) berekend uit de globale straling (-1 voor <0.05 uur); 
	private Integer sp; // Percentage van de langst mogelijke zonneschijnduur; 
	private Integer q; // Globale straling (in J/cm2); 
	private Integer dr; // Duur van de neerslag (in 0.1 uur); 
	private Integer rh; // Etmaalsom van de neerslag (in 0.1 mm) (-1 voor <0.05 mm); 
	private Integer rhx; // Hoogste uursom van de neerslag (in 0.1 mm) (-1 voor <0.05 mm); 
	private Integer rhxh; // Uurvak waarin RHX is gemeten; 
	private Integer pg; // Etmaalgemiddelde luchtdruk herleid tot zeeniveau (in 0.1 hPa) berekend uit 24 uurwaarden; 
	private Integer px; // Hoogste uurwaarde van de luchtdruk herleid tot zeeniveau (in 0.1 hPa); 
	private Integer pxh; // Uurvak waarin PX is gemeten; 
	private Integer pn; // Laagste uurwaarde van de luchtdruk herleid tot zeeniveau (in 0.1 hPa); 
	private Integer pnh; // Uurvak waarin PN is gemeten; 
	private Integer vvn; // Minimum opgetreden zicht; 0: <100 m, 1:100-200 m, 2:200-300 m,..., 49:4900-5000 m, 50:5-6 km, 56:6-7 km, 57:7-8 km,..., 79:29-30 km, 80:30-35 km, 81:35-40 km,..., 89: >70 km)
	private Integer vvnh; // Uurvak waarin VVN is gemeten; 
	private Integer vvx; // Maximum opgetreden zicht; 0: <100 m, 1:100-200 m, 2:200-300 m,..., 49:4900-5000 m, 50:5-6 km, 56:6-7 km, 57:7-8 km,..., 79:29-30 km, 80:30-35 km, 81:35-40 km,..., 89: >70 km)
	private Integer vvxh; // Uurvak waarin VVX is gemeten; 
	private Integer ng; // Etmaalgemiddelde bewolking (bedekkingsgraad van de bovenlucht in achtsten, 9; //bovenlucht onzichtbaar); 
	private Integer ug; // Etmaalgemiddelde relatieve vochtigheid (in procenten); 
	private Integer ux; // Maximale relatieve vochtigheid (in procenten); 
	private Integer uxh; // Uurvak waarin UX is gemeten; 
	private Integer un; // Minimale relatieve vochtigheid (in procenten); 
	private Integer unh; // Uurvak waarin UN is gemeten; 
	private Integer ev24; // Referentiegewasverdamping (Makkink) (in 0.1 mm); 
		
	public WeatherDaily() {
		super();
	}
	
	public LocalDate getDate() {
		return LocalDate.parse(yyyymmdd.toString(), DateTimeFormatter.BASIC_ISO_DATE);
	}
	
	public WeatherDaily(String parts[]) {
		super();
		int i = 0;
		stn = Integer.parseInt(parts[i++].trim());
		yyyymmdd = Integer.parseInt(parts[i++].trim()); 
		try { ddvec = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { ddvec = 0; }
		try { fhvec = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { fhvec = 0; }
		try { fg = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { fg = 0; }
		try { fhx = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { fhx = 0; }
		try { fhxh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { fhxh = 0; }
		try { fhn = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { fhn = 0; }
		try { fhnh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { fhnh = 0; }
		try { fxx = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { fxx = 0; }
		try { fxxh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { fxxh = 0; }
		try { tg = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { tg = 0; }
		try { tn = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { tn = 0; }
		try { tnh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { tnh = 0; }
		try { tx = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { tx = 0; }
		try { txh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { txh = 0; }
		try { t10n = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { t10n = 0; }
		try { t10nh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { t10nh = 0; }
		try { sq = Integer.parseInt(parts[i++].trim());  } catch (NumberFormatException e)  { sq = 0; }
		try { sp = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { sp = 0; }
		try { q = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { q = 0; }
		try { dr = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { dr = 0; }
		try { rh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { rh = 0; }
		try { rhx = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { rhx = 0; }
		try { rhxh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { rhxh = 0; }
		try { pg = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { pg = 0; }
		try { px = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { px = 0; }
		try { pxh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { pxh = 0; }
		try { pn = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { pn = 0; }
		try { pnh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { pnh = 0; }
		try { vvn = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { vvn = 0; }
		try { vvnh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { vvnh = 0; }
		try { vvx = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { vvx = 0; }
		try { vvxh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { vvxh = 0; }
		try { ng = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { ng = 0; }
		try { ug = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { ug = 0; }
		try { ux = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { ux = 0; }
		try { uxh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { uxh = 0; }
		try { un = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { un = 0; }
		try { unh = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { unh = 0; }
		try { ev24 = Integer.parseInt(parts[i++].trim()); } catch (NumberFormatException e)  { ev24 = 0; }
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

	public Integer getDdvec() {
		return ddvec;
	}

	public void setDdvec(Integer ddvec) {
		this.ddvec = ddvec;
	}

	public Integer getFhvec() {
		return fhvec;
	}

	public void setFhvec(Integer fhvec) {
		this.fhvec = fhvec;
	}

	public Integer getFg() {
		return fg;
	}

	public void setFg(Integer fg) {
		this.fg = fg;
	}

	public Integer getFhx() {
		return fhx;
	}

	public void setFhx(Integer fhx) {
		this.fhx = fhx;
	}

	public Integer getFhxh() {
		return fhxh;
	}

	public void setFhxh(Integer fhxh) {
		this.fhxh = fhxh;
	}

	public Integer getFhn() {
		return fhn;
	}

	public void setFhn(Integer fhn) {
		this.fhn = fhn;
	}

	public Integer getFhnh() {
		return fhnh;
	}

	public void setFhnh(Integer fhnh) {
		this.fhnh = fhnh;
	}

	public Integer getFxx() {
		return fxx;
	}

	public void setFxx(Integer fxx) {
		this.fxx = fxx;
	}

	public Integer getFxxh() {
		return fxxh;
	}

	public void setFxxh(Integer fxxh) {
		this.fxxh = fxxh;
	}

	public Integer getTg() {
		return tg;
	}

	public void setTg(Integer tg) {
		this.tg = tg;
	}

	public Integer getTn() {
		return tn;
	}

	public void setTn(Integer tn) {
		this.tn = tn;
	}

	public Integer getTnh() {
		return tnh;
	}

	public void setTnh(Integer tnh) {
		this.tnh = tnh;
	}

	public Integer getTx() {
		return tx;
	}

	public void setTx(Integer tx) {
		this.tx = tx;
	}

	public Integer getTxh() {
		return txh;
	}

	public void setTxh(Integer txh) {
		this.txh = txh;
	}

	public Integer getT10n() {
		return t10n;
	}

	public void setT10n(Integer t10n) {
		this.t10n = t10n;
	}

	public Integer getT10nh() {
		return t10nh;
	}

	public void setT10nh(Integer t10nh) {
		this.t10nh = t10nh;
	}

	public Integer getSq() {
		return sq;
	}

	public void setSq(Integer sq) {
		this.sq = sq;
	}

	public Integer getSp() {
		return sp;
	}

	public void setSp(Integer sp) {
		this.sp = sp;
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

	public Integer getRhx() {
		return rhx;
	}

	public void setRhx(Integer rhx) {
		this.rhx = rhx;
	}

	public Integer getRhxh() {
		return rhxh;
	}

	public void setRhxh(Integer rhxh) {
		this.rhxh = rhxh;
	}

	public Integer getPg() {
		return pg;
	}

	public void setPg(Integer pg) {
		this.pg = pg;
	}

	public Integer getPx() {
		return px;
	}

	public void setPx(Integer px) {
		this.px = px;
	}

	public Integer getPxh() {
		return pxh;
	}

	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public Integer getPnh() {
		return pnh;
	}

	public void setPnh(Integer pnh) {
		this.pnh = pnh;
	}

	public Integer getVvn() {
		return vvn;
	}

	public void setVvn(Integer vvn) {
		this.vvn = vvn;
	}

	public Integer getVvnh() {
		return vvnh;
	}

	public void setVvnh(Integer vvnh) {
		this.vvnh = vvnh;
	}

	public Integer getVvx() {
		return vvx;
	}

	public void setVvx(Integer vvx) {
		this.vvx = vvx;
	}

	public Integer getVvxh() {
		return vvxh;
	}

	public void setVvxh(Integer vvxh) {
		this.vvxh = vvxh;
	}

	public Integer getNg() {
		return ng;
	}

	public void setNg(Integer ng) {
		this.ng = ng;
	}

	public Integer getUg() {
		return ug;
	}

	public void setUg(Integer ug) {
		this.ug = ug;
	}

	public Integer getUx() {
		return ux;
	}

	public void setUx(Integer ux) {
		this.ux = ux;
	}

	public Integer getUxh() {
		return uxh;
	}

	public void setUxh(Integer uxh) {
		this.uxh = uxh;
	}

	public Integer getUn() {
		return un;
	}

	public void setUn(Integer un) {
		this.un = un;
	}

	public Integer getUnh() {
		return unh;
	}

	public void setUnh(Integer unh) {
		this.unh = unh;
	}

	public Integer getEv24() {
		return ev24;
	}

	public void setEv24(Integer ev24) {
		this.ev24 = ev24;
	}

	@Override
	public String toString() {
		return "WeatherDaily [id=" + id + ", stn=" + stn + ", yyyymmdd=" + yyyymmdd + ", ddvec=" + ddvec + ", fhvec=" + fhvec
				+ ", fg=" + fg + ", fhx=" + fhx + ", fhxh=" + fhxh + ", fhn=" + fhn + ", fhnh=" + fhnh + ", fxx=" + fxx
				+ ", fxxh=" + fxxh + ", tg=" + tg + ", tn=" + tn + ", tnh=" + tnh + ", tx=" + tx + ", txh=" + txh
				+ ", t10n=" + t10n + ", t10nh=" + t10nh + ", sq=" + sq + ", sp=" + sp + ", q=" + q + ", dr=" + dr
				+ ", rh=" + rh + ", rhx=" + rhx + ", rhxh=" + rhxh + ", pg=" + pg + ", px=" + px + ", pxh=" + pxh
				+ ", pn=" + pn + ", pnh=" + pnh + ", vvn=" + vvn + ", vvnh=" + vvnh + ", vvx=" + vvx + ", vvxh=" + vvxh
				+ ", ng=" + ng + ", ug=" + ug + ", ux=" + ux + ", uxh=" + uxh + ", un=" + un + ", unh=" + unh
				+ ", ev24=" + ev24 + "]";
	}

}
