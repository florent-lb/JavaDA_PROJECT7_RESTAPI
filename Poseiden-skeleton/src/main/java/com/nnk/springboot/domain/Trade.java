package com.nnk.springboot.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "trade")
@Data
@EqualsAndHashCode(of = "tradeId")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @Column(length = 30)
    @Length(max = 30)
    private String account;

    @Column(length = 30)
    @Length(max = 30)
    private String type;

    private Double buyQuantity;

    private Double sellQuantity;

    private Double buyPrice;

    private Double sellPrice;

    private LocalDateTime tradeDate;

    @Column(length = 125)
    @Length(max = 125)
    private String security;

    @Column(length = 10)
    @Length(max = 10)
    private String status;

    @Column(length = 125)
    @Length(max = 125)
    private String trader;

    @Column(length = 125)
    @Length(max = 125)
    private String benchmark;

    @Column(length = 125)
    @Length(max = 125)
    private String book;

    @Column(length = 125)
    @Length(max = 125)
    private String creationName;

    private LocalDateTime creationDate;

    @Column(length = 125)
    @Length(max = 125)
    private String revisionName;

    private LocalDateTime revisionDate;

    @Column(length = 125)
    @Length(max = 125)
    private String dealName;

    @Column(length = 125)
    @Length(max = 125)
    private String dealType;

    @Column(length = 125)
    @Length(max = 125)
    private String sourceListId;

    @Column(length = 125)
    @Length(max = 125)
    private String side;

}
