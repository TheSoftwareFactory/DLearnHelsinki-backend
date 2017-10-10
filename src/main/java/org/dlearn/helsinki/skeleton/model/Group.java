package org.dlearn.helsinki.skeleton.model;

public class Group {

    public int _id;
    public String name;
    public int class_id;

    public Group() {
    }

    public Group(int _id, String name, int class_id) {
        super();
        this._id = _id;
        this.name = name;
        this.class_id = class_id;
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
        return class_id;
    }

    public void setClass_id(int student_id) {
        this.class_id = student_id;
    }

}
