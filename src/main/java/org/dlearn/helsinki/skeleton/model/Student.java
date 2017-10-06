package org.dlearn.helsinki.skeleton.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

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

    public Student set_id(int _id) {
        this._id = _id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Student setUsername(String username) {
        this.username = username;
        return this;
    }
    
    public Student setAge(int age) {
        this.age = age;
        return this;
    }
    
    public int getAge() {
        return age;
    }
    
    public Student setGender(String gender) {
        this.gender = gender;
        return this;
    }
    
    public String getGender() {
        return gender;
    }
}
