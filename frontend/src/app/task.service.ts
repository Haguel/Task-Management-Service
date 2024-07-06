import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable, tap, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';
import * as http from "node:http";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private baseUrl = 'http://localhost:8080/tasks';

  constructor(private http: HttpClient) {}

  private toDoListSource = new BehaviorSubject<any[]>([]);
  private doingListSource = new BehaviorSubject<any[]>([]);
  private finishedListSource = new BehaviorSubject<any[]>([]);
  private expiredListSource = new BehaviorSubject<any[]>([]);

  toDoList$ = this.toDoListSource.asObservable();
  doingList$ = this.doingListSource.asObservable();
  finishedList$ = this.finishedListSource.asObservable();
  expiredList$ = this.expiredListSource.asObservable();

  updateToDoList(tasks: any[]) {
    this.toDoListSource.next(tasks);
  }

  updateDoingList(tasks: any[]) {
    this.doingListSource.next(tasks);
  }

  updateFinishedList(tasks: any[]) {
    this.finishedListSource.next(tasks);
  }

  updateExpiredList(tasks: any[]) {
    this.expiredListSource.next(tasks);
  }

  createTask(taskData: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.post<any>(this.baseUrl, taskData, { headers })
      .pipe(
        tap((newTask) => {
          const currentTasks = this.toDoListSource.getValue();
          this.toDoListSource.next([...currentTasks, newTask]);
        }),
        catchError(this.handleError)
      );
  }

  getTasks(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    console.log(headers);
    return this.http.get<any>(this.baseUrl, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  updateTaskStatus(taskData: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders()
      .set('Authorization', `${token}`)
      .set('Content-Type', 'application/json');

    console.log(headers);
    return this.http.put<any>(this.baseUrl, taskData, { headers });
  }

  deleteTask(taskId: any) {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.delete<any>(`${this.baseUrl}/${taskId}`, { headers });
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }
}
