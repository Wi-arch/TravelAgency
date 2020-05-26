package by.education.travel.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_tour_id", nullable = false)
    private Tour tour;
}
