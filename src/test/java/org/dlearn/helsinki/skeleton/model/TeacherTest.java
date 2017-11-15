package org.dlearn.helsinki.skeleton.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TeacherTest {
    Teacher teacher1, teacher2;

    @Before
    public void setUp() {
        teacher1 = new Teacher();
        teacher2 = new Teacher(69, "Billy Herrington", "super secret");
    }

    @Test
    public void emptyConstructorSet_id() {
        teacher1.set_id(619);
        assertEquals(619, teacher1.get_id());
    }

    @Test
    public void emptyConstructorGetUsername() {
        assertNull(teacher1.getUsername());
    }

    @Test
    public void emptyConstructorSetUsername() {
        String username = "Van Darkholm";
        teacher1.setUsername(username);
        assertEquals(username, teacher1.getUsername());
    }

    @Test
    public void testProperTeacher() {
        String excepted = "Teacher{_id=69, username=Billy Herrington}";
        assertEquals(excepted, teacher2.toString());
    }
}
