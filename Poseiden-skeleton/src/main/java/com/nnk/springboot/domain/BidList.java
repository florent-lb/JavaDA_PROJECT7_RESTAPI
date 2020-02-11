package com.nnk.springboot.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Table(name = "bidlist")
@Data
@EqualsAndHashCode(of = "bidListId")
@NoArgsConstructor
@RequiredArgsConstructor
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;

    @Column(length = 30)
    @Length(max = 30)
    @NotBlank
    @NonNull
    private String account;

    @Column(length = 30)
    @Length(max = 30)
    @NotBlank
    @NonNull
    private String type;

    @PositiveOrZero
    @NonNull
    private Double bidQuantity;

    private Double askQuantity;

    private Double bid;

    private Double ask;

    @Column(length = 125)
    @Length(max = 125)
    private String benchmark;

    private LocalDateTime bidListDate;

    @Column(length = 125)
    @Length(max = 125)
    private String commentary;

    @Column(length = 125)
    @Length(max = 125)
    private String security;

    @Column(length = 125)
    @Length(max = 125)
    private String status;

    @Column(length = 125)
    @Length(max = 125)
    private String trader;

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
