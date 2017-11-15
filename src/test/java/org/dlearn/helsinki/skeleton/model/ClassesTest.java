package org.dlearn.helsinki.skeleton.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class ClassesTest {
    Classes class1, class2;

    @Before
    public void setUp() {
        class1 = new Classes();
        class2 = new Classes(7, "The best of the best", "Parhaista parhaat", 5);
    }

    @Test
    public void emptyConstructorSet_id() {
        class1.set_id(42);
        assertEquals(42, class1.get_id());
    }

    @Test
    public void emptyConstructorSetName() {
        String className = "Crap class";
        String className_fi = "Paska Luokka";
        class1.setName(className);
        class1.setName_fi(className_fi);
        assertEquals(className, class1.getName());
        assertEquals(className_fi, class1.getName_fi());
    }

    @Test
    public void emptyConstructorSetTeacher_id() {
        class1.setTeacher_id(555);
        assertEquals(555, class1.getTeacher_id());
    }

    @Test
    public void toStringTest() {
        String expected = "Classes{ _id = 0, name = null, name_fi = null, teacher_id = 0 }";
        assertEquals(expected, class1.toString());
    }

    @Test
    public void predefinedConstructorTest() {
        assertEquals(7, class2.get_id());
        assertEquals("The best of the best", class2.getName());
        assertEquals(5, class2.getTeacher_id());
    }

}