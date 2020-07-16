import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  name: String;
  date: number;
  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.loadKeycloakUserProfile().then(profile => {
      this.name = profile.firstName;
    });
    
    this.date = Date.now();
  }

}
