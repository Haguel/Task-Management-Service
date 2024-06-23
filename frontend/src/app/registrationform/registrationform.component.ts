import { Component } from '@angular/core';

@Component({
  selector: 'app-registrationform',
  templateUrl: './registrationform.component.html',
  styleUrl: './registrationform.component.css'
})
export class RegistrationformComponent {
  username: string = '';
  password: string = '';
  email: string = '';
  isValidEmail: boolean = true;
  isValidPassword: boolean = true;
  isValidUsername: boolean = true;



  checkEmailValid() {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    this.isValidEmail = emailPattern.test(this.email);
  }

  checkPasswordValid() {
    const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}$/;
    this.isValidPassword = passwordRegex.test(this.password);
  }

  checkUsernameValid() {
    if (this.username.length < 5) this.isValidUsername = false;
    else this.isValidUsername = true;
  }

  signUpClick(): void {
    if (this.username == '' || this.password == '' || this.email == '') {
      this.isValidUsername = false
      this.isValidPassword = false
      this.isValidEmail = false
      alert('Please enter a valid data');
    }
  }
}
