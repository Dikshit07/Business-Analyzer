package com.project2.projecttwo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.project2.projecttwo.entity.User;
import java.util.List;



public interface userRepopsitory extends JpaRepository<User,Integer>
{
    // User findByRegEmail(String RegEmail);
    Optional<User> findByRegEmail(String regEmail);
    List<User> findByCatageoryIsNull();
    List<User> findByCatageory(String catageory);
    

}
