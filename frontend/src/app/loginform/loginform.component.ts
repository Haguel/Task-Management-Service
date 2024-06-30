import { Component } from '@angular/core';
import { AuthService} from "../auth.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-loginform',
  templateUrl: './loginform.component.html',
  styleUrl: './loginform.component.css'
})
export class LoginformComponent {
  email: string = '';
  password: string = '';
  token: string = '';
  errorMessage: string = '';



  constructor(private authService: AuthService) {}

  loginUser() {
    this.authService.loginUser(this.email, this.password)
      .subscribe(
        (response) => {
          this.token = response.token;
          console.log('Login successful! Token:', this.token);
          localStorage.setItem('token', this.token);
        },
        (error: HttpErrorResponse) => {
          console.error('Login failed:', error);
          this.errorMessage = 'Automatic login failed. Please log in manually.';
        }
      );
  }

  loginClick() {
    this.loginUser()
  }

}
