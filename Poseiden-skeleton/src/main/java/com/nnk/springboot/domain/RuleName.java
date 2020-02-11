package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 125)
    @Length(max = 125)
    private String name;

    @Column(length = 125)
    @Length(max = 125)
    private String description;

    @Column(length = 125)
    @Length(max = 125)
    private String json;

    @Column(length = 512)
    @Length(max = 512)
    private String template;

    @Column(length = 125)
    @Length(max = 125)
    private String sqlStr;

    @Column(length = 125)
    @Length(max = 125)
    private String sqlPart;


}
