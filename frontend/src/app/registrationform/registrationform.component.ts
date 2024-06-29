import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-registrationform',
  templateUrl: './registrationform.component.html',
  styleUrl: './registrationform.component.css'
})
export class RegistrationformComponent {
  name: string = '';
  username: string = '';
  password: string = '';
  email: string = '';

  isValidEmail: boolean = true;
  isValidPassword: boolean = true;
  isValidUsername: boolean = true;
  isValidName: boolean = true;
  isValidForm: boolean = true;

  registrationResponse: any;
  token: string = '';

  constructor(private authService: AuthService) { }

  checkEmailValid() {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    this.isValidEmail = emailPattern.test(this.email);
  }

  checkPasswordValid() {
    const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,50}$/;
    this.isValidPassword = passwordRegex.test(this.password);
  }

  checkUsernameValid() {
    const usernameRegex = /^[a-zA-Z0-9]{4,20}$/;
    this.isValidUsername = usernameRegex.test(this.username);
  }

  checkNameValid() {
    if (this.name.length <= 1) {
      this.isValidName = false;
    } else this.isValidName = true;
  }

  registerUser() {
    this.authService.registerUser(this.name, this.username, this.email, this.password)
      .subscribe(
        (response) => {
          this.token = response.token;
          this.registrationResponse = response;
          console.log('Registration successful:', response);
          localStorage.setItem('token', this.token);
        },
        (error) => {
          console.error('Registration failed:', error);
        }
      );
  }

  signUpClick(): void {
    if (this.username == '') {
      this.isValidUsername = false
      this.isValidForm = false
    } if (this.name == '') {
      this.isValidName = false
      this.isValidForm = false
    } if (this.email == '') {
      this.isValidEmail = false
      this.isValidForm = false
    } if (this.password == '') {
      this.isValidPassword = false
      this.isValidForm = false
    }
    if (this.isValidForm) {
      this.registerUser()
    }
    else {
      alert("Please enter a valid data");
    }
  }
}
