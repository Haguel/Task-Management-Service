import { Component } from '@angular/core';
import { AuthService } from "../auth.service";
import { HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { AppComponent } from "../app.component";

@Component({
  selector: 'app-loginform',
  templateUrl: './loginform.component.html',
  styleUrls: ['./loginform.component.css']
})
export class LoginformComponent {
  email: string = '';
  password: string = '';
  token: string = '';
  status: string = '';

  constructor(private authService: AuthService, private router: Router, private appComponent: AppComponent) {}

  loginUser() {
    this.authService.loginUser(this.email, this.password)
      .subscribe(
        (response) => {
          this.token = response.token;
          console.log('Login successful! Token:', this.token);
          localStorage.setItem('token', this.token);

          if (this.token != null) {
            this.authService.getUserInfo(this.email).subscribe(
              data => {
                console.log(data);
                this.appComponent.updateUserData(data);
                this.router.navigate(['/dashboard']);
              }
            );
          }
        },
        (error: HttpErrorResponse) => {
          if (error.status == 400) alert("Error, invalid data provided!")
          else if (error.status == 401) alert("Error, incorrect username or email or password!");
          else if (error.status == 404) alert("Error, can not find authenticated user by provided email!");
        }
      );
  }
}
