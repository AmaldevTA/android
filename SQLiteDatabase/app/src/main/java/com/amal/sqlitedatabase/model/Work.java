package com.amal.sqlitedatabase.model;


import java.io.Serializable;
import java.util.Date;

public class Work implements Serializable {

    public Work(){}

    public Work(String title, String description, Date dateCreated) {
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    private int _id;

    private String title;

    private String description;

    private Date dateCreated;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
