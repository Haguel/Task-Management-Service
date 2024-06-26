import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainpageComponent} from "./mainpage/mainpage.component";
import {LoginformComponent} from "./loginform/loginform.component";
import {RegistrationformComponent} from "./registrationform/registrationform.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {AccountpageComponent} from "./accountpage/accountpage.component";

const routes: Routes = [
  {path: '', component: MainpageComponent},
  {path: 'login', component: LoginformComponent},
  {path: 'signup', component: RegistrationformComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'account', component: AccountpageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
