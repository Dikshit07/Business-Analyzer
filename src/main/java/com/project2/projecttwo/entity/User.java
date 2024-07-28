package com.project2.projecttwo.entity;

import org.hibernate.mapping.PrimaryKey;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="Customer")


public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id=1;

    @Column(name = "name")
    private String RegName;

    @Column(name = "email")
    private String regEmail;

    @Column(name = "password")
    private String RegPassword;

    @Column(name = "confirm_password")
    private String ConPassword;

    @Column(name = "catageory")
    private String catageory;
    public User(int id,String RegName,String RegEmail,String RegPassword,String ConPassword,String catageory)
    {
        super();
        // this.id=id;
        this.RegName=RegName;
        this.regEmail=RegEmail;
        this.RegPassword= RegPassword;
        this.ConPassword=ConPassword;
        this.catageory=catageory;

    }
    public User()
    {

    }
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
    public String getRegPassword() {
        return RegPassword;
    }
    public void setRegPassword(String regPassword) {
        RegPassword = regPassword;
    }
    public String getConPassword() {
        return ConPassword;
    }
    public void setConPassword(String conPassword) {
        ConPassword = conPassword;
    }
    public String getCatageory() {
        return catageory;
    }
    public void setCatageory(String catageory) {
        this.catageory = catageory;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", RegName=" + RegName + ", RegEmail=" + regEmail + ", RegPassword=" + RegPassword
                + ", ConPassword=" + ConPassword + ", catageory=" + catageory + "]";
    }

    
}
