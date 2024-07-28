package com.project2.projecttwo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

public class AnalyzedUser {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id=1;

    @Column(name = "name")
    private String RegName;

    @Column(name = "email")
    private String regEmail;

    @Column(name = "feedback",length=1000)
    private String Feedback;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegName() {
        return RegName;
    }

    public void setRegName(String regName) {
        RegName = regName;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }
    
    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public AnalyzedUser() {
    }

    public AnalyzedUser(int id, String regName, String regEmail, String feedback) {
        this.id = id;
        RegName = regName;
        this.regEmail = regEmail;
        Feedback = feedback;
    }

    @Override
    public String toString() {
        return "AnalyzedUser [getId()=" + getId() + ", getRegName()=" + getRegName() + ", getRegEmail()="
                + getRegEmail() + ", getFeedback()=" + getFeedback() + "]";
    }
    


}
