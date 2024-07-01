import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private baseUrl = 'http://localhost:8080/tasks'; // Замените на ваш URL

  constructor(private http: HttpClient) {}

  createTask(taskData: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, taskData, { observe: 'response' })
      .pipe(
        catchError(this.handleError)
      );
  }

  getTasks(): Observable<any> {
    return this.http.get<any>(this.baseUrl, { observe: 'response' })
      .pipe(
        catchError(this.handleError)
      );
  }

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
