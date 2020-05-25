package by.education.travel.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Tour {

    private int id;
    private String name;
    private BigDecimal price;
    private Country country;
    private Hotel hotel;
    private Date startDate;
    private Date endDate;
}
