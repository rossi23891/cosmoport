package com.space.service.impl;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public List<Ship> getAllShips(Specification<Ship>shipSpecification) {
        return shipRepository.findAll();
    }


    @Override
    public Page<Ship> getAllShips(Specification<Ship> shipSpecification, Pageable pageable) {
        return shipRepository.findAll(shipSpecification,pageable);
    }

    @Override
    public Ship addShip(Ship ship) {

        Ship savedShip = shipRepository.saveAndFlush(ship);
        return savedShip;
    }

    @Override
    public Ship editShip(Ship ship) {
        return shipRepository.saveAndFlush(ship);
    }

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);

    }

    @Override
    public Ship getShipById(Long id) {

        return shipRepository.getOne(id);
    }

    @Override
    public Specification<Ship> filterByName(String name) {

        return (Specification<Ship>) (root, query, criteriaBuilder) -> name ==null?null : criteriaBuilder.like(root.get("name"),"%" + name + "%");
    }

    @Override
    public Specification<Ship> filterByPlanet(String planet) {
        return null;
    }

    @Override
    public Specification<Ship> filterByShipType(ShipType shipType) {
        return null;
    }

    @Override
    public Specification<Ship> filterByDate(Long after, Long before) {
        return null;
    }

    @Override
    public Specification<Ship> filterByUsage(Boolean isUsed) {
        return null;
    }

    @Override
    public Specification<Ship> filterBySpeed(Double min, Double max) {
        return null;
    }

    @Override
    public Specification<Ship> filterByCrewSize(Integer min, Integer max) {
        return null;
    }

    @Override
    public Specification<Ship> filterByRating(Double min, Double max) {
        return null;
    }

    @Override
    public long count(Specification<Ship>shipSpecification) {
        return shipRepository.count(shipSpecification);
    }
}
