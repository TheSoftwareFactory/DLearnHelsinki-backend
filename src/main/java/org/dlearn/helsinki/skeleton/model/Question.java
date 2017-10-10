package org.dlearn.helsinki.skeleton.model;

public class Question {

    public int _id;
    public String question;
    public int min_answer;
    public int max_answer;

    public Question() {
    }

    public Question(int _id, String question, int min_answer, int max_answer) {
        super();
        this._id = _id;
        this.question = question;
        this.min_answer = min_answer;
        this.max_answer = max_answer;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getMin_answer() {
        return min_answer;
    }

    public void setMin_answer(int min_answer) {
        this.min_answer = min_answer;
    }

    public int getMax_answer() {
        return max_answer;
    }

    public void setMax_answer(int max_answer) {
        this.max_answer = max_answer;
    }

}
