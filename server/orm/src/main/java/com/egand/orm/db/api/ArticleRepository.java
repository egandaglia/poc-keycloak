package com.egand.orm.db.api;

import com.egand.orm.db.entities.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
}
