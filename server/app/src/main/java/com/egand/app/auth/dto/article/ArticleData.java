package com.egand.app.auth.dto.article;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleData {

    private String title;
    private String slug;
    private String imageUrl;
    private String body;
    private String authorSerialCode;
    private Integer categoryId;

}
