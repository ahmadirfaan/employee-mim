package com.mitramandiri.test.services;

import com.mitramandiri.test.entities.Position;
import com.mitramandiri.test.models.PositionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PositionService {
    public Position save(PositionRequest request);

    public Position removeById(Integer id);

    public Position findById(Integer id);

    public List<Position> findAll();

    public Position edit(Integer id, PositionRequest request);

    public Page<Position> findAll(Position search, Integer page, Integer size, Sort.Direction direction);
}
