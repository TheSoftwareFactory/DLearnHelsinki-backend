package org.dlearn.helsinki.skeleton.mentor;

import java.util.ArrayList;
import java.util.List;

import org.dlearn.helsinki.skeleton.model.Answer;

public class Distance {

    public static double euclidean(ArrayList<Answer> u, ArrayList<Answer> v) {
        double result = 0.0;

        if (u.size() != v.size()) {
            return Double.POSITIVE_INFINITY;
        }

        for (int i = 0; i < u.size(); i++) {
            result += Math.pow((u.get(i).getAnswer() - v.get(i).getAnswer()),
                    2);
        }

        return result;
    }

}