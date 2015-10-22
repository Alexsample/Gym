package com.alex.gymwork;

import java.io.Serializable;

public class Weekday implements Serializable {

	private static final long serialVersionUID = 1L;
	private String weekday;
	private int type;
	
	public Weekday(final String weekday, final int type) {
		this.weekday = weekday;
		this.type = type;
	}
	
	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
