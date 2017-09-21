package org.dlearn.helsinki.skeleton.model;

public class Student {

    public int _id;
    public String lastname;
    public String firstname;
    public String username;
    public String password;

    public Student() {
        super();
    }

    public Student(int _id, String lastname, String firstname, String username,
            String password) {
        super();
        this._id = _id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.password = password;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
