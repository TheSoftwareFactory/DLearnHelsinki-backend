package org.dlearn.helsinki.skeleton.model;

import java.util.ArrayList;
import java.util.List;

public class StudentHistory {

    private int survey_id;
    private String survey_name;
    private String survey_description;
    private int start_date;
    private int end_date;
    private List<StudentThemeAverage> themeAverages;

    public StudentHistory() {
        themeAverages = new ArrayList<>();
    }

    public StudentHistory(int survey_id, String survey_name, String survey_description, int start_date, int end_date,
            List<StudentThemeAverage> themeAverages) {
        super();
        this.survey_id = survey_id;
        this.survey_name = survey_name;
        this.survey_description = survey_description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.themeAverages = themeAverages;
    }

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public String getSurvey_name() {
        return survey_name;
    }

    public void setSurvey_name(String survey_name) {
        this.survey_name = survey_name;
    }

    public String getSurvey_description() {
        return survey_description;
    }

    public void setSurvey_description(String survey_description) {
        this.survey_description = survey_description;
    }

    public int getStart_date() {
        return start_date;
    }

    public void setStart_date(int start_date) {
        this.start_date = start_date;
    }

    public int getEnd_date() {
        return end_date;
    }

    public void setEnd_date(int end_date) {
        this.end_date = end_date;
    }

    public List<StudentThemeAverage> getThemeAverages() {
        return themeAverages;
    }

    public void setThemeAverages(List<StudentThemeAverage> themeAverages) {
        this.themeAverages = themeAverages;
    }

    @Override
    public String toString() {
        return "StudentHistory { survey_id = "+survey_id+", survey_name = "+survey_name+", survey_description = "+survey_description+", start_date = "+start_date+", end_date = "+end_date+", themeAverages = "+themeAverages.toString()+"  }";
    }
}
