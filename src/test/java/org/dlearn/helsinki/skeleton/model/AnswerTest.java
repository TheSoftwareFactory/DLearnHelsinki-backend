package org.dlearn.helsinki.skeleton.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class AnswerTest {
    Answer answer1, answer2, answer3;

    @Before
    public void setUp() {
        answer1 = new Answer();
        answer2 = new Answer(0, 1, 2, 3);
        answer3 = new Answer(0, 1, 2, 3, 4);
    }

    @Test
    public void emptyConstructorSetQuestion_id() {
        answer1.setQuestion_id(42);
        assertEquals(42, answer1.getQuestion_id());
    }

    @Test
    public void emptyConstructorSetStundet_id() {
        answer1.setStudent_id(69);
        assertEquals(69, answer1.getStudent_id());
    }

    @Test
    public void emptyConstructorSetAnswer() {
        answer1.setAnswer(2);
        assertEquals(2, answer1.getAnswer());
    }

    @Test
    public void toStringTest() {
        String expected = "Answer{ survey_id = 0, question_id = 0, student_id = 0, answer = 0, group_id = 0 }";
        assertEquals(expected, answer1.toString());
    }

    @Test
    public void predefinedConstructorTest() {
        assertEquals(0, answer2.getSurvey_id());
        assertEquals(1, answer2.getQuestion_id());
        assertEquals(2, answer2.getStudent_id());
        assertEquals(3, answer2.getAnswer());
    }

    @Test
    public void setSurveyId() {
        answer3.setSurvey_id(69);
        assertEquals(69, answer3.getSurvey_id());
    }

    @Test
    public void setGroupId() {
        answer3.setGroup_id(44);
        assertEquals(44, answer3.getGroup_id());
    }
}
