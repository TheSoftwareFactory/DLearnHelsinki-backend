package org.dlearn.helsinki.skeleton.model;

import java.util.ArrayList;
import java.util.List;

public class  NewStudentGroup {

    public int _id;
    public int class_id;
    public String name;
    public List<NewStudent> students;

    public NewStudentGroup() {
        students = new ArrayList<NewStudent>();
    }

    public NewStudentGroup(int _id, int class_id, String name, List<NewStudent> students) {
        super();
        this._id = _id;
        this.class_id = class_id;
        this.name = name;
        this.students = students;
    }
    
    @Override
    public String toString() {
        return "StudentGroup { _id = "+_id+", name = "+name+", students = "+students.toString()+" }";
    }

	public Group getGroup() {
		return null;//new Group(_id, name, class_id) {};
	}
}
