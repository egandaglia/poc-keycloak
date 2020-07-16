package com.egand.app.auth.controller;

import com.egand.app.auth.dto.article.ArticleData;
import com.egand.app.auth.service.ArticleService;
import com.egand.app.auth.service.UserService;
import com.egand.app.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/article")
public class ArticleController extends BaseController {

    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Integer> create(@RequestBody ArticleData articleData) {
        Integer userId = userService.getUserId(articleData.getAuthorSerialCode());
        Integer articleId = articleService.createArticle(articleData, userId);
        return ResponseEntity.ok(articleId);
    }




}
