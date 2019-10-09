package edu.jutlandica.client.controller;

public class StringTime {
	//Expected format 00:00 (hh:mm)
	int hour = 0;
	int minute = 0;
	
	public StringTime(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	
	public static StringTime parseStringTime(String time) {
		String[] split = time.split(":");
		int hour = Integer.parseInt(split[0]);
		int minute = Integer.parseInt(split[1]);
		return new StringTime(hour, minute);
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		s.append((hour < 10) ? "0" + hour : hour);
		s.append(":");
		s.append((minute < 10) ? "0" + minute : minute);
		
		return s.toString();
	}	
}
