import { Injectable } from '@angular/core';
import { Restaurant } from '../models/restaurant';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {
 
  private token: string | null = null;

  constructor(private http: HttpClient) {
  }

  addRestaurant(restaurant: Restaurant): Observable<HttpResponse<any>> {
    this.token = sessionStorage.getItem('token');
    const headers = { 'Authorization': `Bearer ${this.token}` };
    return this.http.post<any>("http://localhost:8090/api/v2/addRestaurant", restaurant, { headers, observe: 'response' });
  }

  getAllRestaurants() {
    return this.http.get<Restaurant[]>("http://localhost:8090/api/v2/restaurants");
  }

  getRestaurantById(): Observable<Restaurant> {
    this.token = sessionStorage.getItem('token');
    const headers = { 'Authorization': `Bearer ${this.token}` };
    return this.http.get<Restaurant>("http://localhost:8090/api/v2/restaurant/ViewRestaurant", { headers });
  }


}
