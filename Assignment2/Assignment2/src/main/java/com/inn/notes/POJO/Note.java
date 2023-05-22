package com.inn.notes.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Note.getAllNotes", query = "select new com.inn.notes.wrapper.NoteWrapper(n.id, n.title, n.description, n.status, n.category.id, n.category.name) from Note n")
@NamedQuery(name = "Note.updateStatus", query = "update Note n set n.status=:status where n.id=:id")
@NamedQuery(name = "Note.getNoteByCategory", query = "select new com.inn.notes.wrapper.NoteWrapper(n.id, n.title, n.description) from Note n where n.category.id=:id and n.status = 'true'")
@NamedQuery(name = "Note.getNoteById", query = "select new com.inn.notes.wrapper.NoteWrapper(n.id, n.title, n.description) from Note n where n.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "note")
public class Note implements Serializable {

    public static final Long serialVersionUID = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

}
