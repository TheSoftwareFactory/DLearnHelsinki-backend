/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dlearn.helsinki.skeleton.model;

/**
 *
 * @author Kalle
 */
public class Theme {
    private int id;
    private String title;
    private String title_fi;
    private String description;
    private String description_fi;

    public Theme(int id, String title, String title_fi, String description, String description_fi){
    this.id = id;
    this.title = title;
    this.title_fi= title_fi;
    this.description = description;
    this.description_fi = description_fi;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the title_fi
     */
    public String getTitle_fi() {
        return title_fi;
    }

    /**
     * @param title_fi the title_fi to set
     */
    public void setTitle_fi(String title_fi) {
        this.title_fi = title_fi;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the description_fi
     */
    public String getDescription_fi() {
        return description_fi;
    }

    /**
     * @param description_fi the description_fi to set
     */
    public void setDescription_fi(String description_fi) {
        this.description_fi = description_fi;
    }
    
    
}
