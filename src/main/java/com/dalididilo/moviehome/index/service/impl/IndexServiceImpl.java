package com.dalididilo.moviehome.index.service.impl;

import com.dalididilo.moviehome.index.bean.Employee;
import com.dalididilo.moviehome.index.dao.IndexDao;
import com.dalididilo.moviehome.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {


    @Autowired
    private IndexDao idexDao;


    @Override
    public Employee getEmpById(String empId) {
        return idexDao.getEmpById(empId);
    }
}
