import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, tap, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';
  private getUserUrl = 'http://localhost:8080/users/';
  private userSubject = new BehaviorSubject<any>(null)
  private authStatusSubject = new BehaviorSubject<boolean>(this.hasToken());
  public authStatus$ = this.authStatusSubject.asObservable();


  constructor(private http: HttpClient) {
    const userJson = localStorage.getItem('currentUser');
    if (userJson) {
      this.userSubject.next(JSON.parse(userJson));
    }
  }

  registerUser(name: string, username: string, email: string, password: string): Observable<any> {
    const data = {
      name: name,
      username: username,
      email: email,
      password: password
    };
    return this.http.post<any>(`${this.apiUrl}/sign-up`, data);
  }

  loginUser(email: string, password: string): Observable<any> {
    const data = {
      email: email,
      password: password
    }
    return this.http.post<any>(`${this.apiUrl}/sign-in`, data);
  }

  getUserInfo(email: string): Observable<any> {
    const token = localStorage.getItem('token'); // Получение токена из localStorage
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `${this.getUserUrl}${email}`;

    return this.http.get<any>(url, { headers })
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.authStatusSubject.next(false); // Уведомляем об изменении состояния аутентификации
  }

  getCurrentUser(): any {
    console.log(this.userSubject.value);
  }
}
