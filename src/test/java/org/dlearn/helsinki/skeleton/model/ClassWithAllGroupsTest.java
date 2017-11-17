package org.dlearn.helsinki.skeleton.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ClassWithAllGroupsTest {

    ClassWithAllGroups cwag = new ClassWithAllGroups();

    @Test
    public void idGetAndSet() {
        cwag.set_id(911);
        assertEquals(911, cwag.get_id());
    }

    @Test
    public void nameGetAndSet() {
        String name = "name";
        cwag.setName(name);
        assertEquals(name, cwag.getName());
    }

    @Test
    public void name_fiGetAndSet() {
        String name = "nimi";
        cwag.setName_fi(name);
        assertEquals(name, cwag.getName_fi());
    }

    @Test
    public void teacher_idGetAndSet() {
        cwag.setTeacher_id(52);
        assertEquals(52, cwag.getTeacher_id());
    }

    @Test
    public void groupsGetAndSet() {
        List<Group> groups = new ArrayList<Group>();
        Group g = new Group();
        groups.add(g);
        cwag.setGroups(groups);
        assertEquals(groups, cwag.getGroups());
    }

    @Test
    public void setFieldsTest() {
        Classes cls = new Classes(7, "The best of the best",
                "Parhaista parhaat", 5);
        cwag.setFields(cls);
        assertEquals(cls.getName(), cwag.getName());
        assertEquals(cls.getName_fi(), cwag.getName_fi());
        assertEquals(cls.get_id(), cwag.get_id());
        assertEquals(cls.getTeacher_id(), cwag.getTeacher_id());
    }

    @Test
    public void toStringFormatTest() {
        String str = "ClassWithAllGroups{ _id = 0, name = null,"
                + " teacher_id = 0, groups = [] }";
        System.out.println(cwag);
        assertEquals(str, cwag.toString());
    }

}