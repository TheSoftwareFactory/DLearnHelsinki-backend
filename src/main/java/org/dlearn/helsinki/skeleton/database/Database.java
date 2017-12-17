package org.dlearn.helsinki.skeleton.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.dlearn.helsinki.skeleton.exceptions.GroupUpdateUnsuccessful;
import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.model.ChangePasswordStudent;
import org.dlearn.helsinki.skeleton.model.Classes;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.ListThemeAverage;
import org.dlearn.helsinki.skeleton.model.NewStudent;
import org.dlearn.helsinki.skeleton.model.NewTeacher;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.Researcher;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.StudentGroup;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.model.SurveyTheme;
import org.dlearn.helsinki.skeleton.model.Teacher;
import org.dlearn.helsinki.skeleton.model.Theme;
import org.dlearn.helsinki.skeleton.model.ThemeAverage;
import org.dlearn.helsinki.skeleton.security.Hasher;

public class Database {

    private static final Logger LOG = LogManager.getLogger(Database.class);

    private static final BasicDataSource DATA_SOURCE = new BasicDataSource();
    private static final String DB_DRIVER = "org.postgresql.Driver";

    private static final String DEV_DB_CONNECTION = "jdbc:postgresql://localhost:5432/Dlearn_db"
            + "?verifyServerCertificate=false" + "&useSSL=true"
            + "&useServerPrepStmts=false" + "&rewriteBatchedStatements=true";
    private static final String DEV_DB_USER = "postgres";
    private static final String DEV_DB_PASSWORD = "admin";
    private final Hasher HASHER = new Hasher();

    static {
        try {
            Class.forName(DB_DRIVER);
            DATA_SOURCE.setDriverClassName(DB_DRIVER);
            //DATA_SOURCE.setMaxIdle(20);
            //DATA_SOURCE.setMaxTotal(100);
            //DATA_SOURCE.setPoolPreparedStatements(true);
            DATA_SOURCE.setInitialSize(1);
            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            if (dbUrl == null) {
                System.out.println("Hello");
                dbUrl = DEV_DB_CONNECTION;
                DATA_SOURCE.setUsername(DEV_DB_USER);
                DATA_SOURCE.setPassword(DEV_DB_PASSWORD);
            } else {
                if (dbUrl.isEmpty()) {
                    String databaseUrl = System.getenv("DATABASE_URL");
                    if (databaseUrl.isEmpty()) {
                        dbUrl = DEV_DB_CONNECTION;
                        DATA_SOURCE.setUsername(DEV_DB_USER);
                        DATA_SOURCE.setPassword(DEV_DB_PASSWORD);
                    } else {
                        URI dbUri = new URI(databaseUrl);
                        dbUrl = "jdbc:postgresql://" + dbUri.getHost()
                                + dbUri.getPath();
                        if (dbUri.getUserInfo() != null) {
                            DATA_SOURCE.setUsername(
                                    dbUri.getUserInfo().split(":")[0]);
                            DATA_SOURCE.setPassword(
                                    dbUri.getUserInfo().split(":")[1]);
                        }
                    }
                }
            }
            DATA_SOURCE.setUrl(dbUrl);
        } catch (URISyntaxException | ClassNotFoundException e) {
            LOG.catching(Level.FATAL, e);
        }
    }

    /**
     * Used to check that database is available
     *
     * @throws java.lang.Exception
     */
    public void testConnection() throws Exception {
        LOG.traceEntry("Testing connection");
        try (Connection dbConnection = getDBConnection()) {
            LOG.debug("Database connection succeeded.");
            dbConnection.close();
        } catch (Exception e) {
            LOG.catching(e);
        }
        LOG.traceExit();
    }

