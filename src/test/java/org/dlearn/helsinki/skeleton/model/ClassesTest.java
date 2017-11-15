package org.dlearn.helsinki.skeleton.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class ClassesTest {
    Classes class1, class2;

    @Before
    public void setUp() {
        class1 = new Classes();
        class2 = new Classes(7, "The best of the best", 5);
    }

    @Test
    public void emptyConstructorSet_id() {
        class1.set_id(42);
        assertEquals(42, class1.get_id());
    }

    @Test
    public void emptyConstructorSetName() {
        String className = "Crap class";
        class1.setName(className);
        assertEquals(className, class1.getName());
    }

    @Test
    public void emptyConstructorSetTeacher_id() {
        class1.setTeacher_id(555);
        assertEquals(555, class1.getTeacher_id());
    }

    @Test
    public void toStringTest() {
        String expected = "Classes{ _id = 0, name = null, teacher_id = 0 }";
        assertEquals(expected, class1.toString());
    }

    @Test
    public void predefinedConstructorTest() {
        assertEquals(7, class2.get_id());
        assertEquals("The best of the best", class2.getName());
        assertEquals(5, class2.getTeacher_id());
    }

}