package org.dlearn.helsinki.skeleton.model;

public class Teacher {

	public int _id;
    public String username;
    public String pwd;

    public Teacher() {
        super();
    }


    public Teacher(int _id, String username,
            String password) {
        super();
        this._id = _id;
        this.username = username;
        this.pwd = password;
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

    public String getPassword() {
        return pwd;
    }

    public void setPassword(String password) {
        this.pwd = password;
    }
}
