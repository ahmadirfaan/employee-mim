package com.mitramandiri.test.dao;

import com.mitramandiri.test.entities.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EmployeeDao extends JpaRepository<Employees, Integer> {

}
