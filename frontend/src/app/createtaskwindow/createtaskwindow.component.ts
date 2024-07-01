import {Component, OnInit} from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, throwError} from 'rxjs';
import { v4 as uuidv4 } from 'uuid';
import { TaskService } from '../task.service';

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
  isValidTitle: boolean = true;
  isValidDeadline: boolean = true;

  serverStatus: string = '';
  constructor(public bsModalRef: BsModalRef, private http: HttpClient,
              private taskService: TaskService) {

  }
  ngOnInit() {

  }


  close() {
    this.bsModalRef.hide();
  }
  checkTaskTitleValid() {
    if (this.taskTitle.length <= 1) {
      this.isValidTitle = false;
    } else {
      this.isValidTitle = true;
    }
  }
  checkDeadlineValid(event: any) {
    this.taskDeadline = event.target.value;
    if (this.taskDeadline.length > 0) {
      this.isValidDeadline = true;
    } else {
      this.isValidDeadline = false;
    }
  }
  createClick(): void {
    if (this.taskTitle == '' || this.taskDescription == '' || this.taskDeadline == '') {
      this.isValidTitle = false
      this.isValidDeadline = false
      alert('Please enter a valid data');
    }
    else {
      let taskEntity = {
        'id': this.getUuid(),
        'title': this.taskTitle,
        'description': this.taskDescription,
        'untilDate': this.convertToISO(this.taskDeadline),
        'status': 'todo',
      }
      this.getTasks();
    }
  }

  createTask(taskData: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, taskData, { observe: 'response' })
      .pipe(
        catchError(this.handleError)
      );
  }

  responseStatus: number | null = null;
  errorMessage: string | null = null;
  tasks: any[] = [];

  getTasks() {
    this.taskService.getTasks().subscribe(
      response => {
        this.tasks = response.body;
        this.responseStatus = response.status;
        this.errorMessage = null;
      },
      error => {
        this.responseStatus = null;
        this.errorMessage = error;
      }
    );
  }

  getUuid() : string {
    return uuidv4();
  }

  convertToISO = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toISOString();
  };

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }


}
