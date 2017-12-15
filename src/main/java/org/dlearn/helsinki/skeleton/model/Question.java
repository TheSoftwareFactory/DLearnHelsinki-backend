package org.dlearn.helsinki.skeleton.model;

public class Question {

    public int _id;
    public String question;
    public String question_fi;
    public int min_answer;
    public int max_answer;
    public int theme_id;

    public Question() {
    }

    public Question(int _id, String question, String question_fi,
            int min_answer, int max_answer) {
        super();
        this._id = _id;
        this.question = question;
        this.question_fi = question_fi;
        this.min_answer = min_answer;
        this.max_answer = max_answer;
    }
    
    public Question(int _id, String question, String question_fi,
            int min_answer, int max_answer, int theme_id) {
        super();
        this._id = _id;
        this.question = question;
        this.question_fi = question_fi;
        this.min_answer = min_answer;
        this.max_answer = max_answer;
        this.theme_id = theme_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_theme_id() {
        return theme_id;
    }

    public void set_theme_id(int _id) {
        this.theme_id = _id;
    }
    
    public String getQuestion() {
        return question;
    }

    public String getQuestion_fi() {
        return question_fi;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestion_fi(String question) {
        this.question_fi = question;
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

    @Override
    public String toString() {
        return "Question{" + "_id=" + _id + ", question=" + question + " : "
                + question_fi + ", min_answer=" + min_answer + ", max_answer="
                + max_answer + '}';
    }

}
