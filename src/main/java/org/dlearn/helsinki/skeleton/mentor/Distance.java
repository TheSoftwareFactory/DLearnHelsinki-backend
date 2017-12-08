package org.dlearn.helsinki.skeleton.mentor;

import java.util.List;
import java.lang.IllegalArgumentException;

import org.dlearn.helsinki.skeleton.model.Answer;

public class Distance {

    public static double euclidean(List<Answer> u, List<Answer> v) {
        double result = 0.0;

        if (u.size() != v.size()) {
            throw new IllegalArgumentException(
                    "Operands could not be broadcast together with sizes "
                            + u.size() + " and " + v.size());
        }

        for (int i = 0; i < u.size(); i++) {
            result += Math.pow((u.get(i).getAnswer() - v.get(i).getAnswer()),
                    2);
        }

        return result;
    }

}