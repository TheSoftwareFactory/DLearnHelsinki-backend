package org.dlearn.helsinki.skeleton.model;

public class StudentThemeAverage {

	private int survey_id;
	private String start_date;
	private int theme_id;
	private String theme_title;
	private String description;
	private int student_id;
	private float answer;
	
	public StudentThemeAverage() {
		super();
	}

	public StudentThemeAverage(int survey_id, String start_date, int theme_id, String theme_title, String description,
			int student_id, float answer) {
		super();
		this.survey_id = survey_id;
		this.start_date = start_date;
		this.theme_id = theme_id;
		this.theme_title = theme_title;
		this.description = description;
		this.student_id = student_id;
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

	public void setTheme_title(String theme_title) {
		this.theme_title = theme_title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public float getAnswer() {
		return answer;
	}

	public void setAnswer(float answer) {
		this.answer = answer;
	}
	
	
}
