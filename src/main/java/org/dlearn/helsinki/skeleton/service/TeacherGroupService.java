package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.exceptions.GroupCannotBeClosedException;
import org.dlearn.helsinki.skeleton.exceptions.GroupUpdateUnsuccessful;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.StudentGroup;

public class TeacherGroupService {

    private static final Database DB = new Database();

    public List<StudentGroup> getGroupsWithStudents(int class_id, boolean all) {
        return DB.getGroupsWithStudents(class_id, all);
    }
    
    public List<Group> getAllGroupsTheStudentIsIn(int studentID) {
        return DB.getAllGroupsForStudent(studentID);
    }

    public List<Group> getAllGroupsFromClass(int class_id) {
        return DB.getAllGroupsFromClass(class_id,false); // only open by default
    }

    public Group getGroupFromClass(int class_id, int group_id) {
        return DB.getGroupFromClass(class_id, group_id);
    }

    public List<Student> getAllStudentsFromClassAndGroup(int class_id,
            int group_id) {
        return DB.getAllStudentsFromClassAndGroup(class_id, group_id);
    }
    
    public void deleteGroupFromClass(int class_id, int group_id) {
    	if (DB.isGroupClosed(group_id)) {
    		if (0 == DB.countNumberOfStudentsInGroup(group_id)) {
    			System.out.println("Closing group");
    			DB.closeGroup(group_id);
    		} else {
        		throw new GroupCannotBeClosedException();
        	};
    	};
    }
    
    public void updateGroupInClass(int class_id, int group_id, Group groupSample) {
   		DB.updateGroupName(class_id, group_id, groupSample);
    }
    
	public Group insertGroupInClass(int class_id, Group group) {
		return DB.createGroupInClass(class_id, group);
	}
}
