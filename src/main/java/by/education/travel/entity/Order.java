package by.education.travel.entity;

import lombok.Data;

@Data
public class Order {

    private int id;
    private User user;
    private Tour tour;
}
