package org.dlearn.helsinki.skeleton.model;

import java.util.Date;

public class GroupThemeAverage {
	
	private int survey_id;
	private String start_date;
	private int theme_id;
	private String theme_title;
	private String description;
	private int group_id;
	private float answer;
	
	public GroupThemeAverage(int survey_id, String start_date, int theme_id, String theme_title, String description,
			int group_id, float answer) {
		super();
		this.survey_id = survey_id;
		this.start_date = start_date;
		this.theme_id = theme_id;
		this.theme_title = theme_title;
		this.description = description;
		this.group_id = group_id;
		this.answer = answer;
	}

	public GroupThemeAverage() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
