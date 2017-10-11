package org.dlearn.helsinki.skeleton.service;

import java.util.Optional;
import java.util.function.Function;
import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Researcher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityService {

    private final static Database DB = new Database();

    public boolean isTheStudent(int student_id) {
        return getStudentId().map(id -> id == student_id).orElse(false);
    }

    public boolean isTheTeacher(int teacher_id) {
        return getTeacherId().map(id -> id == teacher_id).orElse(false);
    }

    public Optional<Integer> getStudentId() {
        return getFromSession(
                name -> DB.getStudentFromUsername(name).map(s -> s._id));
    }

    public Optional<Integer> getTeacherId() {
        return getFromSession(
                name -> DB.getTeacherFromUsername(name).map(s -> s._id));
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
