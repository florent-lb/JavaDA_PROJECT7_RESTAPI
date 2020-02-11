package com.nnk.springboot.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "curvepoint")
@Data
@EqualsAndHashCode(of = "id")
public class CurvePoint {

    @Id
    @GeneratedValue
    private Integer id;

    private Short curveId;

    private LocalDateTime asOfDate;

    private Double term;

    private Double value;

    private LocalDateTime creationDate;


}
