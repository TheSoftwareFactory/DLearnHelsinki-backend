
package org.dlearn.helsinki.skeleton.model;

import java.util.List;

public class ListStudentThemeAverage {
    public List<StudentThemeAverage> themes;
    public Survey survey;

    @Override
    public String toString() {
        return "ListStudentThemeAverage{" + "themes=" + themes + ", survey="
                + survey + '}';
    }
}
