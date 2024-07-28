package com.project2.projecttwo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project2.projecttwo.entity.Customer;
import java.util.List;



public interface userRepositoryCust extends JpaRepository<Customer,Integer>
{
    Optional<Customer> findByEmail(String Email);
    List<Customer> findByStatus(String status);
    

}
