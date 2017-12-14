package org.dlearn.helsinki.skeleton.mentor;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.shuffle;
import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.mentor.Tuple;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LocalOutlierFactorTest {
    LocalOutlierFactor lof = new LocalOutlierFactor();
    List<Answer> rawData;
    List<List<Answer>> data, expected;

    @Before
    public void setUp() {
        data = new ArrayList();
        rawData = new ArrayList();
        expected = new ArrayList();
    }

    @Test
    public void prepareDataEmptyRawData() {
        assertEquals(expected, lof.prepareData(0, rawData));
    }

    @Test
    public void prepareDataZeroSizeAnswerListsAreIgnored() {
        rawData.add(new Answer());
        assertEquals(expected, lof.prepareData(0, rawData));
    }

    @Test
    public void prepareDataTooLongAnswersAreIgnored() {
        rawData.add(new Answer(0, 0, 0, 0));
        rawData.add(new Answer(0, 1, 0, 0));
        assertEquals(expected, lof.prepareData(1, rawData));
    }

    @Test
    public void prepareDataTooShortAnswersAreIgnored() {
        rawData.add(new Answer(0, 0, 0, 0));
        assertEquals(expected, lof.prepareData(2, rawData));
    }

    @Test
    public void prepareDataCorrectForm() {
        List<Integer> students = new ArrayList();
        List<List<Answer>> tmp = new ArrayList();
        // Correct answer lists
        for (int i = 0; i < 3; i++) {
            List<Answer> studentAnswer = new ArrayList();
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 3; k++) {
                    Answer a = new Answer(j, k, i, 3);
                    rawData.add(a);
                    studentAnswer.add(a);
                }
            }
            tmp.add(studentAnswer);
        }
        // too short answers
        for (int i = 3; i < 5; i++) {
            for (int k = 0; k < 3; k++) {
                Answer a = new Answer(1, k, i, 3);
                rawData.add(a);
            }
        }
        shuffle(rawData);
        for (Answer ans : rawData) {
            if (!students.contains(ans.getStudent_id()))
                students.add(ans.getStudent_id());
        }
        for (Integer student : students) {
            for (List<Answer> ans : tmp) {
                if (ans.get(0).getStudent_id() == student) {
                    expected.add(ans);
                }
            }
        }
        assertEquals(expected, lof.prepareData(6, rawData));
    }

    @Test
    public void knnEmptyTuple() {
        List<Answer> p = new ArrayList();
        Tuple<Double, List<List<Answer>>> knn = lof.kNearestNeighbors(0, p,
                expected);
        assertNull(knn.first());
        assertEquals(p, knn.second());
    }

    @Test
    public void knnTwoNeighbors() {
        List<Answer> p = new ArrayList();
        p.add(new Answer(1, 1, 99, 1));
        p.add(new Answer(1, 2, 99, 1));
        List<List<Answer>> container = new ArrayList();
        List<Answer> expected1 = new ArrayList();
        expected1.add(new Answer(1, 1, 69, 1));
        expected1.add(new Answer(1, 2, 69, 2));
        List<Answer> expected2 = new ArrayList();
        expected2.add(new Answer(1, 1, 56, 1));
        expected2.add(new Answer(1, 2, 56, 3));
        container.add(expected1);
        container.add(expected2);
        data.add(expected1);
        data.add(expected2);
        data.add(p);
        for (int j = 0; j < 5; j++) {
            List<Answer> answers = new ArrayList();
            for (int i = 1; i < 3; i++) {
                answers.add(new Answer(1, i, j, 5));
            }
            data.add(answers);
        }

        Tuple<Double, List<List<Answer>>> knn = lof.kNearestNeighbors(2, p,
                data);
        assertEquals(1.0, knn.first(), 0.0);
        assertTrue(knn.second().contains(expected1));
        assertTrue(knn.second().contains(expected2));
    }
}