import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from '../models/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }
  apiUrl = 'http:////localhost:8090/api/v3/customer/addFavorite';
  registerCustomer(customer: Customer): Observable<any> {
    return this.http.post<any>("http://localhost:8090/api/v3/register", customer);
  }

  addToFavorites(foodName: string, token: string): Observable<any> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.post<any>(`${this.apiUrl}`, foodName, { headers })
  }
  private token: string | null = null;

  getToken(): string | null {
    return this.token;
  }

  viewFavoriteList(): Observable<any> {

    this.token = sessionStorage.getItem('token');
    const token = this.getToken();
    if (!token) {
      throw new Error('Token is missing.');
    }

    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);

    return this.http.get("http://localhost:8090/api/v3/customer/viewFavorite", { headers });
  }
}
