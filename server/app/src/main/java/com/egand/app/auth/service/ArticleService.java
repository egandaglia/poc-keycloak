package com.egand.app.auth.service;

import com.egand.app.auth.dto.article.ArticleData;
import com.egand.app.auth.mapper.ArticleMapper;
import com.egand.app.common.BaseService;
import com.egand.orm.db.api.ArticleRepository;
import com.egand.orm.db.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends BaseService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Integer createArticle(ArticleData articleData, Integer userId) {
        // TODO validation of articleData
        Article article = ArticleMapper.INSTANCE.toArticle(articleData);
        article.setAuthorId(userId);
        article = articleRepository.save(article);
        return article.getId();
    }

}
