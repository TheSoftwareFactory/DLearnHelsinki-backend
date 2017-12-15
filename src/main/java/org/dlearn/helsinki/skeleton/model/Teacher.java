package org.dlearn.helsinki.skeleton.model;

public class Teacher {

    public int _id;
    public String username;
    public String name;
    public String lastname;

    public Teacher() {
        super();
    }

    public Teacher(int _id, String username, String password) {
        super();
        this._id = _id;
        this.username = username;
    }

    public Teacher(int _id, String username, String name, String lastname) {
        super();
        this._id = _id;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Teacher{" + "_id=" + _id + ", username=" + username + '}';
    }

}
