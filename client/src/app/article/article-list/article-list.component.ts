import { Component, OnInit } from '@angular/core';
import { Article } from 'src/app/core/models/article.model';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss']
})
export class ArticleListComponent implements OnInit {

  results: Article[];

  constructor() { }

  ngOnInit(): void {
    this.results = [];
  }

}
