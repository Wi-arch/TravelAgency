package by.education.travel.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "conuntry")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "country_name", nullable = false)
    private String name;
}
