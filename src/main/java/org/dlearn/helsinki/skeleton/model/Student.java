package org.dlearn.helsinki.skeleton.model;

public class Student {

    public int _id;
    public String username;
    public String pwd;
    public String gender;
    public int age;

    public Student() {
        super();
    }

    public Student(int _id, String username, String pwd, String gender, int age) {
		super();
		this._id = _id;
		this.username = username;
		this.pwd = pwd;
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

    public String getPassword() {
        return pwd;
    }

    public void setPassword(String password) {
        this.pwd = password;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getGender() {
        return gender;
    }
    
    
}
