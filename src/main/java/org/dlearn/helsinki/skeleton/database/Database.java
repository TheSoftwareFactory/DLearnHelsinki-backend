package org.dlearn.helsinki.skeleton.database;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dlearn.helsinki.skeleton.model.Answer;
<<<<<<< 8f9a9653c5dcdf3ca9659915330c7a7dc9d81eba
import org.dlearn.helsinki.skeleton.model.ClassThemeAverage;
=======
>>>>>>> db is working
import org.dlearn.helsinki.skeleton.model.Classes;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.NewStudent;
import org.dlearn.helsinki.skeleton.model.GroupAnswer;
import org.dlearn.helsinki.skeleton.model.NewTeacher;
import org.dlearn.helsinki.skeleton.model.GroupThemeAverage;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.Researcher;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.StudentGroup;
import org.dlearn.helsinki.skeleton.model.StudentThemeAverage;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.model.Teacher;
import org.dlearn.helsinki.skeleton.security.Hasher;
import org.springframework.jdbc.datasource.AbstractDataSource;

public class Database extends AbstractDataSource {

    private static final String DB_DRIVER = "org.postgresql.Driver";

    /* dev environment online */
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/Dlearn_db?verifyServerCertificate=false&useSSL=true";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";

    public void testConnection() throws Exception {
        try (Connection dbConnection = getDBConnection()) {
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Survey postSurvey : returns the survey that was posted on the database.
    // TODO return id
    /*
        
     */
    public Survey postSurvey(Survey survey) throws SQLException {
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Surveys\" (title, class_id, start_date, teacher_id, description, open) "
                    + "VALUES (?,?,now(),?,?,True) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, survey.title);
                insert.setInt(2, survey.getClass_id());
                //insert.setDate(3, new Date(0));
                insert.setInt(3, survey.getTeacher_id());
                insert.setString(4, survey.description);
                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        survey.set_id(result.getInt("_id"));
                    } else {
                        System.out.println(
                                "Inserting survey didn't return ID of it.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return survey;
    }

    // TODO finish getQuestions method
    public List<Question> getQuestions() {

        ArrayList<Question> questions = new ArrayList<Question>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "Select * FROM public.\"Questions\"";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                //select.setInt(1, spidergraph_id);
                //select.setInt(2, student_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
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

    public Question postQuestion(Question question) throws SQLException {
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Questions\" (question, min_answer, max_answer) "
                    + "VALUES (?, ?, ?) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, question.question);
                insert.setInt(2, question.min_answer);
                insert.setInt(3, question.max_answer);
                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        question.set_id(result.getInt("_id"));
                    } else {
                        System.out.println(
                                "Inserting question didn't return ID of it.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return question;
    }

    public void postSurveyQuestions(List<Question> questions, Survey survey) {
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Survey_questions\" (survey_id, question_id) "
                    + "VALUES (?,?)";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                // prepare batch
                System.out.println("before for loop.");
                for (Question question : questions) {
                    insert.setInt(1, survey.get_id());
                    insert.setInt(2, question.get_id());
                    System.out.println("Preparing batch: " + question.get_id());
                    insert.addBatch();
                }
                // execute query
                insert.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println(
                    "SQL Error(postSurveyQuestions): " + e.getMessage());
        }

    }

    // Method getQuestionFromSurvey
    // parameter : int, id of survey
    // output : ArrayList<Question>, list of questions from the survey
    // Takes a survey id and returns all the questions set to that survey
    public List<Question> getQuestionsFromSurvey(int survey_id) {
        System.out.println("Getting the questions from the survey.");
        ArrayList<Question> questions = new ArrayList<>();

        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "Select _id, question, min_answer, max_answer FROM \"Questions\", \"Survey_questions\" WHERE"
                    + " \"Survey_questions\".survey_id = ? AND \"Survey_questions\".question_id = \"Questions\"._id";
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, survey_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Question question = new Question();
                        question.set_id(result.getInt(1));
                        question.setQuestion(result.getString(2));
                        question.setMin_answer(result.getInt(3));
                        question.setMax_answer(result.getInt(4));
                        questions.add(question);
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
    public void postStudentAnswersForSurvey(List<Answer> answers, int survey_id,
            int student_id) {
        // TODO Auto-generated method stub
    }

    // Method : getSurveysFromClassAsStudent
    // Input  : the survey_id, the student_id
    // Output : returns a list of surveys available to the student
    public List<Survey> getSurveysFromClassAsStudent(int student_id,
            int class_id) throws SQLException {
        // TODO link class with student_classes and remove class_id form student_classes
        //SELECT * FROM public."Surveys",public."Students",public."Student_Classes" WHERE public."Students"._id = public."Student_Classes".class_id AND public."Student_Classes".class_id = public."Surveys".class_id AND public."Student_Classes".class_id = 1 AND public."Students"._id = 1
        return null;
    }

    // Method : getSurveysFromClassAsStudent
    // Input  : the survey_id, the student_id
    // Output : returns a list of surveys available to the student
    public List<Survey> getSurveysFromClassAsTeacher(int teacher_id,
            int class_id) throws SQLException {
        //SELECT * FROM public."Surveys",public."Students",public."Student_Classes" WHERE public."Students"._id = public."Student_Classes".class_id AND public."Student_Classes".class_id = public."Surveys".class_id AND public."Student_Classes".class_id = 1 AND public."Students"._id = 1
        ArrayList<Survey> surveys = new ArrayList<Survey>();

        try (Connection dbConnection = getDBConnection()) {
            String statement = "SELECT _id,title,description,start_date,end_date,open "
                    + "FROM public.\"Surveys\" "
                    + "WHERE class_id = ? AND teacher_id = ?";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);
                select.setInt(2, teacher_id);
                System.out.println("survey list");

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Survey survey = new Survey();
                        survey.set_id(result.getInt(1));
                        survey.setTitle(result.getString(2));
                        survey.setDescription(result.getString(3));
                        survey.setStart_date(result.getDate(4).toString());
                        result.getDate(5);
                        if (!result.wasNull()) {
                            survey.setEnd_date(result.getDate(5).toString());
                        }
                        survey.setOpen(result.getBoolean(6));
                        survey.setClass_id(class_id);
                        survey.setTeacher_id(teacher_id);
                        surveys.add(survey);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return surveys;
    }

    public List<Survey> getSurveys() {
        List<Survey> survey = new ArrayList<>();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "SELECT _id, title, class_id, start_date, end_date, teacher_id, description, open FROM public.\"Surveys\"";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        survey.add(new Survey() {
                            {
                                this._id = result.getInt("_id");
                                this.title = result.getString("title");
                                this.class_id = result.getInt("class_id");
                                this.start_date = result
                                        .getString("start_date");
                                this.end_date = result.getString("end_date");
                                this.teacher_id = result.getInt("teacher_id");
                                this.description = result
                                        .getString("description");
                                this.open = result.getBoolean("open");
                            }
                        });
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return survey;
    }

    public List<Group> getAllGroupsForStudent(int studentID) {
        System.out.println("Getting groups for the student "
                + Integer.toString(studentID));
        ArrayList<Group> groups = new ArrayList<>();

        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select _id, name, student_id, teacher_id FROM public.\"Groups\" WHERE student_id = ?";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, studentID);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Group group = new Group();
                        group.set_id(result.getInt(1));
                        group.setClass_id(result.getInt(2));
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

    public List<Group> getAllGroupsFromClass(int class_id) {
        System.out.println(
                "Getting groups for the class " + Integer.toString(class_id));
        ArrayList<Group> groups = null;

        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select name, _id, class_id FROM public.\"Groups\" WHERE (class_id = ?);";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    groups = new ArrayList<>();
                    while (result.next()) {
                        Group group = new Group();
                        group.set_id(result.getInt("_id"));
                        group.setClass_id(result.getInt("class_id"));
                        group.setName(result.getString("name"));
                        groups.add(group);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return groups;
    }

    public Group getGroupFromClass(int class_id, int group_id) {
        System.out.println("Getting group class " + Integer.toString(class_id));
        Group group = null;

        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select name FROM public.\"Groups\" WHERE (class_id = ?) and (_id = ?);";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);
                select.setInt(2, group_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    if (result.next()) {
                        group = new Group(group_id, result.getString("name"),
                                class_id);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return group;
    }

    public List<Student> getAllStudentsFromClassAndGroup(int class_id,
            int group_id) {
        System.out.println(
                "Getting groups for the class " + Integer.toString(group_id));
        ArrayList<Student> students = null;

        try (Connection dbConnection = getDBConnection()) {
            // TODO update request to remove class_id
            String statement = "Select std._id, username, pwd, gender, age "
                    + "FROM public.\"Student_Classes\" as cls "
                    + "INNER JOIN public.\"Students\" as std "
                    + "ON (cls.student_id = std._id)"
                    + "WHERE (cls.class_id = ?) AND (cls.group_id = ?)";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);
                select.setInt(2, group_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    students = new ArrayList<>();
                    while (result.next()) {
                        Student student = new Student(result.getInt("_id"),
                                result.getString("username"),
                                result.getString("gender"),
                                result.getInt("age"));
                        students.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }

    public Student createStudent(NewStudent student) {
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Students\" (username, pwd, gender, age) "
                    + "VALUES (?,?,?,?) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, student.student.username);
                insert.setString(2,
                        Hasher.getHasher().encode(student.password));
                insert.setString(3, student.student.gender);
                insert.setInt(4, student.student.age);

                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        student.student._id = result.getInt("_id");
                    } else {
                        System.out.println(
                                "Inserting student didn't return ID of it.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return student.student;
    }

    public Teacher createTeacher(NewTeacher teacher) {
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Teachers\" (username, pwd) "
                    + "VALUES (?,?,?,?) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, teacher.teacher.username);
                insert.setString(2,
                        Hasher.getHasher().encode(teacher.password));

                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        teacher.teacher._id = result.getInt("_id");
                    } else {
                        System.out.println(
                                "Inserting teacher didn't return ID of it.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return teacher.teacher;
    }

    public void addStudentToGroup(Student student, int class_id, int group_id) {
        try (Connection dbConnection = getDBConnection()) {
            try (PreparedStatement insert = dbConnection.prepareStatement(
                    "SELECT class_id FROM public.\"Groups\" WHERE group_id=?")) {
                insert.setInt(1, group_id);
                try (ResultSet result = insert.executeQuery()) {
                    result.first();
                    int real_class_id = result.getInt(1);
                    if (class_id != real_class_id) {
                        throw new SQLException("Class id's don't match: "
                                + class_id + " != " + real_class_id);
                    }
                }
            }
            String statement = "INSERT INTO public.\"Student_Classes\" (student_id, class_id, group_id) "
                    + "VALUES (?,?,?)";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setInt(1, student._id);
                insert.setInt(2, class_id);
                insert.setInt(3, group_id);

                // execute query
                insert.execute();
            }
        } catch (SQLException e) {
            System.out
                    .println("SQL Error(addStudentToGroup): " + e.getMessage());
        }
    }

    public Student getStudent(int studentID) {
        Student student = null;

        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select username, pwd, gender, age FROM public.\"Students\" WHERE _id = ?";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, studentID);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    if (result.next()) {
                        student = new Student();
                        student.set_id(studentID);
                        student.setAge(result.getInt("age"));
                        student.setUsername(result.getString("username"));
                        student.setGender(result.getString("gender"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return student;
    }

	public List<Student> getAllStudentsFromClass(int class_id) {
		List<Student> students = null;
		//SQL rewritten for new database
		try(Connection dbConnection = getDBConnection()) {
	        String statement = "Select std._id, username, pwd, gender, age "
	        		+ "FROM public.\"Groups\" AS gr INNER JOIN  public.\"Student_Classes\" as cls "
	        		+ "ON (gr._id = cls.group_id) "
	        		+ "INNER JOIN public.\"Students\" AS st "
	        		+ "ON (st._id = cls.student_id) "
	        		+ "WHERE (gr.class_id = ?);";
	        //prepare statement with student_id
	        try(PreparedStatement select = dbConnection.prepareStatement(statement)) {
	        	select.setInt(1, class_id);
	            // execute query
	            try(ResultSet result = select.executeQuery()) {
	            	students = new ArrayList<Student>();
	            	while(result.next()) { 
	            		Student student = new Student();
	               		student.set_id(result.getInt("_id"));
	               		student.setAge(result.getInt("age"));
	               		student.setUsername(result.getString("username"));
	               		student.setGender(result.getString("gender"));
	               		students.add(student);
                	}
                }
            }
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }		
		return students;
	}

    public Optional<Student> getStudentFromUsername(String username_) {
        Optional<Student> student = Optional.empty();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select _id, gender, age FROM public.\"Students\" WHERE username = ?";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setString(1, username_);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    if (result.next()) {
                        student = Optional.of(new Student() {
                            {
                                this.username = username_;
                                _id = result.getInt("_id");
                                age = result.getInt("age");
                                gender = result.getString("gender");
                            }
                        });
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return student;
    }

    public Optional<Teacher> getTeacherFromUsername(String username_) {
        Optional<Teacher> teacher = Optional.empty();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select _id FROM public.\"Teachers\" WHERE username = ?";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setString(1, username_);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    if (result.next()) {
                        teacher = Optional.of(new Teacher() {
                            {
                                this.username = username_;
                                _id = result.getInt("_id");
                            }
                        });
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return teacher;
    }

    public Optional<Researcher> getResearcherFromUsername(String username_) {
        Optional<Researcher> researcher = Optional.empty();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select _id FROM public.\"Researchers\" WHERE username = ?";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setString(1, username_);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    if (result.next()) {
                        researcher = Optional.of(new Researcher() {
                            {
                                this.username = username_;
                                id = result.getInt("_id");
                            }
                        });
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return researcher;
    }
    
	public boolean doesStudentUsernameExistInDatabase(Student student) {
		boolean exists = false;
		try(Connection dbConnection = getDBConnection()) {
	        String statement = "Select username FROM public.\"Students\" as std WHERE std.username = ?";
	        //prepare statement with student_id
	        try(PreparedStatement select = dbConnection.
	        		prepareStatement(statement)) {
	        	select.setString(1, student.getUsername());
	            // execute query
	            try(ResultSet result = select.executeQuery()) {
	            	if(result.next()) {
	            		exists = true;
                	}
                }
            }
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }
		return exists;
	}

	public List<Classes> getAllClassesOfTeacher(int teacher_id) {
		List<Classes> classes = null;
		
		try(Connection dbConnection = getDBConnection()) {
	        String statement = "Select _id, name"
	        		+ "FROM public.\"Classes\" as cls"
	        		+ "WHERE (cls.teacher_id = ?);";
	        //prepare statement with student_id
	        try(PreparedStatement select = dbConnection.prepareStatement(statement)) {
	        	select.setInt(1, teacher_id);
	            // execute query
	            try(ResultSet result = select.executeQuery()) {
	            	classes = new ArrayList<Classes>();
	            	while(result.next()) { 
	            		Classes newClass = new Classes();
	            		newClass.set_id(result.getInt("_id"));
	            		newClass.setName(result.getString("name"));
	            		newClass.setTeacher_id(teacher_id);
	            		classes.add(newClass);
                	}
                }
            }
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }	
		return classes;		
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
            if(dbUrl == null){ // local # TODO fix
            	System.out.println("JDBC env empty, on local");
                dbConnection = DriverManager.getConnection(
                        DB_CONNECTION, DB_USER, DB_PASSWORD);
            }else { // production
            	//System.out.println("Connecting to " + dbUrl);
            	dbConnection = DriverManager.getConnection(dbUrl);
            }
        } catch (SQLException e) {
            System.out.println("CREATING CONNECTION FAILED HORRIBLY "
                    + e.getMessage() + " (fix pls)");
        }
        return dbConnection;

    }

    @SuppressWarnings("unused")
    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDBConnection();
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        throw new SQLException("Not supported");
    }

    // Inserts an answer into the database
    public void putAnswerToQuestion(Answer answer) {
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Answers\" "
                    + "(question_id, student_id, answer, survey_id)"
                    + "VALUES (?, ?, ?, ?) "
                    + "ON CONFLICT (question_id,student_id,survey_id) DO UPDATE SET answer = ?";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setInt(1, answer.question_id);
                insert.setInt(2, answer.student_id);
                insert.setInt(3, answer.answer);
                insert.setInt(4, answer.survey_id);
                insert.setInt(5, answer.answer);
                // execute query
                insert.executeQuery();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    public List<Answer> getAnswersFromStudentSurvey(int student_id,
            int survey_id) {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "Select student_id, survey_id, question_id, answer FROM \"Answers\" WHERE"
                    + " survey_id = ? AND student_id = ?";
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, survey_id);
                select.setInt(2, student_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Answer answer = new Answer();
                        answer.student_id = result.getInt(1);
                        answer.survey_id = result.getInt(2);
                        answer.setQuestion_id(result.getInt(3));
                        answer.setAnswer(result.getInt(4));
                        answers.add(answer);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return answers;
    }

    public List<GroupThemeAverage> getAverageAnswersFromGroup(int class_id,
            int group_id, int survey_id) {
        ArrayList<GroupThemeAverage> answers = new ArrayList<GroupThemeAverage>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "SELECT avg(answer),\"Themes\".title,\"Themes\".description,\"Themes\"._id,\"Surveys\".start_date "
                    + "FROM public.\"Surveys\",public.\"Answers\", public.\"Student_Classes\", public.\"Themes\", public.\"Questions\" "
                    + "WHERE \"Questions\"._id = question_id "
                    + "AND \"Questions\".theme_id = \"Themes\"._id "
                    + "AND \"Answers\".student_id = \"Student_Classes\".student_id "
                    + "AND \"Surveys\"._id = \"Answers\".survey_id "
                    + "AND \"Student_Classes\".group_id = ? "
                    + "AND \"Answers\".survey_id = ? "
                    + "GROUP BY \"Themes\"._id,start_date";
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, group_id);
                select.setInt(2, survey_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        GroupThemeAverage answer = new GroupThemeAverage();
                        //answer.setQuestion_id(result.getInt(1));
                        answer.setAnswer(result.getFloat(1));
                        answer.setTheme_title(result.getString(2));
                        answer.setDescription(result.getString(3));
                        answer.setTheme_id(result.getInt(4));
                        answer.setStart_date(result.getString(5));
                        answer.setGroup_id(group_id);
                        answer.setSurvey_id(survey_id);
                        System.out.println(
                                "Average answer : " + answer.getAnswer());
                        answers.add(answer);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return answers;
    }

    public List<StudentGroup> getGroupsWithStudents(int class_id) {
        ArrayList<StudentGroup> studentGroups = new ArrayList<StudentGroup>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "SELECT \"Groups\"._id,\"Groups\".name,\"Students\"._id,\"Students\".username,\"Students\".gender,\"Students\".age "
                    + "FROM \"Students\",\"Groups\",\"Student_Classes\" "
                    + "WHERE \"Groups\"._id = \"Student_Classes\".group_id "
                    + "AND \"Student_Classes\".student_id = \"Students\"._id "
                    + "AND \"Student_Classes\".class_id = ?";
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    ArrayList<Integer> group_ids = new ArrayList<>();
                    while (result.next()) {
                        if (group_ids.contains(result.getInt(1))) {
                            // add Student to Group
                            for (StudentGroup group : studentGroups) {
                                if (group._id == result.getInt(1)) {
                                    // add Student to Group
                                    Student student = new Student();
                                    student.set_id(result.getInt(3));
                                    student.setUsername(result.getString(4));
                                    student.gender = result.getString(5);
                                    student.age = result.getInt(6);
                                    group.students.add(student);
                                }
                            }
                            //TODO
                        } else {
                            // update group_id list
                            group_ids.add(result.getInt(1));
                            // add Group
                            StudentGroup group = new StudentGroup();
                            group._id = result.getInt(1);
                            group.name = result.getString(2);

                            // add Student to Group
                            Student student = new Student();
                            student.set_id(result.getInt(3));
                            student.setUsername(result.getString(4));
                            student.gender = result.getString(5);
                            student.age = result.getInt(6);
                            group.students.add(student);

                            studentGroups.add(group);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentGroups;
    }

    public List<ClassThemeAverage> getClassThemeAverage(int class_id,
            int survey_id) {
        ArrayList<ClassThemeAverage> answers = new ArrayList<ClassThemeAverage>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "SELECT avg(answer),\"Themes\".title,\"Themes\".description,\"Themes\"._id,\"Surveys\".start_date "
                    + "FROM public.\"Surveys\",public.\"Answers\", public.\"Student_Classes\",public.\"Groups\", public.\"Themes\", public.\"Questions\" "
                    + "WHERE \"Questions\"._id = question_id "
                    + "AND \"Questions\".theme_id = \"Themes\"._id "
                    + "AND \"Answers\".student_id = \"Student_Classes\".student_id "
                    + "AND \"Surveys\"._id = \"Answers\".survey_id "
                    + "AND \"Student_Classes\".group_id = \"Groups\"._id "
                    + "AND \"Groups\".class_id = ? "
                    + "AND \"Answers\".survey_id = ? "
                    + "GROUP BY \"Themes\"._id,start_date";
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);
                select.setInt(2, survey_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        ClassThemeAverage answer = new ClassThemeAverage();
                        //answer.setQuestion_id(result.getInt(1));
                        answer.setAnswer(result.getFloat(1));
                        answer.setTheme_title(result.getString(2));
                        answer.setDescription(result.getString(3));
                        answer.setTheme_id(result.getInt(4));
                        answer.setStart_date(result.getString(5));
                        answer.setClass_id(class_id);
                        answer.setSurvey_id(survey_id);
                        answers.add(answer);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return answers;
    }

    public List<StudentThemeAverage> getStudentThemeAverage(int survey_id,
            int student_id) {
        ArrayList<StudentThemeAverage> answers = new ArrayList<StudentThemeAverage>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "SELECT avg(answer),\"Themes\".title,\"Themes\".description,\"Themes\"._id,\"Surveys\".start_date "
                    + "FROM public.\"Surveys\",public.\"Answers\", public.\"Student_Classes\",public.\"Groups\", public.\"Themes\", public.\"Questions\" "
                    + "WHERE \"Questions\"._id = question_id "
                    + "AND \"Questions\".theme_id = \"Themes\"._id "
                    + "AND \"Answers\".student_id = ? "
                    + "AND \"Surveys\"._id = \"Answers\".survey_id "
                    + "AND \"Answers\".survey_id = ? "
                    + "GROUP BY \"Themes\"._id,start_date";
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, student_id);
                select.setInt(2, survey_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        StudentThemeAverage answer = new StudentThemeAverage();
                        //answer.setQuestion_id(result.getInt(1));
                        answer.setAnswer(result.getFloat(1));
                        answer.setTheme_title(result.getString(2));
                        answer.setDescription(result.getString(3));
                        answer.setTheme_id(result.getInt(4));
                        answer.setStart_date(result.getString(5));
                        answer.setStudent_id(student_id);
                        answer.setSurvey_id(survey_id);
                        answers.add(answer);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return answers;
    }

    public void closeSurvey(int teacher_id, int class_id, int survey_id) {
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "UPDATE public.\"Surveys\" "
                    + "SET (open,end_date) = (false,now())"
                    + "WHERE teacher_id = ? " + "AND _id = ? "
                    + "AND class_id = ?";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setInt(1, teacher_id);
                insert.setInt(2, survey_id);
                insert.setInt(3, class_id);
                // execute query
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("error caught : " + e.getMessage());

        }
    }

}
