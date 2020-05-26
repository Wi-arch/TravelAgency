package by.education.travel.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

}
