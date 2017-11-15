package org.dlearn.helsinki.skeleton.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChangePasswordStudentTest {

    @Test
    public void testChangePasswordStudent() {
        ChangePasswordStudent cps = new ChangePasswordStudent();
        assertEquals(0, cps.student_id);
        assertNull(cps.password);
        String expected = "ChangePasswordStudent{student_id=0}";
        assertEquals(expected, cps.toString());
    }

}