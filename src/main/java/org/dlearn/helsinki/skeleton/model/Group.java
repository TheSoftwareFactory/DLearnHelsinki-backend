package org.dlearn.helsinki.skeleton.model;

public class Group {

	public int _id;
	public String name;
	public int student_id;
	public int teacher_id;
	
	public Group(int _id, String name, int student_id, int teacher_id) {
		super();
		this._id = _id;
		this.name = name;
		this.student_id = student_id;
		this.teacher_id = teacher_id;
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

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
	
	
}
