package org.dlearn.helsinki.skeleton.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResearcherTest {

    @Test
    public void researcherConstructorTest() {
        Researcher researcher = new Researcher();
        assertEquals(0, researcher.id);
        assertNull(researcher.username);
    }
}