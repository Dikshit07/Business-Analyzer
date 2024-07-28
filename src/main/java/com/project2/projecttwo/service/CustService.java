package com.project2.projecttwo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project2.projecttwo.dao.userRepositoryCust;
import com.project2.projecttwo.entity.Customer;

@Service
public class CustService {
    //userrepository for CustomerData
@Autowired
    private userRepositoryCust curepo;
    public Customer saveCustDetails(Customer cu) throws IOException
    {
        // cu.setFile(file.getBytes());
        return curepo.save(cu);
    }
    public Optional<Customer> findEmailCustomer (String email)
    {
        return curepo.findByEmail(email);
    }
    public List<Customer> getCustomerDetails()
    {
        return curepo.findAll();
    }
    public List<Customer> getUserByStatus(String status)
    {
        return curepo.findByStatus(status);
    }
    public Optional<Customer> downloadfile(int id)
    {
        return curepo.findById(id);
    }
    public void deleterecord(int id)
    {
        curepo.deleteById(id);
    }
    public Optional<Customer> findbyid(int id){
        return curepo.findById(id);
    }
    
}
