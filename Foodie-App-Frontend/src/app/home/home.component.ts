import { Component, OnInit, inject } from '@angular/core';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { map } from 'rxjs/operators';
import { Restaurant } from '../models/restaurant';
import { RestaurantService } from '../services/restaurant.service';
import { Food } from '../models/food';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  restaurants: Restaurant[] = [];


  constructor(private restaurantService: RestaurantService) { }

  ngOnInit(): void {
    this.loadRestaurants();
  }

  searchTitle($event: string) {
    this.restaurantService.getAllRestaurants().subscribe(
      data => {
        this.restaurants = data.filter((restaurant) =>
          restaurant.name?.toLowerCase().startsWith($event.toLowerCase())
        );
      });
  }

  loadRestaurants(): void {
    this.restaurantService.getAllRestaurants().subscribe(
      data => {
        this.restaurants = data;
      },
      error => {
        if (error.status === 302) {
          const redirectUrl = error.headers.get('Location');
          if (redirectUrl) {
            window.location.href = redirectUrl;
          }
        } else {
          console.error('Error loading restaurants', error);
        }
      }
    );
  }

}
