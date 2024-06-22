import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainpageComponent} from "./mainpage/mainpage.component";
import {LoginformComponent} from "./loginform/loginform.component";
import {RegistrationformComponent} from "./registrationform/registrationform.component";

const routes: Routes = [
  {path: '', component: MainpageComponent},
  {path: 'login', component: LoginformComponent},
  {path: 'signup', component: RegistrationformComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
