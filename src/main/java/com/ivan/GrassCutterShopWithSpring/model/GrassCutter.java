package com.ivan.GrassCutterShopWithSpring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class GrassCutter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double price;

    @Enumerated(value = EnumType.STRING)
    private GrassCutterType type;

    @Enumerated(value = EnumType.STRING)
    private GrassCutterBrand brand;

    @OneToMany(mappedBy = "grassCutter")
    private List<Order> orders;

    public GrassCutter(long id, String name, Double price, GrassCutterType type, GrassCutterBrand brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.brand = brand;
    }

    public GrassCutter( String name, Double price, GrassCutterType type, GrassCutterBrand brand) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.brand = brand;
    }
}
