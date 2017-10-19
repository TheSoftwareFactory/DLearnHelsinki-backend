package org.dlearn.helsinki.skeleton.model;

public class Classes {

    private int _id;
    private String name;
    private int teacher_id;

    public Classes() {
    }

    public Classes(int _id, String name, int teacher_id) {
        super();
        this._id = _id;
        this.name = name;
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

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
    
    @Override
    public String toString() {
        return "Classes{ _id = "+_id+", name = "+name+", teacher_id = "+teacher_id+" }";
    }

}
