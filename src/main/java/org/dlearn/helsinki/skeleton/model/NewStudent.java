package org.dlearn.helsinki.skeleton.model;

public class NewStudent {
    public Student student;
    public String password;
    public int class_id;
    public int group_id;

    @Override
    public String toString() {
        return "NewStudent{" + "student=" + student + ", class_id=" + class_id + ", group_id=" + group_id + '}';
    }

	public NewStudent setGroup_id(int group_id){
		this.group_id = group_id;
		return this;
	}

	public NewStudent setClass_id(int class_id){
		this.class_id = class_id;
		return this;
	}    
}
