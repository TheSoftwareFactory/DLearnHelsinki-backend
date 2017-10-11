package org.dlearn.helsinki.skeleton.model;

import java.util.ArrayList;
import java.util.List;

public class StudentGroup {
	
	public int _id;
	public String name;
	public List<Student> students;
	
	public StudentGroup(){
		students = new ArrayList<Student>();
	}

	public StudentGroup(int _id, String name, List<Student> students) {
		super();
		this._id = _id;
		this.name = name;
		this.students = students;
	}
}
