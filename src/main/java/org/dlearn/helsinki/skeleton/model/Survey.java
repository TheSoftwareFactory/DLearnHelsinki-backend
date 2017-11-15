package org.dlearn.helsinki.skeleton.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Survey {

    public int _id;
    public String title;
    public String title_fi;
    public String description;
    public String description_fi;
    public Timestamp start_date;
    public Timestamp end_date;
    public int teacher_id;
    public int class_id;
    public boolean open;

    public Survey() {
        super();
    }

    public Survey(int _id, String title, String title_fi, String description, String description_fi, Timestamp start_date,
    		Timestamp end_date, int teacher_id, int class_id, boolean open) {
        super();
        this._id = _id;
        this.title = title;
        this.title_fi = title_fi;
        this.description = description;
        this.description_fi = description_fi;
        this.start_date = start_date;
        this.end_date = end_date;
        this.teacher_id = teacher_id;
        this.class_id = class_id;
        this.open = open;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }
    
    public String getTitle_fi() {
        return title_fi;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setTitle_fi(String title) {
        this.title_fi = title;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public Timestamp getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Timestamp end_date) {
        this.end_date = end_date;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getDescription() {
        return description;
    }
    
    public String getDescription_fi() {
        return description_fi;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setDescription_fi(String description) {
        this.description_fi = description;
    }

    @Override
    public String toString() {
        return "Survey{" + "_id=" + _id + ", title=" + title + ", description=" + description + ", start_date=" + start_date + ", end_date=" + end_date + ", teacher_id=" + teacher_id + ", class_id=" + class_id + ", open=" + open + '}';
    }
    
}
