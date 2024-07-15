import { Component } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {BsModalService, BsModalRef, ModalOptions} from 'ngx-bootstrap/modal';
import {CreatetaskwindowComponent} from "../createtaskwindow/createtaskwindow.component";
import {KeyValue} from "@angular/common";
import {OnInit} from "@angular/core";
import {TaskService} from "../task.service";
import {RedacttaskwindowComponent} from "../redacttaskwindow/redacttaskwindow.component";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  bsModalRef!: BsModalRef;
  currentUser: any;
  searchName: string = '';
  status_list = ['To do', 'Doing', 'Finished', 'Expired'];


  to_do_list : any[] = [];
  doing_list : any[] = [];
  finished_list : any[] = [];
  expired_list : any[] = [];



  constructor(private taskService: TaskService,private modalService: BsModalService) {
  }

  ngOnInit() {
    this.currentUser = localStorage.getItem('userData');

    if (this.currentUser) {
      this.currentUser = JSON.parse(this.currentUser);
    } else {
      console.log('No user data found in localStorage');
    }

    this.getUserTasks(this.searchName);

    this.taskService.toDoList$.subscribe(tasks => this.to_do_list = tasks);
    this.taskService.doingList$.subscribe(tasks => this.doing_list = tasks);
    this.taskService.finishedList$.subscribe(tasks => this.finished_list = tasks);
    this.taskService.expiredList$.subscribe(tasks => this.expired_list = tasks);
  }

  getUserTasks(filter: string) {
    this.taskService.getTasks().subscribe(
      response => {
        let toDoTasks: any[] = [];
        let doingTasks : any[] = [];
        let finishedTasks : any[] = [];
        let expiredTasks : any[] = [];

        for (let task of response) {
          switch (task.status) {
            case "TODO":
              this.checkAndPushByFilter(task.title, task, toDoTasks)
              break;
            case "DOING":
              this.checkAndPushByFilter(task.title, task, doingTasks)
              break;
            case "FINISHED":
              this.checkAndPushByFilter(task.title, task, finishedTasks)
              break;
            case "EXPIRED":
              this.checkAndPushByFilter(task.title, task, expiredTasks)
              break;
          }
        }

        this.taskService.updateToDoList(toDoTasks);
        this.taskService.updateDoingList(doingTasks);
        this.taskService.updateFinishedList(finishedTasks);
        this.taskService.updateExpiredList(expiredTasks);

        this.getAndSendTasksInfoToAccount(toDoTasks, doingTasks, finishedTasks, expiredTasks);
      },
      error => {
        if (error.status == 401) {
          alert("Error, request provided without token!");
        }
      }
    );
  }

  getAndSendTasksInfoToAccount(toDoTasks: any[], doingTasks: any[], finishedTasks: any[], expiredTasks: any[]) {
    let tasksCountData = {
      all: toDoTasks.length + doingTasks.length + finishedTasks.length + expiredTasks.length,
      doing: doingTasks.length,
      finished: finishedTasks.length,
      expired: expiredTasks.length
    };
    console.log(tasksCountData)
    localStorage.setItem('tasksCountData', JSON.stringify(tasksCountData));
  }

  checkAndPushByFilter(title: string, task: string, status_list: any[]) {
    if (this.searchName.toLowerCase() !== '') {
      if (title.toLowerCase().includes(this.searchName)) {
        status_list.push(task);
      }
    } else {
      status_list.push(task);
    }
  }

  openCreateModal() {
    const config: ModalOptions = {
      backdrop: 'static' as 'static',
      keyboard: false,
    };
    this.bsModalRef = this.modalService.show(CreatetaskwindowComponent, config);
  }

  openRedactModal(task: any) {
    const config: ModalOptions = {
      backdrop: 'static' as 'static',
      keyboard: false,
    };
    localStorage.setItem('currentTaskForRedact', JSON.stringify(task));
    this.bsModalRef = this.modalService.show(RedacttaskwindowComponent, config);
    // @ts-ignore
    this.bsModalRef.onHide.subscribe(() => {
      this.getUserTasks(this.searchName);
    });
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

  drop(event: CdkDragDrop<any[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      const task = event.previousContainer.data[event.previousIndex];
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );

      console.log(task)

      let newStatus: string;
      switch (event.container.id) {
        case 'cdk-drop-list-0':
          newStatus = 'TODO';
          break;
        case 'cdk-drop-list-1':
          newStatus = 'DOING';
          break;
        case 'cdk-drop-list-2':
          newStatus = 'FINISHED';
          break;
        case 'cdk-drop-list-3':
          newStatus = 'EXPIRED';
          break;
        default:
          newStatus = task.status;
      }
      task.status = newStatus;

      let currTask = {
        "taskId": task.id,
        "title": task.title,
        "description": task.description,
        "untilDate": this.taskService.convertToISO(task.untilDate),
        "status": task.status
      }

      console.log(currTask)
      this.taskService.updateTaskStatus(currTask).subscribe(
        response => {
          console.log('Task status updated successfully:', response);
          this.getUserTasks(this.searchName);
        },
        error => {
          console.error('Error updating task status:', error);
        }
      );
    }
  }

  searchBtnClick() {
    this.getUserTasks(this.searchName)
  }
}
