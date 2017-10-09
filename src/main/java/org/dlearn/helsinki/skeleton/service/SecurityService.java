package org.dlearn.helsinki.skeleton.service;

import java.util.Optional;
import java.util.function.Function;
import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.security.AccessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityService {
    private final static Database DB = new Database();
    
    public static void ensureCorrectStudentId(int student_id) throws Exception {
        if (!hasStudentId(student_id)) {
            throw new AccessException("Couldn't authorize the student");
        }
    }
    
    public static void ensureCorrectTeacherId(int teacher_id) throws Exception {
        if (!hasTeacherId(teacher_id)) {
            throw new AccessException("Couldn't authorize the teacher");
        }
    }
    
    public static boolean hasStudentId(int student_id) {
        return hasId(student_id,
                name -> DB.getStudentFromUsername(name)
                        .map(s -> s._id));
    }
    
    public static boolean hasTeacherId(int teacher_id) {
        return hasId(teacher_id,
                name -> DB.getTeacherFromUsername(name)
                        .map(t -> t._id));
    }
    
    private static boolean hasId(int id, Function<String, Optional<Integer>> getIdFromName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return getIdFromName
                    .apply(authentication.getName())
                    .map(n -> n == id)
                    .orElse(false);
        }
        return false;
    }
}
