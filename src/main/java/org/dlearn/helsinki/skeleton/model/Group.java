package org.dlearn.helsinki.skeleton.model;

public class Group {

    public int _id;
    public String name;
    public int class_id;
    public boolean open;

    public Group() {
    }

    public Group(int _id, String name, int class_id, boolean open) {
        super();
        this._id = _id;
        this.name = name;
        this.class_id = class_id;
        this.open = open;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudent_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Boolean getOpen() {
        return open;
    }

    @Override
    public String toString() {
        return "Group { _id = " + _id + ", name = " + name + ", class_id = "
                + class_id + ", open = " + open + " }";
    }
}
