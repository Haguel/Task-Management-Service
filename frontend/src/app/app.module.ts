import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainpageComponent } from './mainpage/mainpage.component';
import { RegistrationformComponent } from './registrationform/registrationform.component';
import { LoginformComponent } from './loginform/loginform.component';

@NgModule({
  declarations: [
    AppComponent,
    MainpageComponent,
    RegistrationformComponent,
    LoginformComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
