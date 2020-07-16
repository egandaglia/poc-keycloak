import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from 'src/app/core/models/category.model';
import { environment } from 'src/environments/environment';
import { ApiWrapper } from 'src/app/core/models/api-wrapper.model';
import { Article } from '../core/models/article.model';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private http: HttpClient) { }

  getCategoryList() {
    return this.http.get<ApiWrapper<Category>>(`${environment.baseUrl}/api/categories/search/findAll`);
  }

  createArticle(
    title: string, slug: string, imageUrl: string, categoryId: number) {
      let article: Article;
      article.title = title;
      article.slug = slug;
      article.imageUrl = imageUrl;
      article.categoryId = categoryId;
  }
}
