package org.dlearn.helsinki.skeleteon.mentor;

import java.util.ArrayList;

import org.dlearn.helsinki.skeleton.model.Answer;
import static org.dlearn.helsinki.skeleton.mentor.Distance.euclidean;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

public class DistanceTest {
    public Answer ans1, ans2, ans3;
    public Answer ans4, ans5, ans6;
    public ArrayList<Answer> u, v;

    @Before
    public void setUp() {
        ans1 = new Answer(1, 1, 1, 1);
        ans2 = new Answer(1, 1, 1, 2);
        ans3 = new Answer(1, 1, 1, 3);
        ans4 = new Answer(1, 1, 2, 2);
        ans5 = new Answer(1, 1, 2, 2);
        ans6 = new Answer(1, 1, 2, 3);
        u = new ArrayList();
        v = new ArrayList();
        u.add(ans1);
        u.add(ans2);
        u.add(ans3);
        v.add(ans4);
        v.add(ans5);
        v.add(ans6);
    }

    @Test
    public void testEuclidean() {
        double expected = 1.0;
        assertEquals(expected, euclidean(u, v), 0.0);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void throwsIllegalArgumentExceptionIfListsHaveDifferentSizes() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(
                "Operands could not be broadcast together with sizes 3 and 4");
        v.add(ans6);
        euclidean(u, v);
    }
}