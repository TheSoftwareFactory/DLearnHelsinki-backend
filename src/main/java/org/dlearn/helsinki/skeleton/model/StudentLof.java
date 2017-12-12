package org.dlearn.helsinki.skeleton.model;

public class StudentLof extends Student {

    private int class_id;
    private double lof_score;

    public StudentLof(Student student) {
        this._id = student.get_id();
        this.username = student.getUsername();
        this.gender = student.getGender();
        this.age = student.getAge();
    }

    public int getClass_id() {
        return this.class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public double getLofScore() {
        return lof_score;
    }

    public void setLofScore(double lof_score) {
        this.lof_score = lof_score;
    }

    @Override
    public String toString() {
        return "StudentLof{" + "_id=" + _id + ", username=" + username
                + ", gender=" + gender + ", age=" + age + ", class_id="
                + class_id + ", lof_score=" + lof_score + '}';
    }
}
