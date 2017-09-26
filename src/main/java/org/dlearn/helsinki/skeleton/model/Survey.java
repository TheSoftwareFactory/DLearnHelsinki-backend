package org.dlearn.helsinki.skeleton.model;

import java.sql.Date;

public class Survey {
	public int _id;
	public String name;
	public int group_id;
	public String start_date;
	public String end_date;
	public int teacher_id;
	
	public Survey(int _id, String name, int group_id, String start_date,
			String end_date, int teacher_id) {
		super();
		this._id = _id;
		this.name = name;
		this.group_id = group_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.teacher_id = teacher_id;
	}
	
	public Survey(){
		super();
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
	
	
}
