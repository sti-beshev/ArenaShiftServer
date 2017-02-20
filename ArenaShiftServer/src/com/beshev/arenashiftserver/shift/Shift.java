package com.beshev.arenashiftserver.shift;

public class Shift {
	

	private int year;
	private int month;
	private int day;
	private String panMehanik;
	private String panKasaOne;
	private String panKasaTwo;
	private String panKasaThree;
	private String razporeditelOne;
	private String razporeditelTwo;
	private String cenMehanik;
	private String cenKasa;

	public Shift() {
		
	}
	
	public Shift(int year, int month, int day, String panMehanik, String panKasaOne, String panKasaTwo,
			String panKasaThree, String razporeditelOne, String razporeditelTwo, String cenMehanik, String cenKasa) {
		
		this.year = year;
		this.month = month;
		this.day = day;
		this.panMehanik = panMehanik;
		this.panKasaOne = panKasaOne;
		this.panKasaTwo = panKasaTwo;
		this.panKasaThree = panKasaThree;
		this.razporeditelOne = razporeditelOne;
		this.razporeditelTwo = razporeditelTwo;
		this.cenMehanik = cenMehanik;
		this.cenKasa = cenKasa;
	}

	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getPanMehanik() {
		return panMehanik;
	}

	public void setPanMehanik(String panMehanik) {
		this.panMehanik = panMehanik;
	}

	public String getPanKasaOne() {
		return panKasaOne;
	}

	public void setPanKasaOne(String panKasaOne) {
		this.panKasaOne = panKasaOne;
	}

	public String getPanKasaTwo() {
		return panKasaTwo;
	}

	public void setPanKasaTwo(String panKasaTwo) {
		this.panKasaTwo = panKasaTwo;
	}

	public String getPanKasaThree() {
		return panKasaThree;
	}

	public void setPanKasaThree(String panKasaThree) {
		this.panKasaThree = panKasaThree;
	}

	public String getRazporeditelOne() {
		return razporeditelOne;
	}

	public void setRazporeditelOne(String razporeditelOne) {
		this.razporeditelOne = razporeditelOne;
	}

	public String getRazporeditelTwo() {
		return razporeditelTwo;
	}

	public void setRazporeditelTwo(String razporeditelTwo) {
		this.razporeditelTwo = razporeditelTwo;
	}

	public String getCenMehanik() {
		return cenMehanik;
	}

	public void setCenMehanik(String cenMehanik) {
		this.cenMehanik = cenMehanik;
	}

	public String getCenKasa() {
		return cenKasa;
	}

	public void setCenKasa(String cenKasa) {
		this.cenKasa = cenKasa;
	}

}