    /**
     * Gets a survey from database by survey_id.
     *
     * @param survey_id
     * @return Survey
     */
    public Survey getSurvey(int survey_id) {
        Survey s = null;
        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select * FROM public.\"Surveys\" WHERE _id = ?";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, survey_id);
                ResultSet result = select.executeQuery();
                if (!result.next()) {
                    return s;
                }
                s = new Survey(result.getInt("_id"), result.getString("title"),
                        result.getString("title_fi"),
                        result.getString("description"),
                        result.getString("description_fi"),
                        result.getTimestamp("start_date"),
                        result.getTimestamp("end_date"),
                        result.getInt("teacher_id"), result.getInt("class_id"),
                        result.getBoolean("open"));

            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(s);
        return s;
    }

    /**
     * Adds a new survey to database, and returns it with the database ID
     *
     * @param surveyTheme
     * @return surveyTheme
     * @throws SQLException
     */
    public SurveyTheme postSurvey(SurveyTheme surveyTheme) throws SQLException {
        LOG.traceEntry("Posting survey {}", surveyTheme);
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Surveys\" (title, title_fi, class_id, start_date, teacher_id, description, description_fi, open) "
                    + "VALUES (?,?,?,now(),?,?,?,True) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, surveyTheme.title);
                insert.setString(2, surveyTheme.title_fi);
                insert.setInt(3, surveyTheme.getClass_id());
                //insert.setDate(3, new Date(0));
                insert.setInt(4, surveyTheme.getTeacher_id());
                insert.setString(5, surveyTheme.description);
                insert.setString(6, surveyTheme.description_fi);
                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        surveyTheme.set_id(result.getInt("_id"));
                    } else {
                        LOG.error("Inserting survey didn't return ID of it.");
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(surveyTheme);
        return surveyTheme;
    }

    /**
     * Get questions for a theme
     *
     * @param surveyTheme
     * @return Questions
     */
    public List<Question> getQuestions(SurveyTheme surveyTheme) {
        LOG.traceEntry();
        ArrayList<Question> questions = new ArrayList<>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            // lame fix TODO check that there is at least one theme chosen
            String statement = "Select * FROM public.\"Questions\" WHERE theme_id = ?";
            int count = 1;
            for (int i = 1; i < surveyTheme.theme_ids.size(); i++) {
                statement += " OR theme_id = ?";
                count++;
                System.out.println(count);
            }
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                count = 0;
                for (int i = 0; i < surveyTheme.theme_ids.size(); i++) {
                    count++;
                    System.out.println(count);
                    select.setInt(i + 1, surveyTheme.theme_ids.get(i));
                }
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Question question = new Question();
                        question.setQuestion(result.getString(1));
                        question.setMin_answer(result.getInt(2));
                        question.setMax_answer(result.getInt(3));
                        question.set_id(result.getInt(4));
                        questions.add(question);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(questions);
        return questions;
    }

    /**
     * Add a question to database
     *
     * @param question
     * @return
     * @throws SQLException
     */
    public Question postQuestion(Question question) throws SQLException {
        LOG.traceEntry("Posting question {}", question);
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Questions\" (question, question_fi, min_answer, max_answer) "
                    + "VALUES (?, ?, ?, ?) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, question.question);
                insert.setString(2, question.question_fi);
                insert.setInt(3, question.min_answer);
                insert.setInt(4, question.max_answer);
                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        question.set_id(result.getInt("_id"));
                    } else {
                        LOG.error("Inserting question didn't return ID of it.");
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(question);
        return question;
    }

    /**
     * Adds a set of questions to a survey
     *
     * @param questions
     * @param survey
     */
    public void postSurveyQuestions(List<Question> questions, Survey survey) {
        LOG.traceEntry("Posting questions {} for survey {}", questions, survey);
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Survey_questions\" (survey_id, question_id) "
                    + "VALUES (?,?)";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                // prepare batch
                for (Question question : questions) {
                    insert.setInt(1, survey.get_id());
                    insert.setInt(2, question.get_id());
                    insert.addBatch();
                }
                // execute query
                insert.executeBatch();
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit();
    }

    /**
     *
     * @param survey_id id of survey
     * @return all the questions set to that survey
     */
    public List<Question> getQuestionsFromSurvey(int survey_id) {
        LOG.traceEntry("Getting the questions from the survey {}", survey_id);
        ArrayList<Question> questions = new ArrayList<>();

        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "Select _id, question, question_fi, min_answer, max_answer FROM \"Questions\", \"Survey_questions\" WHERE"
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
                        question.setQuestion_fi(result.getString(3));
                        question.setMin_answer(result.getInt(4));
                        question.setMax_answer(result.getInt(5));
                        questions.add(question);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(questions);
        return questions;
    }

    /**
     * Adds students answers.
     *
     * @param class_id
     * @param survey_id
     * @param student_id
     * @param answers
     * @return
     */
    public boolean postStudentAnswersForSurvey(int class_id, int survey_id,
            int student_id, List<Answer> answers) {

        for (Answer answer : answers) {
            System.out.println("putting answer");
            this.putAnswerToQuestion(answer, class_id);
        }

        return true;

    }

    /**
     * Gets surveys for a student form a class
     *
     * @param student_id
     * @param class_id
     * @return a list of surveys available to the student
     * @throws SQLException
     */
    public List<Survey> getSurveysFromClassAsStudent(int student_id,
            int class_id) throws SQLException {
        LOG.traceEntry("Getting all surveys from class {} as student {}",
                class_id, student_id);
        ArrayList<Survey> surveys = new ArrayList<>();

        try (Connection dbConnection = getDBConnection()) {
            //1     2        3           4              5          6        7    8         9
            String statement = "SELECT distinct _id,title,title_fi,description,description_fi,start_date,end_date,open,teacher_id "
                    + "FROM public.\"Surveys\" " + "WHERE class_id = ? ";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Survey survey = new Survey();
                        survey.set_id(result.getInt(1));
                        survey.setTitle(result.getString(2));
                        survey.setTitle_fi(result.getString(3));
                        survey.setDescription(result.getString(4));
                        survey.setDescription_fi(result.getString(5));
                        survey.setStart_date(result.getTimestamp(6));
                        result.getTimestamp(7);
                        if (!result.wasNull()) {
                            survey.setEnd_date(result.getTimestamp(7));
                        }
                        survey.setOpen(result.getBoolean(8));
                        survey.setClass_id(class_id);
                        survey.setTeacher_id(result.getInt(9));
                        surveys.add(survey);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(surveys);
        return surveys;
    }

    /**
     *
     * @param teacher_id
     * @param class_id
     * @return a list of surveys.
     * @throws SQLException
     */
    public List<Survey> getSurveysFromClassAsTeacher(int teacher_id,
            int class_id) throws SQLException {
        LOG.traceEntry("Getting surveys from class {} as teacher {}", class_id,
                teacher_id);
        ArrayList<Survey> surveys = new ArrayList<>();

        try (Connection dbConnection = getDBConnection()) {
            String statement = "SELECT _id,title, title_fi, description, description_fi, start_date,end_date,open "
                    + "FROM public.\"Surveys\" "
                    + "WHERE class_id = ? AND teacher_id = ?";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);
                select.setInt(2, teacher_id);

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Survey survey = new Survey();
                        survey.set_id(result.getInt(1));
                        survey.setTitle(result.getString(2));
                        survey.setTitle_fi(result.getString(3));
                        survey.setDescription(result.getString(4));
                        survey.setDescription_fi(result.getString(5));
                        survey.setStart_date(result.getTimestamp(6));

                        if (!result.getBoolean(8)) {
                            survey.setEnd_date(result.getTimestamp(7));
                        }
                        survey.setOpen(result.getBoolean(8));
                        survey.setClass_id(class_id);
                        survey.setTeacher_id(teacher_id);
                        surveys.add(survey);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(surveys);
        return surveys;
    }

    /**
     *
     * @return all surveys
     */
    public List<Survey> getSurveys() {
        LOG.traceEntry("Getting all surveys");
        List<Survey> survey = new ArrayList<>();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "SELECT _id, title, title_fi, class_id, start_date, end_date, teacher_id, description, description_fi open FROM public.\"Surveys\"";

            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        survey.add(new Survey() {
                            {
                                this._id = result.getInt("_id");
                                this.title = result.getString("title");
                                this.title_fi = result.getString("title_fi");
                                this.class_id = result.getInt("class_id");
                                this.start_date = result
                                        .getTimestamp("start_date");
                                this.end_date = result.getTimestamp("end_date");
                                this.teacher_id = result.getInt("teacher_id");
                                this.description = result
                                        .getString("description");
                                this.description_fi = result
                                        .getString("description_fi");
                                this.open = result.getBoolean("open");
                            }
                        });
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(survey);
        return survey;
    }

    /**
     * Gets group info of a student
     *
     * @param class_id
     * @param student_id
     * @return Group
     */
    public Optional<Group> getGroupForStudent(int class_id, int student_id) {
        LOG.traceEntry("Getting groups for student {} in class {}", student_id,
                class_id);
        try (Connection dbConnection = getDBConnection()) {
            String statement = "" + "SELECT sc.group_id, g.name, g.open "
                    + "  FROM public.\"Student_Classes\" as sc,"
                    + "       public.\"Groups\" as g"
                    + "  WHERE sc.group_id = g._id"
                    + "    AND sc.class_id = g.class_id"
                    + "    AND sc.student_id = ?" + "    AND sc.class_id = ?"
                    + "  ORDER BY sc.creation_date DESC" + "  LIMIT 1";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, student_id);
                select.setInt(2, class_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Group group = new Group() {
                            {
                                this.set_id(result.getInt("group_id"));
                                this.setClass_id(class_id);
                                this.setName(result.getString("name"));
                                this.setOpen(result.getBoolean("open"));
                            }
                        };
                        LOG.traceExit(group);
                        return Optional.of(group);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit("No group");
        return Optional.empty();
    }

    /**
     * Gets all groups student belongs to
     *
     * @param student_id
     * @return Groups
     */
    public List<Group> getAllGroupsForStudent(int student_id) {
        LOG.traceEntry("Getting all groups for student {}", student_id);
        ArrayList<Group> groups = new ArrayList<>();

        try (Connection dbConnection = getDBConnection()) {
            String statement = ""
                    + "SELECT sc.group_id, g.name, g.class_id, g.open "
                    + "  FROM public.\"Student_Classes\" as sc,"
                    + "       public.\"Groups\" as g"
                    + "  WHERE sc.group_id = g._id"
                    + "    AND sc.class_id = g.class_id"
                    + "    AND sc.student_id = ?" + "    AND sc.class_id = ?";
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
                        group.setOpen(result.getBoolean("open"));
                        groups.add(group);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(groups);
        return groups;
    }

    /**
     * Get all groups in a class
     *
     * @param class_id
     * @param all
     * @return Groups
     */
    public List<Group> getAllGroupsFromClass(int class_id, boolean all) {
        LOG.traceEntry("Getting groups for the class {}", class_id);
        ArrayList<Group> groups = null;

        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select name, _id, class_id, open "
                    + "FROM public.\"Groups\" " + "WHERE (class_id = ?) ";
            if (!all) {
                statement += "AND (open = true)";
            }
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
                        group.setOpen(result.getBoolean("open"));
                        groups.add(group);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(groups);
        return groups;
    }

    /**
     *
     * @param class_id
     * @param group_id
     * @return
     */
    public Group getGroupFromClass(int class_id, int group_id) {
        LOG.traceEntry("Getting group class {}", class_id);
        Group group = null;

        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select name, open FROM public.\"Groups\" WHERE (class_id = ?) and (_id = ?);";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);
                select.setInt(2, group_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    if (result.next()) {
                        group = new Group(group_id, result.getString("name"),
                                class_id, result.getBoolean("open"));
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(group);
        return group;
    }

    /**
     * Get all students in a class
     *
     * @param class_id
     * @param group_id
     * @return Students
     */
    public List<Student> getAllStudentsFromClassAndGroup(int class_id,
            int group_id) {
        LOG.traceEntry("Getting groups for the class {}", group_id);
        ArrayList<Student> students = null;

        try (Connection dbConnection = getDBConnection()) {
            // TODO update request to remove class_id
            String statement = ""
                    + "SELECT s._id, s.username, s.gender, s.age\n"
                    + "  FROM public.\"Student_Classes\" as sc,\n"
                    + "       public.\"Students\" as s \n"
                    + " WHERE sc.student_id = s._id\n"
                    + "   AND sc.class_id = ?\n" + "   AND sc.group_id = ?\n"
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
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(students);
        return students;
    }

    /**
     * Creates a new student into DB
     *
     * @param new_student
     * @return Student
     */
    public Optional<Student> createStudent(NewStudent new_student) {
        LOG.traceEntry("Creating student {}", new_student);
        Optional<Student> student = Optional.empty();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Students\" (username, pwd, gender, age) "
                    + "VALUES (?,?,?,?) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, new_student.student.username);
                insert.setString(2, HASHER.encode(new_student.password));
                insert.setString(3, new_student.student.gender);
                insert.setInt(4, new_student.student.age);

                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        new_student.student._id = result.getInt("_id");
                        student = Optional.of(new_student.student);
                    } else {
                        LOG.error("Inserting student didn't return ID of it.");
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(student);
        return student;
    }

    /**
     * Create new teacher into DB
     *
     * @param new_teacher
     * @return Teacher
     */
    public Teacher createTeacher(NewTeacher new_teacher) {
        LOG.traceEntry("Creating new teacher {}", new_teacher);
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Teachers\" (username, pwd, firstname, lastname) "
                    + "VALUES (?,?,?,?) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, new_teacher.teacher.username);
                insert.setString(2, HASHER.encode(new_teacher.password));
                insert.setString(3, new_teacher.teacher.name);
                insert.setString(4, new_teacher.teacher.lastname);
                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        new_teacher.teacher._id = result.getInt("_id");
                    } else {
                        LOG.error("Inserting teacher didn't return ID of it.");
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return new_teacher.teacher;
    }

    /**
     * Get all teachers
     *
     * @return Teachers
     */
    public List<Teacher> getTeachers() {
        LOG.traceEntry("Getting all teachers ");
        List<Teacher> teachers = null;
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "" + "SELECT s._id,\n" + "       s.username,\n"
                    + "       s.firstname,\n" + "       s.lastname\n"
                    + "  FROM public.\"Teachers\" as s\n";

            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                // execute query
                teachers = new ArrayList<>();

                try (ResultSet result = insert.executeQuery()) {
                    while (result.next()) {
                        Teacher teacher = new Teacher(result.getInt("_id"),
                                result.getString("username"),
                                result.getString("firstname"),
                                result.getString("lastname"));
                        teachers.add(teacher);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(teachers);
        return teachers;
    }

    /**
     * Create new theme
     *
     * @param theme
     * @return
     */
    public Theme createTheme(Theme theme) {
        LOG.traceEntry("Creating new theme{}", theme);
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Themes\" (title, title_fi, description, description_fi) "
                    + "VALUES (?,?,?,?) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, theme.getTitle());
                insert.setString(2, theme.getTitle_fi());
                insert.setString(3, theme.getDescription());
                insert.setString(4, theme.getDescription_fi());

                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        theme.setId(result.getInt("_id"));
                    } else {
                        LOG.error("Inserting teacher didn't return ID of it.");
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return theme;
    }

    public List<Question> getQuestions() {
        LOG.traceEntry("Getting all questions ");
        List<Question> questions = null;
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "" + "SELECT qu.*, th.title "
                    + "FROM public.\"Questions\" as qu "
                    + "INNER JOIN public.\"Themes\" as th ON th._id=qu.theme_id "
                    + "ORDER BY qu.theme_id, qu._id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                // execute query
                questions = new ArrayList<>();

                try (ResultSet result = insert.executeQuery()) {
                    while (result.next()) {
                        Question question = new Question(result.getInt("_id"),
                                result.getString("question"),
                                result.getString("question_fi"),
                                result.getInt("min_answer"),
                                result.getInt("max_answer"),
                                result.getInt("theme_id"),
                                result.getString("title"));
                        questions.add(question);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(questions);
        return questions;

    }

    /**
     * Get all themes
     *
     * @return
     */
    public List<Theme> getThemes() {
        LOG.traceEntry("Getting all themes");
        List<Theme> themes = null;
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "" + "SELECT s._id,\n" + "       s.title,\n"
                    + "       s.title_fi,\n" + "       s.description,\n"
                    + "       s.description_fi\n"
                    + "  FROM public.\"Themes\" as s\n";

            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                // execute query
                themes = new ArrayList<>();

                try (ResultSet result = insert.executeQuery()) {
                    while (result.next()) {
                        Theme theme = new Theme(result.getInt("_id"),
                                result.getString("title"),
                                result.getString("title_fi"),
                                result.getString("description"),
                                result.getString("description_fi"));
                        themes.add(theme);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(themes);
        return themes;
    }

    /**
     * Create new question into DB
     *
     * @param question
     * @return question, with id
     */
    public Question createQuestion(Question question) {
        LOG.traceEntry("Creating new question{}", question);
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Questions\" (question, question_fi, min_answer, max_answer, theme_id) "
                    + "VALUES (?,?,?,?,?) RETURNING _id";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, question.getQuestion());
                insert.setString(2, question.getQuestion_fi());
                insert.setInt(3, question.getMin_answer());
                insert.setInt(4, question.getMax_answer());
                insert.setInt(5, question.get_theme_id());

                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        question.set_id(result.getInt("_id"));
                    } else {
                        LOG.error("Inserting Question didn't return ID of it.");
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return question;
    }

    /**
     * Change student password into DB
     *
     * @param student
     * @return Student
     */
    public Optional<Student> changeStudentPassword(
            ChangePasswordStudent student) {
        LOG.traceEntry("Changing students password {}", student);
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "UPDATE public.\"Students\" SET pwd = ? WHERE _id = ?";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, HASHER.encode(student.password));
                insert.setInt(2, student.student_id);

                // execute query
                insert.execute();
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
            LOG.traceExit("No password change");
            return Optional.empty();
        }
        Student result = getStudent(student.student_id);
        LOG.traceExit(result);
        return Optional.ofNullable(result);
    }

    /**
     * Add student to a group
     *
     * @param student
     * @param class_id
     * @param group_id
     * @return boolean
     */
    public boolean addStudentToGroup(Student student, int class_id,
            int group_id) {
        LOG.traceEntry("Adding student {} to group {} in class {}", student,
                group_id, class_id);
        try (Connection dbConnection = getDBConnection()) {
            if (!DataBaseHelper.doesGroupClassMatch(dbConnection, group_id,
                    class_id)) {
                LOG.traceExit("Group and class didn't match");
                return false;
            }
            String statement = "INSERT INTO public.\"Student_Classes\" (student_id, class_id, group_id) "
                    + "VALUES (?,?,?)";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setInt(1, student._id);
                insert.setInt(2, class_id);
                insert.setInt(3, group_id);

                // execute query
                insert.executeUpdate();
                LOG.traceExit("Adding successful");
                dbConnection.close();
                return true;
            }

        } catch (SQLException e) {
            LOG.catching(e);
            LOG.traceExit("Adding failed");
            return false;
        }
    }

    /**
     * Remove student from a group
     *
     * @param class_id
     * @param group_id
     * @param student_id
     * @return
     */
    public boolean removeStudentFromGroup(int class_id, int group_id,
            int student_id) {
        boolean success = false;
        try (Connection dbConnection = getDBConnection()) {
            if (!DataBaseHelper.doesGroupClassMatch(dbConnection, group_id,
                    class_id)) {
                LOG.traceExit("Group and class didn't match");
                return success;
            }
            String statement = "DELETE FROM public.\"Student_Classes\""
                    + "WHERE student_id = ?" + "AND class_id = ?"
                    + "AND group_id = ?";

            try (PreparedStatement delete = dbConnection
                    .prepareStatement(statement)) {
                delete.setInt(1, student_id);
                delete.setInt(2, class_id);
                delete.setInt(3, group_id);

                // execute query
                int count = delete.executeUpdate();
                if (count > 0) {
                    success = true;
                }
                LOG.traceExit("Deletion successful");
                dbConnection.close();
                return success;
            }

        } catch (SQLException e) {
            LOG.catching(e);
            LOG.traceExit("Deletion failed");
            return success;
        }
    }

    /**
     *
     * @param studentID
     * @return Student
     */
    public Student getStudent(int studentID) {
        LOG.traceEntry("Getting student {}", studentID);
        Student student = null;

        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select username, gender, age FROM public.\"Students\" WHERE _id = ?";
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
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(student);
        return student;
    }

    /**
     * Get all students in class <class_id>
     *
     * @param class_id
     * @return Students
     */
    public Optional<List<Student>> getAllStudentsFromClass(int class_id) {
        LOG.traceEntry("Getting all students from class {}", class_id);
        try {
            List<Student> result = DataBaseHelper.query(
                    Database::getDBConnection,
                    "" + "SELECT st._id,\n" + "       st.username,\n"
                            + "       st.gender,\n" + "       st.age\n"
                            + "  FROM public.\"Groups\" AS gr,\n"
                            + "       public.\"Student_Classes\" AS sc,\n"
                            + "       public.\"Students\" AS st\n"
                            + " WHERE st._id = sc.student_id\n"
                            + "   AND gr._id = sc.group_id\n"
                            + "   AND gr.class_id = ?\n"
                            + "   AND sc._id = (SELECT sc2._id\n"
                            + "                   FROM public.\"Student_Classes\" as sc2\n"
                            + "                  WHERE sc.student_id = sc2.student_id\n"
                            + "                 ORDER BY sc2.creation_date DESC\n"
                            + "                 LIMIT 1)"
                            + "ORDER BY st.username",
                    select -> select.setInt(1, class_id), results -> {
                        List<Student> students = new ArrayList<>();
                        for (ResultSet r : results) {
                            students.add(new Student() {
                                {
                                    _id = r.getInt("_id");
                                    age = r.getInt("age");
                                    username = r.getString("username");
                                    gender = r.getString("gender");
                                }
                            });
                        }
                        return students;
                    });
            LOG.traceExit(result);
            return Optional.of(result);
        } catch (SQLException e) {
            LOG.catching(e);
            LOG.traceExit("No students returned");
            return Optional.empty();
        }
    }

    /**
     * Get Student by username
     *
     * @param username_
     * @return Student
     */
    public Optional<Student> getStudentFromUsername(String username_) {
        LOG.traceEntry("Getting student from the username {}", username_);
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
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(student);
        return student;
    }

    /**
     * Get Teacher by username
     *
     * @param username_
     * @return Teacher
     */
    public Optional<Teacher> getTeacherFromUsername(String username_) {
        LOG.traceEntry("Getting teacher from username {}", username_);
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
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(teacher);
        return teacher;
    }

    /**
     * Get Researcher by username
     *
     * @param username_
     * @return Researcher
     */
    public Optional<Researcher> getResearcherFromUsername(String username_) {
        LOG.traceEntry("Getting researcher from username {}", username_);
        Optional<Researcher> researcher = Optional.empty();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select _id FROM public.\"Researchers\" WHERE username = ?";
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
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(researcher);
        return researcher;
    }

    /**
     * Check for username in DB
     *
     * @param student
     * @return boolean
     */
    public boolean doesStudentUsernameExistInDatabase(Student student) {
        LOG.traceEntry("Checking if student {} exists in database", student);
        boolean exists = false;
        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select username FROM public.\"Students\" as std WHERE std.username = ?";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setString(1, student.getUsername());
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    exists = result.next();
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(exists);
        return exists;
    }

    /**
     * Get classes for a teacher
     *
     * @param teacher_id
     * @return Classes
     */
    public List<Classes> getAllClassesOfTeacher(int teacher_id) {
        LOG.traceEntry("Getting all classes of teacher {}", teacher_id);
        List<Classes> classes = null;

        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select _id, name, name_fi "
                    + "FROM public.\"Classes\" as cls "
                    + "WHERE (cls.teacher_id = ?);";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, teacher_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    classes = new ArrayList<>();
                    while (result.next()) {
                        Classes newClass = new Classes();
                        newClass.set_id(result.getInt("_id"));
                        newClass.setName(result.getString("name"));
                        newClass.setName_fi(result.getString("name_fi"));
                        newClass.setTeacher_id(teacher_id);
                        classes.add(newClass);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(classes);
        return classes;
    }

    /**
     * Get classes where a student student_id is in.
     *
     * @param student_id
     * @return Classes
     */
    public List<Classes> getAllClassesStundentIsIn(int student_id) {
        LOG.traceEntry("Getting all classes student {} is in", student_id);
        List<Classes> classes = null;

        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select cls._id, cls.name, cls.teacher_id "
                    + "FROM public.\"Groups\" as gr "
                    + "INNER JOIN public.\"Student_Classes\" as st_cls "
                    + "ON st_cls.group_id = gr._id "
                    + "INNER JOIN public.\"Classes\" as cls "
                    + "ON cls._id = gr.class_id "
                    + "WHERE (st_cls.student_id = ?);";
            //prepare statement with student_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, student_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    classes = new ArrayList<>();
                    while (result.next()) {
                        Classes newClass = new Classes();
                        newClass.set_id(result.getInt("_id"));
                        newClass.setName(result.getString("name"));
                        newClass.setTeacher_id(result.getInt("teacher_id"));
                        classes.add(newClass);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(classes);
        return classes;
    }

    /**
     * Close Group
     *
     * @param group_id
     */
    public void closeGroup(int group_id) {
        try (Connection dbConnection = getDBConnection()) {
            String update_statement = "UPDATE public.\"Groups\" "
                    + "SET open = false " + "WHERE (_id = ?);";
            try (PreparedStatement update = dbConnection
                    .prepareStatement(update_statement)) {
                update.setInt(1, group_id);
                // execute query
                update.executeUpdate();
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * check if group exists
     *
     * @param group_id
     * @return boolean
     */
    public boolean doesGroupExistInDatabase(int group_id) {
        boolean exists = false;
        try (Connection dbConnection = getDBConnection()) {
            String statement = "Select username FROM public.\"Groups\" as std WHERE std.username = ?";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, group_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    if (result.next()) {
                        exists = true;
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exists;
    }

    /**
     * change group name
     *
     * @param class_id
     * @param group_id
     * @param group
     */
    public void updateGroupName(int class_id, int group_id, Group group) {
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "UPDATE public.\"Groups\" "
                    + "SET name = ? WHERE (_id = ?) and (class_id = ?);";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, group.getName());
                insert.setInt(2, group_id);
                insert.setInt(3, class_id);
                // execute query
                int count = insert.executeUpdate();
                if (count == 0) {
                    // update unsuccessful
                    throw new GroupUpdateUnsuccessful();
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    /**
     * connect to DB
     *
     * @return
     */
    private static Connection getDBConnection() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            LOG.catching(Level.FATAL, e);
            return null;
        }
    }

    /**
     * Insert an answer into the database
     *
     * @param answer
     * @param class_id
     */
    public void putAnswerToQuestion(Answer answer, int class_id) {
        LOG.traceEntry("Adding answer {}", answer);
        // finding his group_id
        Group group = getGroupForStudent(class_id, answer.student_id)
                .orElse(null);
        int group_id = -1;
        if (group != null) {
            group_id = group._id;
        }
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Answers\" "
                    + "(question_id, student_id, answer, survey_id, group_id)"
                    + "VALUES (?, ?, ?, ?, ?) "
                    + "ON CONFLICT (question_id,student_id,survey_id) DO UPDATE SET answer = ?";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setInt(1, answer.question_id);
                insert.setInt(2, answer.student_id);
                insert.setInt(3, answer.answer);
                insert.setInt(4, answer.survey_id);
                insert.setInt(5, group_id);
                insert.setInt(6, answer.answer);
                // execute query
                insert.executeUpdate();
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit();
    }

    /**
     * Get answers for a survey survey_id from a student student_id
     *
     * @param student_id
     * @param survey_id
     * @return Answers
     */
    public List<Answer> getAnswersFromStudentSurvey(int student_id,
            int survey_id) {
        LOG.traceEntry("Getting answers from student {} survey {}", student_id,
                survey_id);
        ArrayList<Answer> answers = new ArrayList<>();
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
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(answers);
        return answers;
    }

    /**
     * Get students in class, and their groups
     *
     * @param _class_id
     * @param all
     * @return
     */
    public List<StudentGroup> getGroupsWithStudents(int _class_id,
            boolean all) {
        LOG.traceEntry("Getting groups and students in class {}", _class_id);
        Map<Integer, StudentGroup> studentGroups = new TreeMap<>();
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement;
            if (all) {
                statement = ""
                        // Select all open groups with students 
                        + "SELECT g._id as group_id,\n"
                        + "       g.name as group_name,\n"
                        + "       g.open as group_open,\n"
                        + "       s._id as student_id,\n"
                        + "       s.username,\n" + "       s.gender,\n"
                        + "       s.age,\n" + "		  0 as EmptyStudents\n"
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
                        + "                 LIMIT 1) \n"
                        // Select all closed groups (without students, 
                        //		because a group with students can not be closed )
                        + " UNION \n" + "SELECT g2._id as group_id,\n"
                        + "       g2.name as group_name,\n"
                        + "       g2.open as group_open,\n"
                        + "       0 as student_id,\n"
                        + "       '' as username,\n" + "       '' as gender,\n"
                        + "       0 as age,\n" + "		  1 as EmptyStudents\n"
                        + "  FROM public.\"Groups\" as g2 \n"
                        + " WHERE (g2.class_id = ?) "
                        + "	  AND (g2.open = false);";
            } else {
                statement = ""
                        // Select all open groups with students 
                        + "SELECT g._id as group_id,\n"
                        + "       g.name as group_name,\n"
                        + "       g.open as group_open,\n"
                        + "       s._id as student_id,\n"
                        + "       s.username,\n" + "       s.gender,\n"
                        + "       s.age,\n" + "		  0 as EmptyStudents\n"
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
            }
            //prepare statement with survey_id
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                if (all) {
                    select.setInt(1, _class_id);
                    select.setInt(2, _class_id);
                } else {
                    select.setInt(1, _class_id);
                }

                // execute query
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        String group_name = result.getString("group_name");
                        int student_id = result.getInt("student_id");
                        String _username = result.getString("username");
                        String _gender = result.getString("gender");
                        int _age = result.getInt("age");
                        boolean group_open = result.getBoolean("group_open");
                        int noStudent = result.getInt("EmptyStudents");
                        // add Student to Group
                        studentGroups.compute(result.getInt("group_id"),
                                (group_id, group) -> {
                                    if (group == null) {
                                        group = new StudentGroup() {
                                            {
                                                this._id = group_id;
                                                this.name = group_name;
                                                this.open = group_open;
                                                this.class_id = _class_id;
                                            }
                                        };
                                    }
                                    if (noStudent == 0) {
                                        group.students.add(new Student() {
                                            {
                                                this._id = student_id;
                                                this.username = _username;
                                                this.gender = _gender;
                                                this.age = _age;
                                            }
                                        });
                                    }
                                    ;
                                    return group;

                                });
                    }
                    //hotfix TODO refactor
                    // adding missing groups
                    for (Group group : getAllGroupsFromClass(_class_id, all)) {
                        if (!studentGroups.containsKey(group._id)) {
                            StudentGroup gr = new StudentGroup();
                            gr._id = group._id;
                            gr.name = group.name;
                            studentGroups.put(group._id, gr);
                        }
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(studentGroups);
        return studentGroups.values().stream().collect(Collectors.toList());
    }

    public List<Survey> getStudentsAnsweredSurveys(int student_id) {
        List<Survey> surveys = new ArrayList<>();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "" + "SELECT DISTINCT survey_id \n"
                    + "FROM \"Answers\" \n" + "WHERE student_id=? \n"
                    + "ORDER BY survey_id DESC";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, student_id);
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        surveys.add(this.getSurvey(result.getInt("survey_id")));
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return surveys;
    }

    // class_id > 0 required, (<=0 results ambigious query)
    // (group_id <= 0) results one class' surveys
    public List<Survey> getGroupAnsweredSurveys(int class_id, int group_id) {
        List<Survey> surveys = new ArrayList<>();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "SELECT DISTINCT survey_id \n"
                    + "FROM \"Answers\" AS an \n"
                    + "INNER JOIN \"Surveys\" AS su ON an.survey_id=su._id \n"
                    + "WHERE su.class_id=? \n"
                    + ((group_id > 0) ? " AND an.group_id=?" : "");
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);
                if (group_id > 0) {
                    select.setInt(2, group_id);
                }
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        surveys.add(this.getSurvey(result.getInt("survey_id")));
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return surveys;
    }

    /**
     * Get average progression for a student
     *
     * @param std_id student_id
     * @param cls_id class_id (<=0 results all classes)
     * @param amount ( some legacy variable ? )
     * @return List with theme averages and surveys
     */
    public List<ListThemeAverage> getStudentThemeAverageProgression(int std_id,
            int cls_id, int amount) {
        LOG.traceEntry("Getting progression of {} for student {}", amount,
                std_id);
        List<ListThemeAverage> progression = new ArrayList<>();
        List<Survey> surveys = this.getStudentsAnsweredSurveys(std_id);
        for (Survey survey : surveys) {
            List<ThemeAverage> avgs;
            if (survey.class_id == cls_id || cls_id <= 0) {
                avgs = this.getSurveyAnswerAverages(std_id, cls_id, 0, survey._id);
                progression.add(new ListThemeAverage(avgs, survey));
            }
        }
        return progression;
    }

    /**
     * get group progression
     *
     * @param cls_id class_id, > 0 required
     * @param grp_id group_id, ( <=0 results all groups )
     * @param amount
     * @return
     */
    public List<ListThemeAverage> getGroupThemeAverageProgression(int cls_id,
            int grp_id, int amount) {
        LOG.traceEntry("Getting progression of {} for group {} in class {}",
                amount, grp_id, cls_id);
        List<ListThemeAverage> progression = new ArrayList<>();
        List<Survey> surveys = this.getGroupAnsweredSurveys(cls_id, grp_id);
        for (Survey survey : surveys) {
            List<ThemeAverage> avgs = this.getSurveyAnswerAverages(0,
                    cls_id, grp_id, survey._id);
            progression.add(new ListThemeAverage(avgs, survey));
        }
        System.out.println(progression);
        return progression;
    }

    /**
     * close survey
     *
     * @param teacher_id
     * @param class_id
     * @param survey_id
     */
    public void closeSurvey(int teacher_id, int class_id, int survey_id) {
        LOG.traceEntry("Closing survey {} for teacher {} in class {}",
                survey_id, teacher_id, class_id);
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
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit();
    }

    /**
     * check that a group group_id belongs to a class class_id
     *
     * @param group_id
     * @param class_id
     * @return
     */
    public boolean doesGroupClassMatch(int group_id, int class_id) {
        try (Connection dbConnection = getDBConnection()) {
            return DataBaseHelper.doesGroupClassMatch(dbConnection, group_id,
                    class_id);
        } catch (SQLException e) {
            LOG.catching(e);
            return false;
        }
    }

    /**
     * check if grope is closed
     *
     * @param group_id
     * @return boolean
     */
    public boolean isGroupClosed(int group_id) {
        LOG.traceEntry("Checking if group {} is closed", group_id);
        // If group is closed, it is empty.
        boolean closed = false;
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String select_open = "SELECT open " + "FROM public.\"Groups\" "
                    + "WHERE _id = ?; ";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(select_open)) {
                select.setInt(1, group_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    if (result.next()) {
                        closed = result.getBoolean("open");
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(closed);
        return closed;
    }

    /**
     * Check how many students are in a group group_id
     *
     * @param group_id
     * @return
     */
    public int countNumberOfStudentsInGroup(int group_id) {
        LOG.traceEntry("Counting how many students are in group {}", group_id);
        int count = 0;
        try (Connection dbConnection = getDBConnection()) {
            String select_students_count = "SELECT COUNT(t1.group_id) "
                    + "FROM public.\"Student_Classes\" AS t1 " + "INNER JOIN ("
                    + "		SELECT student_id, class_id, MAX(creation_date) as maxdate "
                    + "		FROM public.\"Student_Classes\" "
                    + "		GROUP BY student_id, class_id" + ") AS t2 "
                    + "ON t1.student_id = t2.student_id "
                    + "	AND t1.class_id = t2.class_id "
                    + "	AND t1.creation_date = t2.maxdate "
                    + "WHERE t1.group_id = ?;";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(select_students_count)) {
                select.setInt(1, group_id);
                // execute query
                try (ResultSet result = select.executeQuery()) {
                    if (result.next()) {
                        count = result.getInt(1);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(count);
        return count;
    }

    /**
     * Crate new group into a class, by group
     *
     * @param class_id
     * @param group
     * @return
     */
    public Group createGroupInClass(int class_id, Group group) {
        LOG.traceEntry("Creating group {} in class {}", group, class_id);
        Group createdGroup = null;
        try (Connection dbConnection = getDBConnection()) {
            String insert_group = "INSERT INTO public.\"Groups\" "
                    + "(name, class_id) VALUES (?, ?) RETURNING _id;";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(insert_group)) {
                insert.setString(1, group.getName());
                insert.setInt(2, class_id);
                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        createdGroup = new Group();
                        createdGroup.set_id(result.getInt("_id"));
                        createdGroup.setClass_id(class_id);
                        createdGroup.setName(group.getName());
                        createdGroup.setOpen(true);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(createdGroup);
        return createdGroup;
    }

    /**
     * Crate new group into a class, by group name
     *
     * @param class_id
     * @param name
     * @return
     */
    public Group createGroupInClass(int class_id, String name) {
        LOG.traceEntry("Creating group {} in class {}", name, class_id);
        Group createdGroup = null;
        try (Connection dbConnection = getDBConnection()) {
            String insert_group = "INSERT INTO public.\"Groups\" "
                    + "(name, class_id) VALUES (?, ?) RETURNING _id;";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(insert_group)) {
                insert.setString(1, name);
                insert.setInt(2, class_id);
                // execute query
                try (ResultSet result = insert.executeQuery()) {
                    if (result.next()) {
                        createdGroup = new Group();
                        createdGroup.set_id(result.getInt("_id"));
                        createdGroup.setClass_id(class_id);
                        createdGroup.setName(name);
                        createdGroup.setOpen(true);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(createdGroup);
        return createdGroup;
    }

    /**
     * Create new class for a teacher
     *
     * @param teacher_class
     */
    public void addClassToTeacher(Classes teacher_class) {
        LOG.traceEntry("Add class to teacher {}", teacher_class);
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "INSERT INTO public.\"Classes\" (\"name\", \"name_fi\", teacher_id) VALUES (?, ?, ?);";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                insert.setString(1, teacher_class.getName());
                insert.setString(2, teacher_class.getName_fi());
                insert.setInt(3, teacher_class.getTeacher_id());
                // execute query
                insert.executeUpdate();
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit();
    }

    /**
     * Get DB
     *
     * @return DB
     */
    public DataSource getDataSource() {
        return DATA_SOURCE;
    }

    /**
     * Get all students
     *
     * @return students
     */
    public List<Student> getAllStudents() {//(int teacher_id)  
        LOG.traceEntry("Getting all students ");//from {}", teacher_id);
        List<Student> students = null;
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "" + "SELECT s._id,\n" + "       s.username,\n"
                    + "       s.gender,\n" + "       s.age\n"
                    + "  FROM public.\"Students\" as s\n";
            //+ " WHERE s._id = (SELECT sc2.student_id\n"
            //+ "           	   FROM public.\"Student_Classes\" as sc2\n"
            //+ "                INNER JOIN public.\"Classes\" as cls\n"
            //+ "                ON (cls.teacher_id = ?)\n"
            //+ "				    AND (cls._id = sc2.class_id)\n"
            //+ "                WHERE s._id = sc2.student_id\n"
            //+ "              	ORDER BY sc2.creation_date DESC\n"
            //+ "            	 	LIMIT 1) \n";
            try (PreparedStatement insert = dbConnection
                    .prepareStatement(statement)) {
                // execute query
                students = new ArrayList<Student>();
                //insert.setInt(1, teacher_id);
                try (ResultSet result = insert.executeQuery()) {
                    while (result.next()) {
                        Student student = new Student(result.getInt("_id"),
                                result.getString("username"),
                                result.getString("gender"),
                                result.getInt("age"));
                        students.add(student);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(students);
        return students;
    }

    /**
     *
     * @param student_id
     * @return
     */
    public boolean doesStudentIdExistInDatabase(int student_id) {
        boolean idExists = false;
        LOG.traceEntry("Checking whether a student with student_id = {}",
                student_id, " exists in databsae.");
        try (Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "" + "SELECT COUNT(s._id)\n"
                    + "  FROM public.\"Students\" as s\n"
                    + " WHERE s._id = ?\n";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                // execute query
                select.setInt(1, student_id);
                try (ResultSet result = select.executeQuery()) {
                    if (result.next() & (result.getInt(1) != 0)) {
                        idExists = true;
                    }

                }

            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        LOG.traceExit(idExists);
        return idExists;
    }

    // TODO: Add checks if student belongs to class or grp in services maybe?
    // std = student, cls = class, grp = group, srv = survey
    public List<ThemeAverage> getSurveyAnswerAverages(int std_id, int cls_id,
            int grp_id, int srv_id) {
        List<ThemeAverage> answers = new ArrayList<>();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "" + "SELECT th.*, avg(answer)\n"
                    + "FROM \"Answers\" AS an\n"
                    + "INNER JOIN \"Questions\" as qu ON qu._id=an.question_id\n"
                    + "INNER JOIN \"Surveys\" as su ON su._id=an.survey_id\n"
                    + "INNER JOIN \"Themes\" as th ON th._id=qu.theme_id\n"
                    + "WHERE 1 = 1 \n" // dummy condition to continue with AND
                    + ((std_id > 0) ? " AND student_id=? \n" : "") // for 1 student
                    + ((cls_id > 0) ? " AND su.class_id=? \n" : "") // for 1 class
                    + ((grp_id > 0) ? " AND group_id=? \n" : "") // for 1 grp
                    + ((srv_id > 0) ? " AND survey_id=? \n" : "") // for 1 srv
                    + "GROUP BY qu.theme_id, th._id\n" + "ORDER BY qu.theme_id";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                int i = 1;
                if (std_id > 0) {
                    select.setInt(i, std_id);
                    i++;
                }
                if (cls_id > 0) {
                    select.setInt(i, cls_id);
                    i++;
                }
                if (grp_id > 0) {
                    select.setInt(i, grp_id);
                    i++;
                }
                if (srv_id > 0) {
                    select.setInt(i, srv_id);
                }
                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        ThemeAverage ta = new ThemeAverage();
                        ta.setAnswer(result.getFloat("avg"));
                        ta.setTheme_title(result.getString("title"));
                        ta.setTheme_title_fi(result.getString("title_fi"));
                        ta.setTheme_id(result.getInt("_id"));
                        ta.setDescription(result.getString("description"));
                        ta.setDescription_fi(
                                result.getString("description_fi"));
                        if (cls_id > 0) {
                            ta.setClass_id(cls_id);
                        }
                        if (srv_id > 0) {
                            ta.setSurvey_id(srv_id);
                        }
                        if (grp_id > 0) {
                            ta.setGroup_id(grp_id);
                        }
                        if (std_id > 0) {
                            ta.setStudent_id(std_id);
                        }
                        answers.add(ta);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
            return null;
        }
        return answers;
    }

    /**
     * Get all answers in a class.
     *
     * @param class_id
     * @return
     */
    public List<Answer> getClassAnswers(int class_id) {
        List<Answer> results = new ArrayList<>();
        try (Connection dbConnection = getDBConnection()) {
            String statement = "" + "SELECT an.*\n" + "FROM \"Answers\" AS an\n"
                    + "INNER JOIN \"Surveys\" as su ON su._id=an.survey_id\n"
                    + "WHERE su.class_id = ?";
            try (PreparedStatement select = dbConnection
                    .prepareStatement(statement)) {
                select.setInt(1, class_id);

                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        Answer a = new Answer();
                        a.setQuestion_id(result.getInt("question_id"));
                        a.setStudent_id(result.getInt("student_id"));
                        a.setAnswer(result.getInt("answer"));
                        a.setSurvey_id(result.getInt("survey_id"));
                        a.setGroup_id(result.getInt("group_id"));
                        results.add(a);
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            LOG.catching(e);
            return null;
        }
        return results;
    }
}
