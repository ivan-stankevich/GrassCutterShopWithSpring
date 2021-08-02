package com.ivan.GrassCutterShopWithSpring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "grass_cutter_id" )
    private GrassCutter grassCutter;

    public Order(User user, GrassCutter grassCutter) {
        this.user = user;
        this.grassCutter = grassCutter;
    }
}
