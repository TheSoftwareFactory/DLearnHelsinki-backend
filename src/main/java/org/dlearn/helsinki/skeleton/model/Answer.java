package org.dlearn.helsinki.skeleton.model;

public class Answer {

	public int question_id;
	public int student_id;
	public int survey_id;
	public int answer;
	
	public Answer(int question_id, int student_id, int survey_id, int answer) {
		super();
		this.question_id = question_id;
		this.student_id = student_id;
		this.survey_id = survey_id;
		this.answer = answer;
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
	public int getSurvey_id() {
		return survey_id;
	}
	public void setSurvey_id(int survey_id) {
		this.survey_id = survey_id;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	
	
}
