package org.dlearn.helsinki.skeleton.model;

public class StudentLof {

    private int student_id;
    private int class_id;
    private String name;
    private double lof_score;

    public StudentLof(int student_id, int class_id, String name,
            double lof_score) {
        this.student_id = student_id;
        this.class_id = class_id;
        this.name = name;
        this.lof_score = lof_score;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLofScore() {
        return lof_score;
    }

    public void setLofScore(double lof_score) {
        this.lof_score = lof_score;
    }

    @Override
    public String toString() {
        return "StudentLof { student_id = " + student_id + ", class_id = "
                + class_id + ", name = " + name + ", lof_score = " + lof_score
                + " }";
    }
}
