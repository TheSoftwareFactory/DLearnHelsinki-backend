package org.dlearn.helsinki.skeleton.service;

import java.util.ArrayList;
import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.ClassWithAllGroups;
import org.dlearn.helsinki.skeleton.model.Classes;
import org.dlearn.helsinki.skeleton.model.Student;

public class ClassService {

    final static Database db = new Database();

    public List<ClassWithAllGroups> getAllClassesWithGroups(int teacher_id) {
        // TODO Auto-generated method stub
        ArrayList<ClassWithAllGroups> classesGroups = new ArrayList<ClassWithAllGroups>();
        List<Classes> classes = db.getAllClassesOfTeacher(teacher_id);
        for (Classes s : classes) {
            ClassWithAllGroups classWithAllGroups = new ClassWithAllGroups();
            classWithAllGroups.setFields(s);
            classWithAllGroups
                    .setGroups(db.getAllGroupsFromClass(s.get_id(), false)); //by default returns only open groups
            classesGroups.add(classWithAllGroups);
        }
        return classesGroups;
    }

    public List<Classes> getAllClassesStundentIsIn(int student_id) {
        return db.getAllClassesStundentIsIn(student_id);
    }

    public void addClassToTeacher(Classes teacher_class) {
        // TODO Auto-generated method stub
        db.addClassToTeacher(teacher_class);
    }
}
