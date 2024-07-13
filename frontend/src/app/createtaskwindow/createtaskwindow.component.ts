import {Component, OnInit} from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, throwError} from 'rxjs';
import { v4 as uuidv4 } from 'uuid';
import { TaskService } from '../task.service';
import {response} from "express";

@Component({
  selector: 'app-createtaskwindow',
  templateUrl: './createtaskwindow.component.html',
  styleUrl: './createtaskwindow.component.css'
})
export class CreatetaskwindowComponent implements OnInit {
  baseUrl: string = 'http://localhost:8080/tasks';

  taskTitle: string = '';
  taskDescription: string = '';
  taskDeadline: string = '';

  serverStatus: string = '';
  constructor(public bsModalRef: BsModalRef, private http: HttpClient,
              private taskService: TaskService) {

  }
  ngOnInit() {

  }


  close() {
    this.bsModalRef.hide();
  }

  createClick(): void {
    let taskEntity = {
      'title': this.taskTitle,
      'description': this.taskDescription,
      'untilDate': this.convertToISO(this.taskDeadline),
    }
    this.taskService.createTask(taskEntity).subscribe(
      response => {
        this.close();
      },
      (error) => {
        if (error.status == 400) alert("Error, invalid data provided!");
        else if (error.status == 401) alert("Error, request provided without token!");
      }
    )

  }

  convertToISO = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toISOString();
  };



}
