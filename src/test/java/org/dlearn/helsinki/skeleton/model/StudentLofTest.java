package org.dlearn.helsinki.skeleton.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class StudentLofTest {
    Student student;
    StudentLof lof;

    @Before
    public void setUp() {
        student = new Student(0, "Mathieu", "mtf", 1);
        lof = new StudentLof(student);
    }

    @Test
    public void emptyConstructor() {
        StudentLof std = new StudentLof();
        assertEquals(0, std.get_id());
        assertEquals(null, std.getUsername());
        assertEquals(null, std.getGender());
        assertEquals(0, std.getAge());
        assertEquals(0, std.getClass_id());
        assertEquals(0.0, std.getLofScore(), 0.0);
    }

    @Test
    public void primitiveConstructor() {
        StudentLof std = new StudentLof(1, "Dud", "Male", 69, 12, 3.0);
        assertEquals(1, std.get_id());
        assertEquals("Dud", std.getUsername());
        assertEquals("Male", std.getGender());
        assertEquals(69, std.getAge());
        assertEquals(12, std.getClass_id());
        assertEquals(3.0, std.getLofScore(), 3.0);
    }

    @Test
    public void getNSetClass_id() {
        lof.setClass_id(619);
        assertEquals(619, lof.getClass_id());
    }

    @Test
    public void getNSetLofScore() {
        lof.setLofScore(3.14);
        assertEquals(3.14, lof.getLofScore(), 0.0);
    }

    @Test
    public void toStringTest() {
        String expected = "StudentLof{_id=0, username=Mathieu, gender=mtf, age=1, class_id=0, lof_score=0.0}";
        assertEquals(expected, lof.toString());
    }
}