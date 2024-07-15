import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {GitService} from "./git.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  currentUser: any = null;
  private currentUserSubject = new BehaviorSubject<any>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private gitService: GitService) {}

  ngOnInit(): void {
    this.gitService.getUserData('quk1se')
        .subscribe(data => {
          localStorage.setItem('quk1seData', JSON.stringify(data));
        });

    this.gitService.getUserData('haguel')
        .subscribe(data => {
          localStorage.setItem('haguelData', JSON.stringify(data));
        });

    const storedUser = localStorage.getItem('userData');
    if (storedUser) {
      this.currentUser = JSON.parse(storedUser);
      this.currentUserSubject.next(this.currentUser);
    } else {
      console.log('No user data found in localStorage');
    }

    this.currentUser$.subscribe(user => {
      this.currentUser = user;
    });

    console.log(this.currentUser);
  }

  updateUserData(userData: any) {
    this.currentUser = userData;
    this.currentUserSubject.next(this.currentUser);
    localStorage.setItem('userData', JSON.stringify(userData));
  }

}
