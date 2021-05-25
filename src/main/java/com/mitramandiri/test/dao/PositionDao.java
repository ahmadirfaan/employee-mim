package com.mitramandiri.test.dao;

import com.mitramandiri.test.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PositionDao extends JpaRepository<Position, Integer> {
}
