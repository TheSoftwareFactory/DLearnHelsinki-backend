package org.dlearn.helsinki.skeleton.model;

public class NewTeacher {
    public Teacher teacher;
    public String password;

    public NewTeacher(Teacher teacher, String password) {
        this.teacher = teacher;
        this.password = password;
    }

    @Override
    public String toString() {
        return "NewTeacher{" + "teacher=" + teacher + '}';
    }

}
