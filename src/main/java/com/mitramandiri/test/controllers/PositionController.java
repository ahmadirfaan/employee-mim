package com.mitramandiri.test.controllers;

import com.mitramandiri.test.entities.Position;
import com.mitramandiri.test.exceptions.EntityNotFoundException;
import com.mitramandiri.test.models.*;
import com.mitramandiri.test.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/positions")
@RestController
public class PositionController {

    @Autowired
    private PositionService service;

    @PostMapping
    public ResponseMessage<Position> add(
            @RequestBody @Valid PositionRequest request) {
        Position entity = service.save(request);

        System.out.println(entity);
        return ResponseMessage.success(entity);
    }

    @PutMapping("/{id}")
    public ResponseMessage<Position> edit(@PathVariable Integer id,
                                          @RequestBody @Valid PositionRequest request) {

        Position entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }

        entity = service.edit(id, request);

        return ResponseMessage.success(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Position> removeById(@PathVariable Integer id) {
        Position entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return new ResponseMessage<>(200, "Success Deleted Positions", entity);
    }


    @GetMapping()
    public ResponseMessage<List<Position>> findAll(
            @Valid PageSearch pageSearch
    ) {
        List<Position> entities = service.findAll();
        return ResponseMessage.success(entities);
    }


    @GetMapping("/{id}")
    public ResponseMessage<Position> findById(@PathVariable Integer id) {
        Position entity = service.findById(id);
        if(entity == null || entity.getDelete().equals(true)) {
            throw new EntityNotFoundException();
        }
        return ResponseMessage.success(entity);
    }

}
