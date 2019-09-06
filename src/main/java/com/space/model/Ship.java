package com.space.model;

import com.space.validator.ProdDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table (name= "ship")

public class Ship {

    @Id
    @GeneratedValue
    @Positive(message = "ID should be number greater than 0")
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 50,message = "Name length should not be more than 50 symbols")
    private String name;

    @NotEmpty(message = "Planet should not be empty")
    @Size(max = 50,message = "Planet length should not be more than 50 symbols")
    private String planet;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ShipType shipType;

    @NotNull
    @ProdDate
    private Date prodDate;

    private Boolean isUsed;

    @NotNull
    @DecimalMin(value = "0.01",message = "Speed should start from 0.01")
    @DecimalMax(value = "0.99",message = "Speed should not be higher 0.99")
    private Double speed;

    @NotNull
    @Min(value = 1,message = "Crew size should be more than 0")
    @Max(value = 9999,message = "Crew size should be more than 10000")
    private Integer crewSize;

    private Double rating;

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

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
