import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ArticleService } from '../article.service';
import { Category } from 'src/app/core/models/category.model';

@Component({
  selector: 'app-article-create',
  templateUrl: './article-create.component.html',
  styleUrls: ['./article-create.component.scss']
})
export class ArticleCreateComponent implements OnInit {
  categories: Category[];
  constructor(private articleService: ArticleService) { }

  ngOnInit(): void {
    this.articleService.getCategoryList().subscribe(result => {
      console.log(result);  
      this.categories = result.content;
    });
  }

  onSubmit(f: NgForm) {
    console.log(f.value);
    
  }

}
