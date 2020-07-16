import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ArticleRoutingModule } from './article-routing.module';
import { ArticleCreateComponent } from './article-create/article-create.component';
import { ArticleListComponent } from './article-list/article-list.component';
import { ArticleDetailComponent } from './article-detail/article-detail.component';
import { ArticlePreviewComponent } from './article-preview/article-preview.component';
import { HttpClientModule } from '@angular/common/http';
import { ArticleComponent } from './article/article.component';


@NgModule({
  declarations: [
    ArticleListComponent,
    ArticleDetailComponent,
    ArticlePreviewComponent,
    ArticleCreateComponent,
    ArticleComponent
  ],
  imports: [
    CommonModule,
    ArticleRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  exports: [
    FormsModule
  ]
})
export class ArticleModule { }
