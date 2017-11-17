package org.dlearn.helsinki.skeleton.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ClassThemeAverageTest {
    ClassThemeAverage cta = new ClassThemeAverage();

    @Test
    public void surveyIdGetAndSet() {
        cta.setSurvey_id(31);
        assertEquals(31, cta.getSurvey_id());
    }

    @Test
    public void startDateGetAndSet() {
        String start_date = "2017-11-17";
        cta.setStart_date(start_date);
        assertEquals(start_date, cta.getStart_date());
    }

    @Test
    public void themeIdGetAndSet() {
        cta.setTheme_id(245);
        assertEquals(245, cta.getTheme_id());
    }

    @Test
    public void themeTitleGetAndSet() {
        String title = "generic title";
        cta.setTheme_title(title);
        assertEquals(title, cta.getTheme_title());
    }

    @Test
    public void themeTitleFiGetAndSet() {
        String title = "geneerinen otsikko";
        cta.setTheme_title_fi(title);
        assertEquals(title, cta.getTheme_title_fi());
    }

    @Test
    public void descriptionGetAndSet() {
        String description = "description";
        cta.setDescription(description);
        assertEquals(description, cta.getDescription());
    }

    @Test
    public void descriptionFiGetAndSet() {
        String description = "kuvaus";
        cta.setDescription_fi(description);
        assertEquals(description, cta.getDescription_fi());
    }

    @Test
    public void classIdGetAndSet() {
        cta.setClass_id(112);
        assertEquals(112, cta.getClass_id());
    }

    @Test
    public void answerSetAndGetOtherConstructor() {
        cta = new ClassThemeAverage(1, "2017-11-10", 1, "title", "otsikko",
                "description", "kuvaus", 1, 5.0f);
        assertEquals(5.0f, cta.getAnswer(), 0.0f);
        cta.setAnswer(4.0f);
        assertEquals(4.0f, cta.getAnswer(), 0.0f);
    }

    @Test
    public void toStringFormatTest() {
        String str = "ClassThemeAverage{ survey_id = 0, start_date = null,"
                + " theme_id = 0, theme_title = null, description = null,"
                + " class_id = 0, answer = 0.0 }";
        assertEquals(str, cta.toString());
    }

}