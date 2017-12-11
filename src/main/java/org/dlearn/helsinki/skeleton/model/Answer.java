package org.dlearn.helsinki.skeleton.model;

public class Answer {

    public int survey_id;
    public int question_id;
    public int student_id;
    public int answer;
    public int group_id;

    public Answer() {
    }

    public Answer(int survey_id, int question_id, int student_id, int answer) {
        this.survey_id = survey_id;
        this.question_id = question_id;
        this.student_id = student_id;
        this.answer = answer;
    }

    public Answer(int survey_id, int question_id, int student_id, int answer,
            int group_id) {
        this.survey_id = survey_id;
        this.question_id = question_id;
        this.student_id = student_id;
        this.answer = answer;
        this.group_id = group_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return "Answer{ survey_id = " + survey_id + ", question_id = "
                + question_id + ", student_id = " + student_id + ", answer = "
                + answer + ", group_id = " + group_id + " }";
    }

}
