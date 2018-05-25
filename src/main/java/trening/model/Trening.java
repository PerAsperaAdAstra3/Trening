package trening.model;

import java.util.Date;

public class Trening {

	private Date datum;
	private Integer brojTreninga;
	private Zadatak zadatak;
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public Integer getBrojTreninga() {
		return brojTreninga;
	}
	public void setBrojTreninga(Integer brojTreninga) {
		this.brojTreninga = brojTreninga;
	}
	public Zadatak getZadatak() {
		return zadatak;
	}
	public void setZadatak(Zadatak zadatak) {
		this.zadatak = zadatak;
	}
	public Trening(Date datum, Integer brojTreninga, Zadatak zadatak) {
		super();
		this.datum = datum;
		this.brojTreninga = brojTreninga;
		this.zadatak = zadatak;
	}
	
	
	
}
