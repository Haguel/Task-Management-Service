import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth'; // Укажите базовый URL вашего API

  constructor(private http: HttpClient) {}

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
    return this.http.post<any>(`${this.apiUrl}/sign-in`, data)
  }
}
