import { Injectable } from '@angular/core';
import { Restaurant } from '../models/restaurant';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { Customer } from '../models/customer';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  private token: string | null = null;
  private role: string | null = null;

  constructor(private http: HttpClient, private router: Router) {
    this.token = sessionStorage.getItem('token');
    this.role = sessionStorage.getItem('role');
  }

  isLoggedIn: boolean = false;
  isAdmin: boolean = false;
  isCustomer: boolean = false;

  logout(): void {
    this.token = null;
    this.role = null;
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('role');
  }
  login(restaurant: Restaurant): Observable<any> {
    return this.http.post<any>("http://localhost:8090/api/v1/login", restaurant);
  }

}
