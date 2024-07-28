package com.project2.projecttwo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project2.projecttwo.entity.AnalyzedUser;
import java.util.List;


public interface analyzeduserRepository extends JpaRepository<AnalyzedUser,Integer>
{
    Optional<AnalyzedUser> findByRegEmail(String regEmail);


    }
 


