package by.education.travel.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "tour_name", nullable = false)
    private String name;

    @Column(name = "tour_price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(name = "tour_start_date", nullable = false)
    private Date startDate;

    @Column(name = "tour_end_date", nullable = false)
    private Date endDate;
}
