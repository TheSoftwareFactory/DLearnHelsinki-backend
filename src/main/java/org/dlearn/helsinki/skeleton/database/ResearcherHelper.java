package org.dlearn.helsinki.skeleton.database;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.dlearn.helsinki.skeleton.model.NewTeacher;
import org.dlearn.helsinki.skeleton.model.Teacher;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.Theme;

public class ResearcherHelper {

    static Database db = new Database();

    static public Teacher addTeacher(HttpServletRequest request) {
        String firstname, lastname, username, password;

        firstname = request.getParameter("add_teacher_first");
        lastname = request.getParameter("add_teacher_last");
        username = request.getParameter("add_teacher_user");
        password = request.getParameter("add_teacher_pwd");

        if (firstname == null || lastname == null || username == null
                || password == null || firstname.isEmpty() || lastname.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            return null;
        }

        Teacher t = new Teacher(0, username, firstname, lastname);
        return db.createTeacher(new NewTeacher(t, password));
    }

    static public Question addQuestion(HttpServletRequest request) {
        String question, question_fi;
        int min_answer, max_answer, theme;

        question = request.getParameter("add_question_question");
        question_fi = request.getParameter("add_question_question_fi");
        min_answer = toInt(request.getParameter("add_question_min_answer"));
        max_answer = toInt(request.getParameter("add_question_max_answer"));
        theme = toInt(request.getParameter("add_teacher_pwd"));

        if (question == null || question_fi == null || question.isEmpty()
                || question_fi.isEmpty()) {
            return null;
        }

        if (min_answer > 0 && max_answer > 0 && theme > 0) {
            Question q = new Question(0, question, question_fi, min_answer,
                    max_answer, theme);
            return db.createQuestion(q);
        } else {
            return null;
        }
    }

    static public Theme addTheme(HttpServletRequest request) {
        String title, title_fi, description, description_fi;

        title = request.getParameter("add_theme_title");
        title_fi = request.getParameter("add_theme_title_fi");
        description = request.getParameter("add_theme_description");
        description_fi = request.getParameter("add_theme_description_fi");

        if (title == null || title_fi == null || description == null
                || description_fi == null || title.isEmpty()
                || title_fi.isEmpty() || description.isEmpty()
                || description_fi.isEmpty()) {
            return null;
        }

        Theme t = new Theme(0, title, title_fi, description, description_fi);
        return db.createTheme(t);

    }

    static public List<Theme> listTheme() {
        return db.getThemes();
    }

    static private int toInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
