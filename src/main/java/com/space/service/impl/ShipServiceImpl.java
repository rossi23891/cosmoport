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

        return (Specification<Ship>) (root, query, criteriaBuilder) ->
                name ==null?null : criteriaBuilder.like(root.get("name"),"%" + name + "%");
    }

    @Override
    public Specification<Ship> filterByPlanet(String planet) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->
                planet ==null?null : criteriaBuilder.like(root.get("planet"),"%" + planet + "%");
    }

    @Override
    public Specification<Ship> filterByShipType(ShipType shipType) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->
                shipType ==null?null : criteriaBuilder.like(root.get("shiptype"),"%" + shipType + "%");
    }

    @Override
    public Specification<Ship> filterByDate(Long after, Long before) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->
        {
            if(after==null&&before==null){
                return null;
            }
            if(after==null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"),before);
            }
            if(before==null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"),after);
            }
            return criteriaBuilder.between(root.get("prodDate"),after,before);
        };
    }

    @Override
    public Specification<Ship> filterByUsage(Boolean isUsed) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->{
            if(isUsed==null){
                return null;
            }
            if(isUsed){
                return criteriaBuilder.isTrue(root.get("isUsed"));
            }
            return criteriaBuilder.isFalse(root.get("isUsed"));
        };
    }

    @Override
    public Specification<Ship> filterBySpeed(Double min, Double max) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->{
            if (min == null && max == null){
                return null;
            }
            if (min == null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("speed"), max);
            }
            if (max == null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), min);
            }
            return criteriaBuilder.between(root.get("speed"), min, max);
        };
    }

    @Override
    public Specification<Ship> filterByCrewSize(Integer min, Integer max) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->{
            if (min == null && max == null){
                return null;
            }
            if (min == null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), max);
            }
            if (max == null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), min);
            }
            return criteriaBuilder.between(root.get("crewSize"), min, max);
        };
    }

    @Override
    public Specification<Ship> filterByRating(Double min, Double max) {
        return (Specification<Ship>) (root, query, criteriaBuilder) ->{
            if (min == null && max == null){
                return null;
            }
            if (min == null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("rating"), max);
            }
            if (max == null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), min);
            }
            return criteriaBuilder.between(root.get("rating"), min, max);
        };
    }

    @Override
    public long count(Specification<Ship>shipSpecification) {
        return shipRepository.count(shipSpecification);
    }
}
