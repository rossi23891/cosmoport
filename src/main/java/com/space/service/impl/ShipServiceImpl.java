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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

        if(ship.getUsed()==null){
            ship.setUsed(false);
        }

        Double rating = calculateRating(ship);
        ship.setRating(rating);

        return shipRepository.saveAndFlush(ship);
    }

    public boolean ifIdExists(Long id){
        return shipRepository.existsById(id);
    }

    @Override
    public Ship editShip(Long id,Ship ship) {
        Ship tobeUpdated = shipRepository.getOne(id);
        if(ship.getName()!=null){
            tobeUpdated.setName(ship.getName());
        }
        if(ship.getPlanet()!=null){
            tobeUpdated.setPlanet(ship.getPlanet());
        }
        if(ship.getShipType()!=null){
            tobeUpdated.setShipType(ship.getShipType());
        }

        if(ship.getProdDate()!=null){
            tobeUpdated.setProdDate(ship.getProdDate());
        }
        if(ship.getUsed()!=null){
            tobeUpdated.setUsed(ship.getUsed());
        }
        if(ship.getSpeed()!=null){
            tobeUpdated.setSpeed(ship.getSpeed());
        }
        if(ship.getCrewSize()!=null){
            tobeUpdated.setCrewSize(ship.getCrewSize());
        }

        Double newRating = calculateRating(tobeUpdated);
        tobeUpdated.setRating(newRating);
        return shipRepository.saveAndFlush(tobeUpdated);
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
                Date beforeDate = new Date(before);
                return criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"),beforeDate);
            }
            if(before==null){
                Date afterDate = new Date(after);
                return criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"),afterDate);
            }
            Date beforeDate = new Date(before);
            Date afterDate = new Date(after);
            return criteriaBuilder.between(root.get("prodDate"),afterDate,beforeDate);
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


    private Double calculateRating(Ship ship){
        double k = ship.getUsed() ? 0.5 : 1.0;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(ship.getProdDate());
        int year = calendar.get(Calendar.YEAR);
        Double currentRating = ((80*ship.getSpeed()*k)/(3019-year+1));
        currentRating = Math.round(currentRating*100.0)/100.0;
        return currentRating;
    }
}
