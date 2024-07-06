import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {AppComponent} from "../app.component";

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

  registrationResponse: any;
  token: string = '';

  constructor(private authService: AuthService, private router: Router, private appComponent: AppComponent) { }

  registerUser() {
    this.authService.registerUser(this.name, this.username, this.email, this.password)
      .subscribe(
        (response) => {
          this.token = response.token;
          this.registrationResponse = response;
          console.log('Registration successful:', response);
          localStorage.setItem('token', this.token);
          if (this.token != null) {
            this.authService.getUserInfo(this.email).subscribe(
              data => {
                this.appComponent.updateUserData(data);
                this.router.navigate(['/dashboard']);
              }
            );
          }
        },
        (error) => {
          if (error.status == 400) {
            alert("Error, enter valid data");
          } else if (error.status == 409) {
            alert("Error, user with such username or email already exists");
          }
        }
      );
  }

  signUpClick(): void {
    this.registerUser()
  }
}
