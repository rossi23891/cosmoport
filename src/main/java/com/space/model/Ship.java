package com.space.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Entity
@Table (name= "ships")

public class Ship {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String planet;
    private ShipType shipType;
    private Date prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public Ship(Long id, String name, String planet, ShipType shipType, Date prodDate,
                Boolean isUsed, Double speed, Integer crewSize, Double rating) {
        this.id = id;
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating() {
        this.rating = calculateRating();
    }

    private Double calculateRating(){
        double k = isUsed ? 0.5 : 1.0;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getProdDate());
        int year = calendar.get(Calendar.YEAR);
        Double currentRating = ((80*getSpeed()*k)/(3019-year+1));
        currentRating = Math.round(currentRating*100.0)/100.0;
        return currentRating;
    }
}
