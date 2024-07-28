package com.project2.projecttwo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project2.projecttwo.dao.analyzeduserRepository;
import com.project2.projecttwo.entity.AnalyzedUser;

@Service
public class Analyzedservice {
    @Autowired
    private analyzeduserRepository  analyzerepo;
    public AnalyzedUser savedetails(AnalyzedUser au)
    {
        return analyzerepo.save(au);
    }
    public List<AnalyzedUser> getDetails()
    {
        return analyzerepo.findAll();
    }
    public  Optional<AnalyzedUser>findbyemails(String email)
    {
        return analyzerepo.findByRegEmail(email);
    }
   

}
