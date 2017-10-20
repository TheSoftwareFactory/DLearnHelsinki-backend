package org.dlearn.helsinki.skeleton.model;

import java.util.ArrayList;
import java.util.List;

public class StudentGroup {

    public int _id;
    public String name;
    public int class_id;
    public boolean open;
    public List<Student> students;

    public StudentGroup() {
    	super();
        students = new ArrayList<Student>();
    }

    public StudentGroup(int _id, String name, int class_id, boolean open) {
        super();
        this._id = _id;
        this.name = name;
        this.class_id = class_id;
        this.open = open;
        students = new ArrayList<Student>();
    }
    
    @Override
    public String toString() {
        return "StudentGroup { _id = "+_id+", name = "+name+", students = "+students.toString()+" }";
    }
}
