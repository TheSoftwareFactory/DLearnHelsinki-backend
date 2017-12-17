package org.dlearn.helsinki.skeleton.model;

public class ThemeAverage {

    private int survey_id;
    private String start_date;
    private int theme_id;
    private String theme_title;
    private String theme_title_fi;
    private String description;
    private String description_fi;
    private int student_id;
    private int class_id;
    private int group_id;
    private float answer;

    public ThemeAverage() {
    }

    public ThemeAverage(int survey_id, String start_date, int theme_id,
            String theme_title, String theme_title_fi, String description,
            String description_fi, int student_id, int class_id, int group_id,
            float answer) {
        this.survey_id = survey_id;
        this.start_date = start_date;
        this.theme_id = theme_id;
        this.theme_title = theme_title;
        this.theme_title_fi = theme_title_fi;
        this.description = description;
        this.description_fi = description_fi;
        this.student_id = student_id;
        this.class_id = class_id;
        this.group_id = group_id;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ThemeAverage { " + "survey_id = " + survey_id
                + ", start_date = " + start_date + ", theme_id = " + theme_id
                + ", theme_title = " + theme_title + ", description = "
                + description + ", student_id = " + student_id + ", group_id = "
                + group_id + ", class_id = " + class_id + ", answer = " + answer
                + " }";
    }

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
    }

    public String getTheme_title() {
        return theme_title;
    }

    public void setTheme_title(String theme_title) {
        this.theme_title = theme_title;
    }

    public String getTheme_title_fi() {
        return theme_title_fi;
    }

    public void setTheme_title_fi(String theme_title_fi) {
        this.theme_title_fi = theme_title_fi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_fi() {
        return description_fi;
    }

    public void setDescription_fi(String description_fi) {
        this.description_fi = description_fi;
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

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public float getAnswer() {
        return answer;
    }

    public void setAnswer(float answer) {
        this.answer = answer;
    }
}
