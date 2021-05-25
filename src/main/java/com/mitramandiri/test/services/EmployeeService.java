package com.mitramandiri.test.services;

import com.mitramandiri.test.entities.Employees;
import com.mitramandiri.test.entities.Position;
import com.mitramandiri.test.models.EmployeesRequest;
import com.mitramandiri.test.models.PageSearch;
import com.mitramandiri.test.models.PositionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface EmployeeService {
    public Employees save(EmployeesRequest t);

    public Employees edit(Integer id, EmployeesRequest request);

    public Employees removeById(Integer id);

    public Employees findById(Integer id);

    public List<Employees> findAll();

    public Page<Employees> findAll(PageSearch pageSearch);
}