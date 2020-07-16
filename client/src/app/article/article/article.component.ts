import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-article',
  template: '<router-outlet></router-outlet>',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
