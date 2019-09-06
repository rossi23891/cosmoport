package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShipController {

    @Autowired
    private ShipService shipService;

    @RequestMapping(value = "/ships",method = RequestMethod.GET)// без фильтров просто возвращает все корабли
    public List<Ship> getAllShips(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "planet", required = false) String planet,
                                  @RequestParam(value = "shipType", required = false) ShipType shipType,
                                  @RequestParam(value = "after", required = false) Long after,
                                  @RequestParam(value = "before", required = false) Long before,
                                  @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                  @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                  @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                  @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                  @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                  @RequestParam(value = "minRating", required = false) Double minRating,
                                  @RequestParam(value = "maxRating", required = false) Double maxRating,
                                  @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {


        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        return shipService.getAllShips(
                Specification.where(shipService.filterByName(name)
                        .and(shipService.filterByPlanet(planet)))
                        .and(shipService.filterByShipType(shipType))
                        .and(shipService.filterByDate(after, before))
                        .and(shipService.filterByUsage(isUsed))
                        .and(shipService.filterBySpeed(minSpeed, maxSpeed))
                        .and(shipService.filterByCrewSize(minCrewSize, maxCrewSize))
                        .and(shipService.filterByRating(minRating, maxRating)), pageable)
                .getContent();
    }

    @RequestMapping(value = "/ships/count", method = RequestMethod.GET)
    public Count getCount(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "planet", required = false) String planet,
                            @RequestParam(value = "shipType", required = false) ShipType shipType,
                            @RequestParam(value = "after", required = false) Long after,
                            @RequestParam(value = "before", required = false) Long before,
                            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                            @RequestParam(value = "minRating", required = false) Double minRating,
                            @RequestParam(value = "maxRating", required = false) Double maxRating) {

        Integer count= shipService.getAllShips(
                Specification.where(shipService.filterByName(name)
                        .and(shipService.filterByPlanet(planet)))
                        .and(shipService.filterByShipType(shipType))
                        .and(shipService.filterByDate(after, before))
                        .and(shipService.filterByUsage(isUsed))
                        .and(shipService.filterBySpeed(minSpeed, maxSpeed))
                        .and(shipService.filterByCrewSize(minCrewSize, maxCrewSize))
                        .and(shipService.filterByRating(minRating, maxRating)))
                .size();
        return new Count(count);
    }

    @PostMapping(value = "/ships")
    public @ResponseBody Ship addShip(@Valid@RequestBody Ship ship) {
         try{
             shipService.addShip(ship);
         } catch (ConstraintViolationException e){
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
         }
         return ship;
    }

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.GET)
    public @ResponseBody Ship getShipById(@PathVariable("id")@Valid Long id) {
        Ship ship;
        if(!shipService.ifIdExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not possible to get data. ID " + id
                    + " doesn't exist");
        }
        try{
            ship= shipService.getShipById(id);
        } catch (ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return ship;
    }

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.DELETE)
    public void deleteShipById(@PathVariable("id")@Valid Long id){
        if(!shipService.ifIdExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not possible to delete data. ID " + id
                    + " doesn't exist");
        }
        try{
            shipService.deleteShip(id);
        } catch (ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

}
