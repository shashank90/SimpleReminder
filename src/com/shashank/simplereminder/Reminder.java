package com.shashank.simplereminder;

public class Reminder {
	
	Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public Integer getRandomInt() {
		return randomInt;
	}
	public void setRandomInt(Integer randomInt) {
		this.randomInt = randomInt;
	}
	String Note;
	String Date;
	String Time;
	String AlarmType;
	String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAlarmType() {
		return AlarmType;
	}
	public void setAlarmType(String alarmType) {
		AlarmType = alarmType;
	}
	Integer randomInt;
	
	
}
