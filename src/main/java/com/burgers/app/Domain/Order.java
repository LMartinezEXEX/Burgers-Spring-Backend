package com.burgers.app.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
@Entity
@Table(name="Burger_Order")
public class Order  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Transient
    private Long nextBurgerId = 1L;

    private Date placedAt;

    @NotNull
    @Range(min = 0)
    private Long price = 120L;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    private String zip;

    @NotNull
    private boolean usingFreeDelivery;

    @ManyToMany(targetEntity=Burger.class)
    private List<Burger> burgers = new ArrayList<>();

    @ManyToOne(targetEntity=User.class)
    private User user;

    public void addDesign(Burger design){
        design.setBurgerInOrderId(this.nextBurgerId++);
        this.burgers.add(design);
    }

    public void calculatePrice() {
        for(Burger burger: this.burgers)
            this.price += burger.getPrice();
    }

    public Burger getBurgerByBurgerInOrderId(Long id){
        for(Burger burger : this.burgers){
            if(burger.getBurgerInOrderId().equals(id))
                return burger;
        }
        return null;
    }

    public Optional<Burger> getBurgerById(Long id){
        for(Burger burger : this.burgers){
            if (burger.getId().equals(id))
                return Optional.of(burger);
        }
        return Optional.empty();
    }

    public boolean isValid() {
        if(this.burgers.isEmpty()) return false;

        for(Burger burger : this.burgers) {
            if(!burger.isValid()) return false;
        }

        return true;
    }

    public void apply(Coupon coupon) {
        Long discount = coupon.getDiscount();
        Long price = Math.max(0, this.getPrice()-discount);
        
        this.setPrice(price);
    }

    @PrePersist
    public void placedAt(){
        this.placedAt = new Date();
    }

    public Order() {

    }

    public Order(String name, String street, String city, String state, String zip) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public void completeDeliveryData(User user, String name, String street, String city, String state, String zip, boolean usingFreeDelivery) {
        this.setUser(user);
        this.setName(name);
        this.setStreet(street);
        this.setCity(city);
        this.setState(state);
        this.setZip(zip);
        this.setUsingFreeDelivery(usingFreeDelivery);

        if(isUsingFreeDelivery()) this.setPrice(Math.max(0, this.getPrice() - 120L));
    }
}
