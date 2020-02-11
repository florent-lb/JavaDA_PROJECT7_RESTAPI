package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 125)
    @Length(max = 125)
    private String moodysRating;

    @Column(length = 125)
    @Length(max = 125)
    private String sandPRating;

    @Column(length = 125)
    @Length(max = 125)
    private String fitchRating;

    @Column(length = 125)
    @Length(max = 125)
    private String orderNumber;

}
