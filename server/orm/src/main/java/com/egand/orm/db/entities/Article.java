package com.egand.orm.db.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@NoArgsConstructor
@Table("t_article")
public class Article {

    @Id
    private Integer id;

    private String title;
    private String slug;
    private String body;

    @Column("image_url")
    private String imageUrl;

    @Column("dt_created")
    private Date dtCreated;

    @Column("dt_updated")
    private Date dtUpdated;

    @Column("author_id")
    private Integer authorId;

    @Column("category_id")
    private Integer categoryId;

}
