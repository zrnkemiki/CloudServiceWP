package model;

import java.time.LocalDateTime;

public class Activity {
	private int id;
	private LocalDateTime dateON;
	private LocalDateTime dateOFF;

	public Activity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Activity(int id, LocalDateTime dateON, LocalDateTime dateOFF) {
		super();
		this.id = id;
		this.dateON = dateON;
		this.dateOFF = dateOFF;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDateON() {
		return dateON;
	}

	public void setDateON(LocalDateTime dateON) {
		this.dateON = dateON;
	}

	public LocalDateTime getDateOFF() {
		return dateOFF;
	}

	public void setDateOFF(LocalDateTime dateOFF) {
		this.dateOFF = dateOFF;
	}

}
