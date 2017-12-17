
package org.dlearn.helsinki.skeleton.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListThemeAverage {

    private List<ThemeAverage> themes;
    private Survey survey;

    public ListThemeAverage() {} // Needed to get JSON work
    
    public ListThemeAverage(List<ThemeAverage> themes, Survey survey) {
        this.themes = themes;
        this.survey = survey;
    }

    public List<ThemeAverage> getThemes() {
        return themes;
    }

    public void setThemes(List<ThemeAverage> themes) {
        this.themes = themes;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public String toString() {
        return "ListThemeAverage{" + "themes=" + themes + ", survey=" + survey
                + '}';
    }
}
