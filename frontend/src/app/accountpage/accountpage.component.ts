import {Component, OnInit} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Router} from "@angular/router";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-accountpage',
  templateUrl: './accountpage.component.html',
  styleUrl: './accountpage.component.css'
})
export class AccountpageComponent implements OnInit {
  currentUser: any;
  private currentUserSubject = new BehaviorSubject<any>(null);

  all_tasks_count = 0;
  doing_tasks_count = 0;
  finished_tasks_count = 0;
  expired_tasks_count = 0;

  tasks_counts: any;

  constructor(private router: Router, private appComponent: AppComponent) {
  }

  ngOnInit() {
    const storedUser = localStorage.getItem('userData');
    if (storedUser) {
      this.currentUser = JSON.parse(storedUser);
      this.currentUserSubject.next(this.currentUser);
    } else {
      console.log('No user data found in localStorage');
    }

    let tasksCountData = localStorage.getItem('tasksCountData');
    if (tasksCountData) {
      this.tasks_counts = JSON.parse(tasksCountData);
    }

    this.all_tasks_count = this.tasks_counts.all;
    this.doing_tasks_count = this.tasks_counts.doing;
    this.finished_tasks_count = this.tasks_counts.finished;
    this.expired_tasks_count = this.tasks_counts.expired;
  }

  logout() {
    this.currentUser = null;
    localStorage.removeItem('userData');
    localStorage.removeItem('token');
    this.currentUserSubject.next(this.currentUser);
    this.router.navigate(['']);
    this.appComponent.updateUserData(this.currentUser)
  }
}
