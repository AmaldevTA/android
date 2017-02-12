package com.amal.realm.model;

import java.util.Date;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by amal on 29/5/16.
 */
public class Work extends RealmObject {

    public Work(){}

    public Work(String title, String description, Date dateCreated) {
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    @Required
    @PrimaryKey
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
