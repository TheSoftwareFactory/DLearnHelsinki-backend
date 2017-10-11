package org.dlearn.helsinki.skeleton.model;

public class Survey_question {

    public int survey_id;
    public int question_id;

    public Survey_question(int survey_id, int question_id) {
        super();
        this.survey_id = survey_id;
        this.question_id = question_id;
    }

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

}
