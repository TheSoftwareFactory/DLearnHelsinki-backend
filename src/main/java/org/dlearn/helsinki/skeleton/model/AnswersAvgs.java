package org.dlearn.helsinki.skeleton.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement()
@XmlType(name = "response", propOrder = { "student", "survey", "results" })
public class AnswersAvgs {

    private Survey survey;
    private Student student;
    public final List<AverageAnswer> results;

    public AnswersAvgs() {
        results = new ArrayList<>();
    }

    public Survey getSurvey() {
        return survey;
    }

    public List getResults() {
        return results;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public void addAverage(int q_id, int t_id, String q, float avg) {
        AverageAnswer an = new AverageAnswer();
        an.setQuestion_id(q_id);
        an.setTheme_id(t_id);
        an.setQuestion(q);
        an.setAverage(avg);
        results.add(an);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}

class AverageAnswer {

    protected int question_id;
    protected int theme_id;
    protected String question;
    protected float average;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
