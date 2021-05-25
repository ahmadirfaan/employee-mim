package com.mitramandiri.test.controllers;

import com.mitramandiri.test.entities.Employees;
import com.mitramandiri.test.entities.Position;
import com.mitramandiri.test.exceptions.EntityNotFoundException;
import com.mitramandiri.test.models.EmployeesRequest;
import com.mitramandiri.test.models.PageSearch;
import com.mitramandiri.test.models.PagedList;
import com.mitramandiri.test.models.ResponseMessage;
import com.mitramandiri.test.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/employees")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseMessage<Employees> add(
            @RequestBody @Valid EmployeesRequest model) {
        Employees employees = employeeService.save(model);
        if(employees == null) {
            throw new EntityNotFoundException();
        }
        return ResponseMessage.success(employees);
    }

    @PutMapping("/{id}")
    public ResponseMessage<Employees> edit(@PathVariable Integer id, @RequestBody @Valid EmployeesRequest request) {

        Employees entity = employeeService.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        entity = employeeService.edit(id, request);

        return ResponseMessage.success(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Employees> removeById(@PathVariable Integer id) {
        Employees entity = employeeService.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return new ResponseMessage<>(200, "Success Deleted Positions", entity);
    }


    @GetMapping()
    public ResponseMessage<PagedList<Employees>> findAll(
            @Valid PageSearch pageSearch
    ) {
        Page<Employees> entityPage = employeeService.findAll(pageSearch);
        List<Employees> employeesList = entityPage.toList();
        PagedList<Employees> data = new PagedList<>(    //Berfungsi untuk mengeliminasi data" pada JSON yang ditampilkan agar lebih informatif
                employeesList,                                 // Dengan menyeleksi kebutuhan field data pada JSON yang diambil adalah page, size dan
                entityPage.getNumber(),                // total elements
                entityPage.getSize(),
                entityPage.getTotalElements());
        return ResponseMessage.success(data);
    }


    @GetMapping("/{id}")
    public ResponseMessage<Employees> findById(@PathVariable Integer id) {
        Employees data = employeeService.findById(id);
        if (data == null || data.getDelete().equals(true)) {
            throw new EntityNotFoundException();
        }
        return ResponseMessage.success(data);
    }

}
