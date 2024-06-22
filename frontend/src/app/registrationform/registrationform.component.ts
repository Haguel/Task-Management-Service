import { Component } from '@angular/core';

@Component({
  selector: 'app-registrationform',
  templateUrl: './registrationform.component.html',
  styleUrl: './registrationform.component.css'
})
export class RegistrationformComponent {
  username: string = ''
  password: string = ''
  email: string = ''

  myClick() {
    alert("hello world")
  }
}
