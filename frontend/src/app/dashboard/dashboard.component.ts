import { Component } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import {CreatetaskwindowComponent} from "../createtaskwindow/createtaskwindow.component";
import {KeyValue} from "@angular/common";
import {AuthService} from "../auth.service";
import {OnInit} from "@angular/core";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  bsModalRef!: BsModalRef;
  currentUser: any;

  status_columns_list = {
    'To do': ['Task 1', 'Task 2', 'Task 3'],
    'Doing': [],
    'Finished': [],
    'Expired': ['Task 4', 'Task 5']
  };

  constructor(private authService: AuthService,private modalService: BsModalService) {
  }

  ngOnInit() {
    this.currentUser = localStorage.getItem('userData');

    if (this.currentUser) {
      this.currentUser = JSON.parse(this.currentUser);
      console.log(this.currentUser);
    } else {
      console.log('No user data found in localStorage');
    }
  }


  originalOrder = (a: KeyValue<string, string[]>, b: KeyValue<string, string[]>): number => {
    const order = ['To do', 'Doing', 'Finished', 'Expired'];
    return order.indexOf(a.key) - order.indexOf(b.key);
  };


  openModal() {
    this.bsModalRef = this.modalService.show(CreatetaskwindowComponent);
  }


  getClass(key: string): string {
    switch (key) {
      case "To do":
        return 'assigned-circle';
      case 'Doing':
        return 'progress-circle';
      case 'Finished':
        return 'complete-circle';
      case 'Expired':
        return 'deferred-circle';
      default:
        return '';
    }
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }
}
