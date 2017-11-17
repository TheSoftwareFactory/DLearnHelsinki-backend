package org.dlearn.helsinki.skeleton.model;

public class ClassThemeAverage {

    private int survey_id;
    private String start_date;
    private int theme_id;
    private String theme_title;
    private String theme_title_fi;
    private String description;
    private String description_fi;
    private int class_id;
    private float answer;

    public ClassThemeAverage() {
        super();
    }

    public ClassThemeAverage(int survey_id, String start_date, int theme_id,
            String theme_title, String theme_title_fi, String description,
            String description_fi, int class_id, float answer) {
        super();
        this.survey_id = survey_id;
        this.start_date = start_date;
        this.theme_id = theme_id;
        this.theme_title = theme_title;
        this.theme_title_fi = theme_title_fi;
        this.description = description;
        this.description_fi = description_fi;
        this.class_id = class_id;
        this.answer = answer;
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

    public String getTheme_title_fi() {
        return theme_title_fi;
    }

    public void setTheme_title(String theme_title) {
        this.theme_title = theme_title;
    }

    public void setTheme_title_fi(String theme_title) {
        this.theme_title_fi = theme_title;
    }

    public String getDescription() {
        return description;
    }

    public String getDescription_fi() {
        return description_fi;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescription_fi(String description) {
        this.description_fi = description;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public float getAnswer() {
        return answer;
    }

    public void setAnswer(float answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ClassThemeAverage{ survey_id = " + survey_id + ", start_date = "
                + start_date + ", theme_id = " + theme_id + ", theme_title = "
                + theme_title + ", description = " + description
                + ", class_id = " + class_id + ", answer = " + answer + " }";
    }

}
