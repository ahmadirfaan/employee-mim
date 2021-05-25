package com.mitramandiri.test.services.impl;

import com.mitramandiri.test.dao.PositionDao;
import com.mitramandiri.test.entities.Position;
import com.mitramandiri.test.exceptions.EntityNotFoundException;
import com.mitramandiri.test.models.PageSearch;
import com.mitramandiri.test.models.PositionRequest;
import com.mitramandiri.test.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionDao positionDao;


    @Override
    public Position save(PositionRequest request) {
        Position saved = new Position();
        saved.setName(request.getName());
        saved.setCode(request.getCode());
        saved.setDelete(false);
        return positionDao.save(saved);
    }

    @Override
    public Position removeById(Integer id) {
        Position findPosition = findById(id);
        if(findPosition != null) {
            findPosition.setDelete(true);
            findPosition = positionDao.save(findPosition);
            return findPosition;
        } else {
            return null;
        }
    }

    @Override
    public Position findById(Integer id) {
        Position findPosition = positionDao.findById(id).orElse(null);
        if(findPosition == null || findPosition.getDelete().equals(true)) {
            return null;
        } else {
            return findPosition;
        }
    }

    @Override
    public List<Position> findAll() {
        List<Position> findAllPosition = positionDao.findAll().stream().filter(
                p -> p.getDelete().equals(false)).collect(Collectors.toList());
        return findAllPosition;
    }

    @Override
    public Position edit(Integer id, PositionRequest request) {
        Position findPosition = findById(id);
        if(findPosition != null || findPosition.getDelete().equals(true)) {
            return null;
        } else {
            findPosition.setName(request.getName());
            findPosition.setCode(request.getCode());
            findPosition = positionDao.save(findPosition);
            return findPosition;
        }
    }

    @Override
    public Page<Position> findAll(PageSearch pageSearch) {
        Pageable pageable = PageRequest.of(pageSearch.getPage(), pageSearch.getSize());
        return positionDao.findAll(pageable);
    }
}
