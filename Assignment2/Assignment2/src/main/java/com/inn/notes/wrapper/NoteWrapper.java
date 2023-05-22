package com.inn.notes.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoteWrapper {

    private Integer id;
    private String title;
    private String description;
    private String status;
    private Integer categoryId;
    private String categoryName;

    public NoteWrapper(Integer id, String title, String description, String status, Integer categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public NoteWrapper(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
