package org.dlearn.helsinki.skeleton.model;

public class Student {

    public int _id;
    public String username;
    public String gender;
    public int age;

    public Student() {
        super();
    }

    public Student(int _id, String username, String gender, int age) {
        super();
        this._id = _id;
        this.username = username;
        this.gender = gender;
        this.age = age;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
