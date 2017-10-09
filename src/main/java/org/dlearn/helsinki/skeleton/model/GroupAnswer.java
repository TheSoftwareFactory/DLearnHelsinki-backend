package org.dlearn.helsinki.skeleton.model;

public class GroupAnswer {
	
	private int survey_id;
	private int question_id;
	private int group_id;
	private float answer;
	
	public GroupAnswer(){}

	public GroupAnswer(int survey_id, int question_id, int group_id, float answer) {
		super();
		this.survey_id = survey_id;
		this.question_id = question_id;
		this.group_id = group_id;
		this.answer = answer;
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
