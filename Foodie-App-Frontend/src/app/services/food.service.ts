import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of } from 'rxjs';
import { Food } from '../models/food';

@Injectable({
  providedIn: 'root'
})
export class FoodService {
  private apiUrl = 'http:////localhost:8090/api/v2/restaurant/addFood';
  private getallUrl = 'http://localhost:8090/api/v2/restaurant/viewOneFood';
  private deleteFoodApiUrl = 'http:////localhost:8090/api/v2/restaurant/deleteFood'

  constructor(private http: HttpClient) { }

  addFoodToRestaurant(foodData: Food, tokens: string): Observable<any> {
    if (!foodData.itemName) {
      console.error('Item name cannot be null');
      return of({ error: 'Item name cannot be null' });
    }
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.post<any>(this.apiUrl, foodData, { headers })
  }

  getAllFoods(name: any) {
    return this.http.get<Food[]>("http://localhost:8090/api/v2/viewFood/" + name);
  }

  editFoodInRestaurant(foodData: Food, token: string): Observable<any> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.put<any>("http://localhost:8090/api/v2/restaurant/updateFood", foodData, { headers });
  }


  getFoodDetailsByName(foodName: string, token: string): Observable<Food> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<Food>(`${this.getallUrl}/${foodName}`, { headers });
  }

  deleteFood(itemName: string, token: string): Observable<any> {
    if (!token) {
      throw new Error('Token is required for deleting food');
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<any>(`${this.deleteFoodApiUrl}/${itemName}`, { headers });
  }

}

