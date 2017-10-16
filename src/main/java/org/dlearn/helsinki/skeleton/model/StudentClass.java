package org.dlearn.helsinki.skeleton.model;

public class StudentClass {

    private int _id;
    private int student_id;
    private int class_id;
    private int group_id;

    public StudentClass() {
        super();
    }

    public StudentClass(int _id, int student_id, int class_id, int group_id) {
        super();
        this._id = _id;
        this.student_id = student_id;
        this.class_id = class_id;
        this.group_id = group_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

}
