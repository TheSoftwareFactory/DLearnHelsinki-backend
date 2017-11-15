package org.dlearn.helsinki.skeleton.model;

public class Classes {

    private int _id;
    private String name;
    private String name_fi;
    private int teacher_id;

    public Classes() {
    }

    public Classes(int _id, String name, String name_fi, int teacher_id) {
        super();
        this._id = _id;
        this.name = name;
        this.name_fi = name_fi;
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
    
    public String getName_fi() {
        return name_fi;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setName_fi(String name) {
        this.name_fi = name;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
    
    @Override
    public String toString() {
        return "Classes{ _id = "+_id+", name = "+name+", name_FI = "+name_fi+", teacher_id = "+teacher_id+" }";
    }

}
