package org.dlearn.helsinki.skeleton.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class StudentLofTest {
    StudentLof lof;

    @Before
    public void setUp() {
        lof = new StudentLof(0, 1, "Mathieu", 3.0);
    }

    @Test
    public void getNSetStudent_id() {
        lof.setStudent_id(69);
        assertEquals(69, lof.getStudent_id());
    }

    @Test
    public void getNSetClass_id() {
        lof.setClass_id(619);
        assertEquals(619, lof.getClass_id());
    }

    @Test
    public void getNSetName() {
        lof.setName("Adolf");
        assertEquals("Adolf", lof.getName());
    }

    @Test
    public void getNSetLofScore() {
        lof.setLofScore(3.14);
        assertEquals(3.14, lof.getLofScore(), 0.0);
    }

    @Test
    public void toStringTest() {
        String expected = "StudentLof { student_id = 0, class_id = 1, name = Mathieu, lof_score = 3.0 }";
        assertEquals(expected, lof.toString());
    }
}