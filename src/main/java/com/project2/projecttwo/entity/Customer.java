package com.project2.projecttwo.entity;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="customerinfo")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "S.N")
    int sn;

    @Column(name="Name")
    String name;

    @Column(name = "Email Address")
    String email;

    @Column(name = "Bussiness Type")
    String bussiness;

    @Column(name = "Category")
    String catageory;

    @Lob
    @Column(name="File",columnDefinition = "LONGBLOB")
    byte[] file;

    @Column(name = "File_Name")
    String filename;

    @Lob
    @Column(name="Report_File",columnDefinition = "LONGBLOB")
    byte[] reportfile;

    @Column(name = "Report_Name")
    String reportname;

    @Column(name="Message")
    String message;
    @Column(name="status")
    String status;
    
    @Column(name="analyzedby")
    String analyzedby;

    public String getAnalyzedby() {
        return analyzedby;
    }

    public void setAnalyzedby(String analyzedby) {
        this.analyzedby = analyzedby;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name="Analyst_Message")
    String analystmessage;


    // Getters and Setters

    public int getSn() {
        return sn;
    }

    public byte[] getReportfile() {
        return reportfile;
    }

    public void setReportfile(byte[] reportfile) {
        this.reportfile = reportfile;
    }

    public String getReportname() {
        return reportname;
    }

    public void setReportname(String reportname) {
        this.reportname = reportname;
    }

    public String getAnalystmessage() {
        return analystmessage;
    }

    public void setAnalystmessage(String analystmessage) {
        this.analystmessage = analystmessage;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBussiness() {
        return bussiness;
    }

    public void setBussiness(String bussiness) {
        this.bussiness = bussiness;
    }

    public String getCatageory() {
        return catageory;
    }

    public void setCatageory(String catageory) {
        this.catageory = catageory;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Customer() {
    }

    public Customer(int sn, String name, String email, String bussiness, String catageory, byte[] file, String filename,
            byte[] reportfile, String reportname, String message, String status, String analyzedby,
            String analystmessage) {
        this.sn = sn;
        this.name = name;
        this.email = email;
        this.bussiness = bussiness;
        this.catageory = catageory;
        this.file = file;
        this.filename = filename;
        this.reportfile = reportfile;
        this.reportname = reportname;
        this.message = message;
        this.status = status;
        this.analyzedby = analyzedby;
        this.analystmessage = analystmessage;
    }

    @Override
    public String toString() {
        return "Customer [sn=" + sn + ", name=" + name + ", email=" + email + ", bussiness=" + bussiness
                + ", catageory=" + catageory + ", file=" + Arrays.toString(file) + ", filename=" + filename
                + ", reportfile=" + Arrays.toString(reportfile) + ", reportname=" + reportname + ", message=" + message
                + ", status=" + status + ", analyzedby=" + analyzedby + ", analystmessage=" + analystmessage + "]";
    }

   
    
}