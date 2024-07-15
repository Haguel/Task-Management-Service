import {Component, OnInit} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap/modal";
import {TaskService} from "../task.service";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-redacttaskwindow',
  templateUrl: './redacttaskwindow.component.html',
  styleUrl: './redacttaskwindow.component.css'
})
export class RedacttaskwindowComponent implements OnInit {
  currTask: any;
  taskTitle: string = '';
  taskDescription: string = '';
  taskDeadline: string = '';

  constructor(public bsModalRef: BsModalRef, private taskService: TaskService) {
  }

  ngOnInit(): void {
    let taskString = localStorage.getItem('currentTaskForRedact');

    if (taskString !== null) {
      this.currTask = JSON.parse(taskString);
    }
    this.taskTitle = this.currTask.title;
    this.taskDescription = this.currTask.description;
    this.taskDeadline = this.currTask.untilDate;

  }

  delete() {
    console.log(this.currTask)
    let obj = {'taskId': this.currTask.id};
    console.log(obj)
    this.taskService.deleteTask(obj).subscribe(
      response => {
        console.log(response);
      },
      error => {
        console.log(error);
      }
    )
  }

  save() {
    const redactTask = {
      taskId: this.currTask.id,
      title: this.taskTitle,
      description: this.taskDescription,
      untilDate: this.taskService.convertToISO(this.taskDeadline),
      status: this.currTask.status,
    }
    console.log(redactTask);
    this.taskService.updateTaskStatus(redactTask).subscribe(
      response => {
        this.close();
      },
      error => {
        console.log(error);
      }
    )
  }

  close() {
    this.bsModalRef.hide();
  }
}
