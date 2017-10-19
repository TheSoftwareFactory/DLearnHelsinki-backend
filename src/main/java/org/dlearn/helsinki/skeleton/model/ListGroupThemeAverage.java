
package org.dlearn.helsinki.skeleton.model;

import java.util.List;


public class ListGroupThemeAverage {
    public List<GroupThemeAverage> themes;
    public Survey survey;

    @Override
    public String toString() {
        return "ListGroupThemeAverage{" + "themes=" + themes + ", survey=" + survey + '}';
    }
}
