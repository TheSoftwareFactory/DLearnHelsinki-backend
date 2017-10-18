package org.dlearn.helsinki.skeleton.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.commons.dbcp2.BasicDataSource;

import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.model.ChangePasswordStudent;
import org.dlearn.helsinki.skeleton.model.ClassThemeAverage;
import org.dlearn.helsinki.skeleton.model.Classes;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.NewStudent;
import org.dlearn.helsinki.skeleton.model.NewTeacher;
import org.dlearn.helsinki.skeleton.model.GroupThemeAverage;
import org.dlearn.helsinki.skeleton.model.ListClassThemeAverage;
import org.dlearn.helsinki.skeleton.model.ListGroupThemeAverage;
import org.dlearn.helsinki.skeleton.model.ListStudentThemeAverage;
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
    
    private static BasicDataSource datasource;
    
    static {
        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
            datasource = new BasicDataSource();
            
            if (dbUri.getUserInfo() != null) {
                datasource.setUsername(dbUri.getUserInfo().split(":")[0]);
                datasource.setPassword(dbUri.getUserInfo().split(":")[1]);
            }
            datasource.setDriverClassName("org.postgresql.Driver");
            datasource.setUrl(dbUrl);
            datasource.setInitialSize(1);
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }

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
        // TODO implement but currently it's easier for front-end to send one at a time...
    }

    // Method : getSurveysFromClassAsStudent
    // Input  : the survey_id, the student_id
    // Output : returns a list of surveys available to the student
    public List<Survey> getSurveysFromClassAsStudent(int student_id,
            int class_id) throws SQLException {
    	ArrayList<Survey> surveys = new ArrayList<Survey>();

        try (Connection dbConnection = getDBConnection()) {
            String statement = "SELECT distinct _id,title,description,start_date,end_date,open,teacher_id "
                    + "FROM public.\"Surveys\" "
                    + "WHERE class_id = ? ";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);
                System.out.println("survey list");

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Survey survey = new Survey();
                        survey.set_id(result.getInt(1));
                        survey.setTitle(result.getString(2));
                        survey.setDescription(result.getString(3));
                        survey.setStart_date(result.getTimestamp(4));
                        result.getTimestamp(5);
                        if (!result.wasNull()) {
                            survey.setEnd_date(result.getTimestamp(5));
                        }
                        survey.setOpen(result.getBoolean(6));
                        survey.setClass_id(class_id);
                        survey.setTeacher_id(result.getInt(7));
                        surveys.add(survey);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return surveys;
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
                        survey.setStart_date(result.getTimestamp(4));
                        result.getTimestamp(5);
                        if (!result.wasNull()) {
                            survey.setEnd_date(result.getTimestamp(5));
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
                                        .getTimestamp("start_date");
                                this.end_date = result.getTimestamp("end_date");
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
    
    public Optional<Group> getGroupForStudent(int class_id, int student_id) {
        try (Connection dbConnection = getDBConnection()) {
            String statement = ""
                    + "SELECT sc.group_id, g.name"
                    + "  FROM public.\"Student_Classes\" as sc,"
                    + "       public.\"Groups\" as g"
                    + "  WHERE sc.group_id = g._id"
                    + "    AND sc.class_id = g.class_id"
                    + "    AND sc.student_id = ?"
                    + "    AND sc.class_id = ?"
                    + "  ORDER BY sc.creation_date DESC"
                    + "  LIMIT 1";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, student_id);
                select.setInt(2, class_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        return Optional.of(new Group() {{
                            this.set_id(result.getInt("group_id"));
                            this.setClass_id(class_id);
                            this.setName(result.getString("name"));
                        }});
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public List<Group> getAllGroupsForStudent(int student_id) {
        ArrayList<Group> groups = new ArrayList<>();

        try (Connection dbConnection = getDBConnection()) {
            String statement = ""
                    + "SELECT sc.group_id, g.name, g.class_id"
                    + "  FROM public.\"Student_Classes\" as sc,"
                    + "       public.\"Groups\" as g"
                    + "  WHERE sc.group_id = g._id"
                    + "    AND sc.class_id = g.class_id"
                    + "    AND sc.student_id = ?"
                    + "    AND sc.class_id = ?";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, student_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Group group = new Group();
                        group.set_id(result.getInt("group_id"));
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
            String statement = ""
                    + "SELECT s._id, s.username, s.gender, s.age\n"
                    + "  FROM public.\"Student_Classes\" as sc,\n"
                    + "       public.\"Students\" as s \n"
                    + " WHERE sc.student_id = s._id\n"
                    + "   AND sc.class_id = ?\n"
                    + "   AND sc.group_id = ?\n"
                    + "   AND sc._id = (SELECT sc2._id\n"
                    + "                  FROM public.\"Student_Classes\" as sc2\n"
                    + "                  WHERE sc.student_id = sc2.student_id\n"
                    + "                 ORDER BY sc2.creation_date DESC\n"
                    + "                 LIMIT 1)";
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
    
    public Optional<Student> changeStudentPassword(ChangePasswordStudent student) {
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "UPDATE public.\"Students\" SET pwd = ? WHERE _id = ?";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1,
                        Hasher.getHasher().encode(student.password));
                insert.setInt(2, student.student_id);

                // execute query
                insert.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(getStudent(student.student_id));
    }

    public boolean addStudentToGroup(Student student, int class_id, int group_id) {
        try (Connection dbConnection = getDBConnection()) {
            ensureGroupClassMatch(dbConnection, group_id, class_id);
            String statement = "INSERT INTO public.\"Student_Classes\" (student_id, class_id, group_id) "
                    + "VALUES (?,?,?)";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setInt(1, student._id);
                insert.setInt(2, class_id);
                insert.setInt(3, group_id);

                // execute query
                insert.execute();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error(addStudentToGroup): " + e.getMessage());
            return false;
        }
    }

    private void ensureGroupClassMatch(final Connection dbConnection, int group_id, int class_id) throws SQLException {
        try (PreparedStatement insert = dbConnection.prepareStatement(
                "SELECT class_id FROM public.\"Groups\" WHERE _id=?")) {
            insert.setInt(1, group_id);
            try (ResultSet result = insert.executeQuery()) {
                result.next();
                int real_class_id = result.getInt(1);
                if (class_id != real_class_id) {
                    throw new SQLException("Class id's don't match: "
                            + class_id + " != " + real_class_id);
                }
            }
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
	        String statement = "Select st._id, username, pwd, gender, age "
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
	        String statement = "Select _id, name "
	        		+ "FROM public.\"Classes\" as cls "
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
	
	public List<Classes> getAllClassesStundentIsIn(int student_id) {
		List<Classes> classes = null;
		
		try(Connection dbConnection = getDBConnection()) {
	        String statement = "Select cls._id, cls.name, cls.teacher_id "
	        		+ "FROM public.\"Groups\" as gr "
	        		+ "INNER JOIN public.\"Student_Classes\" as st_cls "
	        		+ "ON st_cls.group_id = gr._id "
	        		//+ "INNER JOIN public.\"Students\" as st_cls "
	        		//+ "ON st_cls.group_id = gr.class_id"
	        		+ "INNER JOIN public.\"Classes\" as cls "
	        		+ "ON cls._id = gr.class_id "
	        		+ "WHERE (st_cls.student_id = ?);";
	        //prepare statement with student_id
	        try(PreparedStatement select = dbConnection.prepareStatement(statement)) {
	        	select.setInt(1, student_id);
	            // execute query
	            try(ResultSet result = select.executeQuery()) {
	            	classes = new ArrayList<Classes>();
	            	while(result.next()) { 
	            		Classes newClass = new Classes();
	            		newClass.set_id(result.getInt("_id"));
	            		newClass.setName(result.getString("name"));
	            		newClass.setTeacher_id(result.getInt("teacher_id"));
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
        try {
            return datasource.getConnection();
//        Connection dbConnection = null;
//        try {
//            Class.forName(DB_DRIVER);
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            String dbUrl = System.getenv("JDBC_DATABASE_URL");
//            if (dbUrl == null) {
//                System.out.println("JDBC env empty, on local");
//                dbConnection = DriverManager.getConnection(DB_CONNECTION,
//                        DB_USER, DB_PASSWORD);
//            } else { // production
//                dbConnection = DriverManager.getConnection(dbUrl);
//            }
//        } catch (SQLException e) {
//            System.out.println("CREATING CONNECTION FAILED HORRIBLY "
//                    + e.getMessage() + " (fix pls)");
//        }
//        return dbConnection;
        } catch (SQLException ex) {
            return null;
        }
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
        Map<Integer, StudentGroup> studentGroups = new TreeMap<>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = ""
                    + "SELECT g._id as group_id,\n"
                    + "       g.name as group_name,\n"
                    + "       s._id as student_id,\n"
                    + "       s.username,\n"
                    + "       s.gender,\n"
                    + "       s.age\n"
                    + "  FROM public.\"Students\" as s,\n"
                    + "       public.\"Groups\" as g,\n"
                    + "       public.\"Student_Classes\" as sc\n"
                    + " WHERE g._id = sc.group_id\n"
                    + "   AND sc.student_id = s._id\n"
                    + "   AND sc.class_id = ?\n"
                    + "   AND sc._id = (SELECT sc2._id\n"
                    + "                   FROM public.\"Student_Classes\" as sc2\n"
                    + "                  WHERE sc.student_id = sc2.student_id\n"
                    + "                 ORDER BY sc2.creation_date DESC\n"
                    + "                 LIMIT 1)";
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        String group_name = result.getString("group_name");
                        int student_id = result.getInt("student_id");
                        String _username = result.getString("username");
                        String _gender = result.getString("gender");
                        int _age = result.getInt("age");
                        // add Student to Group
                        studentGroups.compute(result.getInt("group_id"),
                            (group_id, group) -> {
                                if (group == null) {
                                    group = new StudentGroup(){{
                                        this._id = group_id;
                                        this.name = group_name;
                                    }};
                                }
                                group.students.add(new Student() {{
                                    this._id = student_id;
                                    this.username = _username;
                                    this.gender = _gender;
                                    this.age = _age;
                                }});
                                return group;
                            });
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentGroups.values().stream().collect(Collectors.toList());
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

    public Optional<List<ListStudentThemeAverage>> getStudentThemeAverageProgression(int student_id, int amount) {
        try {
            return Optional.of(DataBaseHelper.query(Database::getDBConnection, ""
                    + "SELECT * FROM (\n"
                    + "    SELECT\n"
                    + "        DENSE_RANK() OVER(ORDER BY su._id DESC) AS survey_rank,\n"
                    + "        avg(an.answer) as average,\n"
                    + "        su._id as survey_id,\n"
                    + "        th.title,\n"
                    + "        th.description,\n"
                    + "        th._id as theme_id,\n"
                    + "        su.start_date \n"
                    + "    FROM public.\"Surveys\" as su,\n"
                    + "         public.\"Answers\" as an,\n"
                    + "         public.\"Themes\" as th,\n"
                    + "         public.\"Questions\" as qu \n"
                    + "    WHERE qu._id = an.question_id \n"
                    + "      AND qu.theme_id = th._id \n"
                    + "      AND an.student_id = ? \n"
                    + "      AND su._id = an.survey_id \n"
                    + "    GROUP BY su._id, th._id\n"
                    + "    ORDER BY su.start_date DESC, th._id\n"
                    + ") x WHERE x.survey_rank <= ?",
                    select -> {
                        select.setInt(1, student_id);
                        select.setInt(2, amount);
                    },
                    results -> new ArrayList<ListStudentThemeAverage>() {
                        {
                            int last_survey_rank = -2;
                            for (ResultSet result : results) {
                                StudentThemeAverage answer = new StudentThemeAverage();
                                answer.setAnswer(result.getFloat("average"));
                                answer.setTheme_title(result.getString("title"));
                                answer.setDescription(result.getString("description"));
                                answer.setTheme_id(result.getInt("theme_id"));
                                answer.setStart_date(result.getString("start_date"));
                                answer.setStudent_id(student_id);
                                answer.setSurvey_id(result.getInt("survey_id"));
                                int survey_rank = result.getInt("survey_rank") - 1;
                                if (last_survey_rank == survey_rank) {
                                    this.get(survey_rank).themes.add(answer);
                                } else {
                                    last_survey_rank = survey_rank;
                                    this.add(new ListStudentThemeAverage() {{
                                        this.themes = Lists.newArrayList(answer);
                                    }});
                                }
                            }
                        }
                    }
                ));
        } catch (SQLException e) {
            System.out.println("Error caught: " + e);
        }
        return Optional.empty();
    }

    public Optional<List<ListStudentThemeAverage>> getStudentThemeAverageProgressionInClass(int class_id,
            int student_id, int amount) {
        try {
            return Optional.of(DataBaseHelper.query(Database::getDBConnection, ""
                    + "SELECT * FROM (\n"
                    + "    SELECT\n"
                    + "        DENSE_RANK() OVER(ORDER BY su._id DESC) AS survey_rank,\n"
                    + "        avg(an.answer) as average,\n"
                    + "        su._id as survey_id,\n"
                    + "        th.title,\n"
                    + "        th.description,\n"
                    + "        th._id as theme_id,\n"
                    + "        su.start_date \n"
                    + "    FROM public.\"Surveys\" as su,\n"
                    + "         public.\"Answers\" as an,\n"
                    + "         public.\"Student_Classes\" as sc,\n"
                    + "         public.\"Themes\" as th,\n"
                    + "         public.\"Questions\" as qu \n"
                    + "    WHERE qu._id = an.question_id \n"
                    + "      AND qu.theme_id = th._id \n"
                    + "      AND an.student_id = ? \n"
                    + "      AND su._id = an.survey_id \n"
                    + "      AND sc.student_id = an.student_id\n"
                    + "      AND sc.class_id = ?\n"
                    + "    GROUP BY su._id, th._id\n"
                    + "    ORDER BY su.start_date DESC, th._id\n"
                    + ") x WHERE x.survey_rank <= ?",
                    select -> {
                        select.setInt(1, student_id);
                        select.setInt(2, class_id);
                        select.setInt(3, amount);
                    },
                    results -> new ArrayList<ListStudentThemeAverage>() {
                        {
                            int last_survey_rank = -2;
                            for (ResultSet result : results) {
                                StudentThemeAverage answer = new StudentThemeAverage();
                                answer.setAnswer(result.getFloat("average"));
                                answer.setTheme_title(result.getString("title"));
                                answer.setDescription(result.getString("description"));
                                answer.setTheme_id(result.getInt("theme_id"));
                                answer.setStart_date(result.getString("start_date"));
                                answer.setStudent_id(student_id);
                                answer.setSurvey_id(result.getInt("survey_id"));
                                int survey_rank = result.getInt("survey_rank") - 1;
                                if (last_survey_rank == survey_rank) {
                                    this.get(survey_rank).themes.add(answer);
                                } else {
                                    last_survey_rank = survey_rank;
                                    this.add(new ListStudentThemeAverage() {{
                                        this.themes = Lists.newArrayList(answer);
                                    }});
                                }
                            }
                        }
                    }
                ));
        } catch (SQLException e) {
            System.out.println("Error caught: " + e);
        }
        return Optional.empty();
    }

    public Optional<List<ListGroupThemeAverage>> getGroupThemeAverageProgression(int class_id,
            int group_id, int amount) {
        try {
            return Optional.of(DataBaseHelper.query(Database::getDBConnection, ""
                    + "SELECT * FROM (\n"
                    + "    SELECT\n"
                    + "        DENSE_RANK() OVER(ORDER BY su._id DESC) AS survey_rank,\n"
                    + "        avg(an.answer) as average,\n"
                    + "        su._id as survey_id,\n"
                    + "        th.title,\n"
                    + "        th.description,\n"
                    + "        th._id as theme_id,\n"
                    + "        su.start_date \n"
                    + "    FROM public.\"Surveys\" as su,\n"
                    + "         public.\"Answers\" as an,\n"
                    + "	        public.\"Student_Classes\" as sc,\n"
                    + "         public.\"Themes\" as th,\n"
                    + "	        public.\"Questions\" as qu \n"
                    + "    WHERE qu._id = an.question_id \n"
                    // TODO: Take only students that are in the group
                    + "      AND qu.theme_id = th._id \n"
                    + "      AND su._id = an.survey_id \n"
                    + "      AND sc.student_id = an.student_id\n"
                    + "      AND sc.group_id = ?\n"
                    + "      AND sc.class_id = ?\n"
                    + "    GROUP BY su._id, th._id\n"
                    + "    ORDER BY su.start_date DESC, th._id\n"
                    + ") x WHERE x.survey_rank <= ?",
                    select -> {
                        select.setInt(1, group_id);
                        select.setInt(2, class_id);
                        select.setInt(3, amount);
                    },
                    results -> new ArrayList<ListGroupThemeAverage>() {
                        {
                            int last_survey_rank = -2;
                            for (ResultSet result : results) {
                                GroupThemeAverage answer = new GroupThemeAverage();
                                answer.setAnswer(result.getFloat("average"));
                                answer.setTheme_title(result.getString("title"));
                                answer.setDescription(result.getString("description"));
                                answer.setTheme_id(result.getInt("theme_id"));
                                answer.setStart_date(result.getString("start_date"));
                                answer.setGroup_id(group_id);
                                answer.setSurvey_id(result.getInt("survey_id"));
                                int survey_rank = result.getInt("survey_rank") - 1;
                                if (last_survey_rank == survey_rank) {
                                    this.get(survey_rank).themes.add(answer);
                                } else {
                                    last_survey_rank = survey_rank;
                                    this.add(new ListGroupThemeAverage() {{
                                        this.themes = Lists.newArrayList(answer);
                                    }});
                                }
                            }
                        }
                    }
            ));
        } catch (SQLException e) {
            System.out.println("Error caught: " + e);
        }
        return Optional.empty();
    }

    public Optional<List<ListClassThemeAverage>> getClassThemeAverageProgression(int class_id, int amount) {
        try {
            return Optional.of(DataBaseHelper.query(Database::getDBConnection, ""
                    + "SELECT * FROM (\n"
                    + "    SELECT\n"
                    + "        DENSE_RANK() OVER(ORDER BY su._id DESC) AS survey_rank,\n"
                    + "        avg(an.answer) as average,\n"
                    + "        su._id as survey_id,\n"
                    + "        th.title,\n"
                    + "        th.description,\n"
                    + "        th._id as theme_id,\n"
                    + "        su.start_date \n"
                    + "    FROM public.\"Surveys\" as su,\n"
                    + "         public.\"Answers\" as an,\n"
                    + "	        public.\"Student_Classes\" as sc,\n"
                    + "         public.\"Themes\" as th,\n"
                    + "	        public.\"Questions\" as qu \n"
                    + "    WHERE qu._id = an.question_id \n"
                    + "      AND qu.theme_id = th._id \n"
                    + "      AND su._id = an.survey_id \n"
                    + "      AND sc.student_id = an.student_id\n"
                    + "      AND sc.class_id = ?\n"
                    + "    GROUP BY su._id, th._id\n"
                    + "    ORDER BY su.start_date DESC, th._id\n"
                    + ") x WHERE x.survey_rank <= ?",
                    select -> {
                        select.setInt(1, class_id);
                        select.setInt(2, amount);
                    },
                    results -> new ArrayList<ListClassThemeAverage>() {{
                        int last_survey_rank = -2;
                        for (ResultSet result : results) {
                            ClassThemeAverage answer = new ClassThemeAverage();
                            answer.setAnswer(result.getFloat("average"));
                            answer.setTheme_title(result.getString("title"));
                            answer.setDescription(result.getString("description"));
                            answer.setTheme_id(result.getInt("theme_id"));
                            answer.setStart_date(result.getString("start_date"));
                            answer.setClass_id(class_id);
                            answer.setSurvey_id(result.getInt("survey_id"));
                            int survey_rank = result.getInt("survey_rank") - 1;
                            if (last_survey_rank == survey_rank) {
                                this.get(survey_rank).themes.add(answer);
                            } else {
                                last_survey_rank = survey_rank;
                                this.add(new ListClassThemeAverage() {{
                                    this.themes = Lists.newArrayList(answer);
                                }});
                            }
                        }
                    }}));
        } catch (SQLException e) {
            System.out.println("Error caught: " + e);
            return Optional.empty();
        }
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
    
    public List<StudentThemeAverage> getStudentLifeTimeAverage(int student_id, int class_id){
    	ArrayList<StudentThemeAverage> answers = new ArrayList<StudentThemeAverage>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String select_averages = "SELECT avg(answer),\"Themes\".title,\"Themes\".description,\"Themes\"._id,\"Surveys\".start_date,\"Surveys\"._id,\"Surveys\".title,\"Surveys\".description,\"Surveys\".start_date,\"Surveys\".end_date "
            		+ "FROM public.\"Surveys\",public.\"Answers\", public.\"Student_Classes\",public.\"Groups\", public.\"Themes\", public.\"Questions\" "
            		+ "WHERE \"Questions\"._id = question_id "
            		+ "AND \"Questions\".theme_id = \"Themes\"._id "
            		+ "AND \"Answers\".student_id = ? "
            		+ "AND \"Groups\".class_id = ?"
            		+ "AND \"Surveys\"._id = \"Answers\".survey_id "
            		+ "GROUP BY \"Surveys\"._id,\"Themes\"._id,start_date "
            		+ "ORDER BY start_date DESC";
        	String select_total_average = "SELECT theme_id,avg(average_answer) "
        			+ "FROM (" + select_averages + ") "
        			+ "AS Averages GROUP BY theme_id";
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(select_averages)) {
                select.setInt(1, student_id);
                select.setInt(2, class_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        StudentThemeAverage answer = new StudentThemeAverage();
                        answer.setAnswer(result.getFloat(1));
                        answer.setTheme_title(result.getString(2));
                        answer.setDescription(result.getString(3));
                        answer.setTheme_id(result.getInt(4));
                        answer.setStart_date(result.getString(5));
                        answer.setStudent_id(student_id);
                        answer.setSurvey_id(-1);
                        answers.add(answer);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return answers;
    }

    public List<StudentThemeAverage> getStudentHistory(int student_id, int class_id){
    	ArrayList<StudentThemeAverage> answers = new ArrayList<StudentThemeAverage>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String select_averages = "SELECT avg(answer),\"Themes\".title,\"Themes\".description,\"Themes\"._id,\"Surveys\".start_date,\"Surveys\"._id,\"Surveys\".title,\"Surveys\".description,\"Surveys\".start_date,\"Surveys\".end_date "
            		+ "FROM public.\"Surveys\",public.\"Answers\", public.\"Student_Classes\",public.\"Groups\", public.\"Themes\", public.\"Questions\" "
            		+ "WHERE \"Questions\"._id = question_id "
            		+ "AND \"Questions\".theme_id = \"Themes\"._id "
            		+ "AND \"Answers\".student_id = ? "
            		+ "AND \"Groups\".class_id = ?"
            		+ "AND \"Surveys\"._id = \"Answers\".survey_id "
            		+ "GROUP BY \"Surveys\"._id,\"Themes\"._id,start_date "
            		+ "ORDER BY start_date DESC";
        	String select_total_average = "SELECT theme_id,avg(average_answer) "
        			+ "FROM (" + select_averages + ") "
        			+ "AS Averages GROUP BY theme_id";
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(select_averages)) {
                select.setInt(1, student_id);
                select.setInt(2, class_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                	ArrayList<Integer> if_list = new ArrayList<Integer>();
                    while (result.next()) {
                        StudentThemeAverage answer = new StudentThemeAverage();
                        //answer.setQuestion_id(result.getInt(1));
                        answer.setAnswer(result.getFloat(1));
                        answer.setTheme_title(result.getString(2));
                        answer.setDescription(result.getString(3));
                        answer.setTheme_id(result.getInt(4));
                        answer.setStart_date(result.getString(5));
                        answer.setStudent_id(student_id);
                        //answer.setSurvey_id(survey_id);
                        answers.add(answer);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return answers;
    	
    }
    
}
