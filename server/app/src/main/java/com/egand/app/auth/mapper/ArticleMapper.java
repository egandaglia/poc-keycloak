package com.egand.app.auth.mapper;

import com.egand.app.auth.dto.article.ArticleData;
import com.egand.orm.db.entities.Article;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    Article toArticle(ArticleData articleData);
}
