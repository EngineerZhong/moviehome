package com.dalididilo.moviehome.index.dao;


import com.dalididilo.moviehome.index.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexDao {



    Employee getEmpById(String empId);


}
