package com.project2.projecttwo.service;
import org.python.core.exceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMailMessage;

import com.project2.projecttwo.dao.userRepopsitory;
import com.project2.projecttwo.dao.userRepositoryCust;
import com.project2.projecttwo.entity.Customer;
import com.project2.projecttwo.entity.User;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.List;
import java.util.Optional;
import java.util.Properties;



@org.springframework.stereotype.Service
public class Service 
{
    @Autowired
    //userrepository for Registration Data
    private userRepopsitory userrepo;
    

    

    public User saveDetails(User user)
    {
        return userrepo.save(user);
    }
   
    // Getting All the data from database..
    public List<User> GetDetails()
    {
        return userrepo.findAll();
    }
    // public User fetchDetailByEmail(String email)
    // {
    //     return userrepo.findByRegEmail(email);
    // }
    public Optional<User> IsEmailPresent(String Email)
    {
        
        return userrepo.findByRegEmail(Email);
    }

    public List<User> getUsersWithNullCategory() {
        return userrepo.findByCatageoryIsNull();
    }
    public List<User> getAnalyst()
    {
        return userrepo.findByCatageory("analyst");
    }
    public void deletebyid(int id) 
    {
        userrepo.deleteById(id);
    }
    public Optional<User> findbyid(int id)
    {
        return userrepo.findById(id);
    }

    //Email API
    public void sendEmail(String to,String subject,String message)
    {
        String from = "javadeveloper9610@gmail.com";
        // email host 
        String host = "smtp.gmail.com";
        //get the system properties
        Properties properties=System.getProperties();
        //setting important information to properties object
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth","true");


        //Step:1 to get the session Object
       Session session= Session.getInstance(properties,new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "skce cxir sziu jmwy"); 
            }
        });
        session.setDebug(true);
        //Step:2 Compose the message
        MimeMessage mm = new MimeMessage(session);
        
        try {
            //from mail
            mm.setFrom(from);
            //to 
            mm.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
            //Adding Subject
            mm.setSubject(subject);
            //adding Message
            mm.setText(message);

            //sending
            Transport.send(mm);
        } catch (Exception e) {
            // TODO: handle exception
        }




    }
    
}
