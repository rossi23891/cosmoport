package com.space.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Entity
@Table (name= "ships")

public class Ship {

    @Id
    @GeneratedValue
    @NotNull
    @Positive
    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String planet;

    @NotNull
    private ShipType shipType;

    @NotNull
    private Date prodDate;

    @NotNull
    private Boolean isUsed;

    @NotNull
    @DecimalMin(value = "0.01")@DecimalMax(value = "0.99")
    private Double speed;

    @NotNull
    @Min(value = 1)
    @Max(value = 9999)
    private Integer crewSize;

    @NotNull
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
        this.rating = rating;
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
