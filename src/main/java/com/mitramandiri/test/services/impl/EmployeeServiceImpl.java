package com.mitramandiri.test.services.impl;

import com.mitramandiri.test.dao.EmployeeDao;
import com.mitramandiri.test.dao.PositionDao;
import com.mitramandiri.test.entities.Employees;
import com.mitramandiri.test.entities.Position;
import com.mitramandiri.test.entities.enums.Gender;
import com.mitramandiri.test.exceptions.EntityNotFoundException;
import com.mitramandiri.test.models.EmployeesRequest;
import com.mitramandiri.test.models.PositionRequest;
import com.mitramandiri.test.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private EmployeeDao employeeDao;

    private final DateTimeFormatter formatterDate = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

    @Override
    public Employees save(EmployeesRequest employeeRequest) {
        LocalDateTime birthDateRequest = LocalDateTime.
                parse(employeeRequest.getBirthDate(), formatterDate);

        Position positionSaved = positionDao.findById(employeeRequest.getIdPosition()).orElse(null);

        if(positionSaved.equals(null) || positionSaved.getDelete().equals(true)) {
            return null;
        }

        Employees employeeSaved = new Employees();
        employeeSaved.setName(employeeRequest.getName());
        employeeSaved.setIdPosition(positionSaved);
        employeeSaved.setIdNumber(employeeRequest.getIdNumber());
        employeeSaved.setDelete(false);
        employeeSaved.setBirthDate(birthDateRequest);
        employeeSaved.setGender(Gender.getGender(employeeRequest.getGender()));

        return employeeDao.save(employeeSaved);
    }

    @Override
    public Employees edit(Integer id, EmployeesRequest request) {
        Employees findEmployees = findById(id);
        LocalDateTime birthDateRequest = LocalDateTime.
                parse(request.getBirthDate(), formatterDate);
        if(findEmployees == null || findEmployees.getDelete().equals(true)) {
            return null;
        } else {
            Position positionEdit = positionDao.findById(request.getIdPosition()).orElse(null);

            if(positionEdit.equals(null)) {
                return null;
            }
            findEmployees.setName(request.getName());
            findEmployees.setGender(Gender.getGender(request.getGender()));
            findEmployees.setIdNumber(request.getIdNumber());
            findEmployees.setBirthDate(birthDateRequest);
            findEmployees.setIdPosition(positionEdit);
            findEmployees = employeeDao.save(findEmployees);
            return findEmployees;
        }
    }

    @Override
    public Employees removeById(Integer id) {
        Employees findEmployee = findById(id);
        if(findEmployee != null) {
            findEmployee.setDelete(true);
            findEmployee = employeeDao.save(findEmployee);
            return findEmployee;
        } else {
            return null;
        }
    }

    @Override
    public Employees findById(Integer id) {
        Employees findEmployee = employeeDao.findById(id).orElse(null);
        if(findEmployee == null || findEmployee.getDelete().equals(true)) {
            return null;
        } else {
            return findEmployee;
        }
    }

    @Override
    public List<Employees> findAll() {
        List<Employees> findAllEmployees = employeeDao.findAll().stream().filter(
                p -> p.getDelete().equals(false)).collect(Collectors.toList());
        return findAllEmployees;
    }

    @Override
    public Page<Employees> findAll(Employees search, int page, int size, Sort.Direction direction) {
        return null;
    }
}
