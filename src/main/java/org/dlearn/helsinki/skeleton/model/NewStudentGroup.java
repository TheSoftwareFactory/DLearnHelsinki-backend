package org.dlearn.helsinki.skeleton.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewStudentGroup {

    public int _id;
    public int class_id;
    public boolean open;
    public String name;
    public List<NewStudent> students;

    public NewStudentGroup() {
        students = new ArrayList<NewStudent>();
    }

    public NewStudentGroup(int _id, int class_id, String name,
            List<NewStudent> students) {
        super();
        this._id = _id;
        this.class_id = class_id;
        this.name = name;
        this.students = students;
    }

    @Override
    public String toString() {
        return "StudentGroup { _id = " + _id + ", name = " + name
                + ", students = " + students.toString() + " }";
    }

    public Group getGroup() {
        return new Group(_id, name, class_id, open);
    }

    public void setGroup(Group group) {
        _id = group.get_id();
        class_id = group.getStudent_id();
        open = group.getOpen();
        name = group.getName();
    }
}
