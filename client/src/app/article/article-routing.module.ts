import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ArticleCreateComponent } from './article-create/article-create.component';
import { ArticleComponent } from './article/article.component';
import { ArticleGuard } from './article.guard';
import { ArticleListComponent } from './article-list/article-list.component';
import { ArticleDetailComponent } from './article-detail/article-detail.component';


const routes: Routes = [
  {
    path: 'article',
    component: ArticleComponent,
    canActivate: [ArticleGuard],
    children: [
      {
        path: '',
        children: [
          {
            path: 'list', component: ArticleListComponent
          },
          {
            path: 'create', component: ArticleCreateComponent
          },
          {
            path: 'detail/:id', component: ArticleDetailComponent
          }
        ]
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticleRoutingModule { }
