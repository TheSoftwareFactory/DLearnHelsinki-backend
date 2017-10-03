package org.dlearn.helsinki.skeleton.database;

import java.sql.Connection;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.Survey;

public class Database {

    private static final String DB_DRIVER = "org.postgresql.Driver";
    
    /* dev environment online */
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/Dlearn_db?verifyServerCertificate=false&useSSL=true";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";

    public void testConnection() throws Exception {
        try(Connection dbConnection = getDBConnection()) {
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Survey postSurvey : returns the survey that was posted on the database.
    // TODO return id
    /*
        
     */
	public Survey postSurvey(Survey survey) throws SQLException {
		try(Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Surveys\" (title, class_id, start_date, teacher_id, description, open) "
                       + "VALUES (?,?,?,?,?,True) RETURNING _id";
            try(PreparedStatement insert = dbConnection.prepareStatement(statement)) {
                insert.setString(1, survey.title); 
                insert.setInt(2, survey.getClass_id());
                insert.setDate(3, new Date(0));
                insert.setInt(4, survey.getTeacher_id());
                insert.setString(5, survey.description);
                // execute query
                try(ResultSet result = insert.executeQuery()) {
                	if (result.next()) {
                        survey.set_id(result.getInt("_id"));
                    } else {
                        System.out.println("Inserting survey didn't return ID of it.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
		//TODO remove
		//survey.set_id(4); 
		return survey;
	}
	
	// TODO finish getQuestions method
	public List<Question> getQuestions() {
		
		ArrayList<Question> questions = new ArrayList<Question>();
		try(Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "Select * FROM public.\"Questions\"";
            try(PreparedStatement select = dbConnection.prepareStatement(statement)) {
                //select.setInt(1, spidergraph_id);
                //select.setInt(2, student_id);
                // execute query
                try(ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                    	Question question = new Question();
                    	question.setQuestion(result.getString(1));
                    	question.setMin_answer(result.getInt(2));
                    	question.setMax_answer(result.getInt(3));
                    	question.set_id(result.getInt(4));
                    	questions.add(question);
                    	System.out.println(question.getQuestion());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return questions;
	}
	
	public void postSurveyQuestions(List<Question> questions, Survey survey) {
		try(Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Survey_questions\" (survey_id, question_id) "
            		+ "VALUES (?,?)";
            try(PreparedStatement insert = dbConnection.prepareStatement(statement)) {
            	// prepare batch
            	System.out.println("before for loop.");
            	for(Question question : questions){
                    insert.setInt(1,survey.get_id()); 
                    insert.setInt(2, question.get_id());
                    System.out.println("Preparing batch: " + question.get_id());
                    insert.addBatch();
            	}
                // execute query
                insert.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println("SQL Error(postSurveyQuestions): "+ e.getMessage());
        }
	}
	
	// Method getQuestionFromSurvey
	// parameter : int, id of survey
	// output : ArrayList<Question>, list of questions from the survey
	// Takes a survey id and returns all the questions set to that survey
	public List<Question> getQuestionsFromSurvey(int survey_id) {
		
		System.out.println("Getting the questions from the survey.");
		ArrayList<Question> questions = new ArrayList<Question>();
		try(Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "Select _id, question, min_answer, max_answer FROM \"Questions\", \"Survey_questions\" WHERE"
            		+ " \"Survey_questions\".survey_id = ? AND \"Survey_questions\".question_id = \"Questions\"._id";
            //prepare statement with survey_id
            try(PreparedStatement select = dbConnection.prepareStatement(statement)) {
                select.setInt(1, survey_id);

                // execute query
                try(ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                    	Question question = new Question();
                    	question.set_id(result.getInt(1));
                    	question.setQuestion(result.getString(2));
                    	question.setMin_answer(result.getInt(3));
                    	question.setMax_answer(result.getInt(4));
                    	questions.add(question);
                    	System.out.println(question.getQuestion());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return questions;
	}

	// Method : postSutdentAnswersForSurvey
	// Takes the survey_id, the student_id
	public void postStudentAnswersForSurvey(List<Answer> answers, int survey_id, int student_id) {
		// TODO Auto-generated method stub
	}
	
	// Method : getSurveysFromClassAsStudent
	// Input  : the survey_id, the student_id
	// Output : returns a list of surveys available to the student
	public List<Survey> getSurveysFromClassAsStudent(int student_id, int class_id) throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Group> getAllGroupsForStudent(int studentID) {
		System.out.println("Getting groups for the student " + Integer.toString(studentID));
		ArrayList<Group> groups = new ArrayList<>();

		try(Connection dbConnection = getDBConnection()) {
            String statement = "Select _id, name, student_id, teacher_id FROM public.\"Groups\" WHERE student_id = ?";
            //prepare statement with student_id
            try(PreparedStatement select = dbConnection.prepareStatement(statement)) {
            	select.setInt(1, studentID);
                // execute query
                try(ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                    	Group group = new Group();
                    	group.set_id(result.getInt(1));
                    	group.setStudent_id(result.getInt(2));
                    	group.setName(result.getString(1));
                    	groups.add(group);
                    }
                }
            }
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }		
		return groups;
		//*/
	}
	
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            String dbLogin = System.getenv("JDBC_DATABASE_LOGIN");
            String dbPassword = System.getenv("JDBC_DATABASE_PASSWORD");
            if(dbUrl == null){ // local # TODO fix
            	System.out.println("JDBC env empty, on local");
                dbConnection = DriverManager.getConnection(
                        DB_CONNECTION, DB_USER, DB_PASSWORD);
            }else if(dbLogin == null && dbPassword == null) { // production
            	dbConnection = DriverManager.getConnection(dbUrl);
            } else {
            	
            	//"jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true"
            	dbUrl += "?user=" + dbLogin + "&password=" + dbPassword + "&ssl=true";
            	//dbConnection = DriverManager.getConnection(dbUrl, dbLogin, dbPassword);
            	dbConnection = DriverManager.getConnection(dbUrl);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;

    }

    @SuppressWarnings("unused")
    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

}
