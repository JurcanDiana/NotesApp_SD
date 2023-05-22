package com.inn.notes.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Report.getAllReports", query = "select r from Report r order by r.id desc")
@NamedQuery(name = "Report.getReportByUsername", query = "select r from Report r where r.createdBy=:username order by r.id desc")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "report")
public class Report implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "notedetails", columnDefinition = "json")
    private String noteDetails;

    @Column(name = "createdby")
    private String createdBy;
}
