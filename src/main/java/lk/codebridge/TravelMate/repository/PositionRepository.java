package lk.codebridge.TravelMate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.codebridge.TravelMate.entity.Position;


public interface PositionRepository extends JpaRepository<Position, Integer> {
    
    
    Position findByName(String name);

}
