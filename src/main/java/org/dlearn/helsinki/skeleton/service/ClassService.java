package org.dlearn.helsinki.skeleton.service;

import java.util.ArrayList;
import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.ClassWithAllGroups;
import org.dlearn.helsinki.skeleton.model.Classes;

public class ClassService {
		
	final static Database db = new Database();

	public List<ClassWithAllGroups> getAllClassesWithGroups(int teacher_id) {
		// TODO Auto-generated method stub
		ArrayList<ClassWithAllGroups> classesGroups = new ArrayList<ClassWithAllGroups>();		
		List<Classes> classes = db.getAllClassesOfTeacher(teacher_id);
		for(Classes s : classes) {
			ClassWithAllGroups classWithAllGroups = new ClassWithAllGroups();
			classWithAllGroups.setFields(s);
			classWithAllGroups.setGroups(db.getAllGroupsFromClass(s.get_id()));
			classesGroups.add(classWithAllGroups);
		};
		return classesGroups;
	}
}
