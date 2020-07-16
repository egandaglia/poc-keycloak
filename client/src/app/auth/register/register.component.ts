import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(f: NgForm) {
    console.log(f.value);
    let input = f.value;
    if(input.password != input.rePassword) {
      // handle error
      return;
    }
    this.authService.signUp(input.serialCode, input.firstName, input.lastName, input.password)
    .subscribe(response => {
      console.log(response);
    });
  }

}
