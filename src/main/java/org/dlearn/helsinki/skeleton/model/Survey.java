package org.dlearn.helsinki.skeleton.model;

import java.sql.Date;

public class Survey {
	
	public int _id;
	public String title;
	public String description;
	public String start_date;
	public String end_date;
	public int teacher_id;
	public int class_id;
	public boolean open;
	
	public Survey(){
		super();
	}

	public Survey(int _id, String title, String description, String start_date, String end_date, int teacher_id, int class_id,
			boolean open) {
		super();
		this._id = _id;
		this.title = title;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.teacher_id = teacher_id;
		this.class_id = class_id;
		this.open = open;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getClass_id() {
		return class_id;
	}

	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	
}
