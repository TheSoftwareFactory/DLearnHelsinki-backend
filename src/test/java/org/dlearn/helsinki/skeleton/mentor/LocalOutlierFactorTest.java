package org.dlearn.helsinki.skeleton.mentor;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.shuffle;
import org.dlearn.helsinki.skeleton.model.Answer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LocalOutlierFactorTest {
    LocalOutlierFactor lof = new LocalOutlierFactor();
    List<Answer> data, rawData;
    List<List<Answer>> expected;

    @Before
    public void setUp() {
        data = new ArrayList();
        rawData = new ArrayList();
        expected = new ArrayList();
        //data.add();
    }

    @Test
    public void prepareDataEmptyRawData() {
        assertEquals(expected, lof.prepareData(0, rawData));
    }

    @Test
    public void zeroSizeAnswerListsAreIgnored() {
        rawData.add(new Answer());
        assertEquals(expected, lof.prepareData(0, rawData));
    }

    @Test
    public void tooLongAnswersAreIgnored() {
        rawData.add(new Answer(0, 0, 0, 0));
        rawData.add(new Answer(0, 1, 0, 0));
        assertEquals(expected, lof.prepareData(1, rawData));
    }

    @Test
    public void tooShortAnswersAreIgnored() {
        rawData.add(new Answer(0, 0, 0, 0));
        assertEquals(expected, lof.prepareData(2, rawData));
    }

    @Test
    public void correctForm() {
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
}