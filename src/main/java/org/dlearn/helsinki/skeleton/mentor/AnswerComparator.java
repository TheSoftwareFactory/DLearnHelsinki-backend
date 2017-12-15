package org.dlearn.helsinki.skeleton.mentor;

import java.util.Comparator;
import org.dlearn.helsinki.skeleton.model.Answer;

public class AnswerComparator implements Comparator<Answer> {

    @Override
    public int compare(Answer a, Answer b) {
        int surveyComparison = Integer.compare(a.survey_id, b.survey_id);
        return surveyComparison == 0
                ? Integer.compare(a.question_id, b.question_id)
                : surveyComparison;
    }
}