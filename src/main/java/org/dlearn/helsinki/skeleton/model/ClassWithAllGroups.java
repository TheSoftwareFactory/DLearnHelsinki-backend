package org.dlearn.helsinki.skeleton.model;

import java.util.ArrayList;
import java.util.List;

public class ClassWithAllGroups {

    private int _id;
    private String name;
    private String name_fi;
    private int teacher_id;
    List<Group> groups = null;

    public ClassWithAllGroups() {
        groups = new ArrayList<Group>();
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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void setFields(Classes classes) {
        this.name = classes.getName();
        this.name_fi = classes.getName_fi();
        this._id = classes.get_id();
        this.teacher_id = classes.getTeacher_id();
    }

    @Override
    public String toString() {
        return "ClassWithAllGroups{ _id = " + _id + ", name = " + name
                + ", teacher_id = " + teacher_id + ", groups = "
                + groups.toString() + " }";
    }
}
