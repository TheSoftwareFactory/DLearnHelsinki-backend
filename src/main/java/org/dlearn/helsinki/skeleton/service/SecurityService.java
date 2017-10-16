package org.dlearn.helsinki.skeleton.service;

import java.util.Optional;
import java.util.function.Function;
import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Researcher;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.Teacher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityService {

    private final static Database DB = new Database();

    public boolean isTheStudent(int student_id) {
        return getStudent().map(s -> s._id == student_id).orElse(false);
    }

    public boolean isTheTeacher(int teacher_id) {
        return getTeacher().map(t -> t._id == teacher_id).orElse(false);
    }

    public boolean isTheResearcher(int researcher_id) {
        return getResearcher().map(t -> t.id == researcher_id).orElse(false);
    }

    public Optional<Student> getStudent() {
        return getFromSession(DB::getStudentFromUsername);
    }

    public Optional<Teacher> getTeacher() {
        return getFromSession(DB::getTeacherFromUsername);
    }

    public Optional<Researcher> getResearcher() {
        return getFromSession(DB::getResearcherFromUsername);
    }

    private static <T> Optional<T> getFromSession(
            Function<String, Optional<T>> getIdFromName) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return getIdFromName.apply(authentication.getName());
        }
        return Optional.empty();
    }
}
