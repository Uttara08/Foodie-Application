import { Component, OnInit } from '@angular/core';
import { Restaurant } from '../models/restaurant';
import { RestaurantService } from '../services/restaurant.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { FoodService } from '../services/food.service';
import { CustomerService } from '../services/customer.service';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'app-display-food',
  templateUrl: './display-food.component.html',
  styleUrls: ['./display-food.component.css'] // Corrected from styleUrl to styleUrls
})
export class DisplayFoodComponent implements OnInit {

  displayRestaurant: Restaurant = {
    name: '',
    address: '',
    foodList: []
  };

  constructor(
    private restaurantService: RestaurantService,
    private activatedRoute: ActivatedRoute, 
    private router: Router,
    public loginService: LoginService, 
    private foodService: FoodService,
    private customerService: CustomerService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    const deleteData = sessionStorage.getItem("delete");
    const itemName = sessionStorage.getItem('itemName') ?? "";
    const token = sessionStorage.getItem('token') ?? "";
    const role = sessionStorage.getItem("role");

    if (role === "Admin" && deleteData === "true" && itemName && token) {
      if (confirm("Are you sure you want to delete this food?")) {
        this.foodService.deleteFood(itemName, token).subscribe(
          response => {
            console.log('Food deleted successfully:', response);
            sessionStorage.removeItem("itemName");
            sessionStorage.removeItem("delete");
            this.getOneRestaurantdetails();
          },
          error => {
            console.error('Error deleting food:', error);
            sessionStorage.removeItem("itemName");
            sessionStorage.removeItem("delete");
            this.getOneRestaurantdetails();
          }
        );
      }
    } else {
      this.getOneRestaurantdetails();
    }

    this.activatedRoute.paramMap.subscribe(params => {
      const restId = params.get('name') ?? 0;
      if (restId) {
        this.getRestaurantdetails(restId);
      } else {
        console.error('Restaurant ID not provided in the route parameters.');
      }
    });
  }

  getRestaurantdetails(restId: any) {
    this.foodService.getAllFoods(restId).subscribe((data) => {
      this.displayRestaurant.foodList = data;
    });
  }

  getOneRestaurantdetails() {
    this.restaurantService.getRestaurantById().subscribe(
      (data) => {
        this.displayRestaurant = data;
      },
      (error) => {
        console.error('Error fetching restaurant details:', error);
      }
    );
  }

  deleteFood(itemName: any): void {
    sessionStorage.setItem("delete", "true");
    sessionStorage.setItem("itemName", itemName);
    this.router.navigateByUrl("deleteItem");
  }

  addToFavorites(foodName: any): void {
    const token = sessionStorage.getItem('token');
    if (!token) {
      console.error('Token is missing');
      return;
    }
    this.customerService.addToFavorites(foodName, token).subscribe(
      response => {
        console.log('Food item added to view Cart', response);

        this.showSnackbar('Food item added to view Cart', 3000);
      },
      error => {
        console.error('Error adding to view cart:', error);
        this.showSnackbar('Failed to add to view cart. Please try again later', 3000);
      }
    );
  }

  showSnackbar(message: string, duration: number, verticalPosition: 'top' | 'bottom' = 'top') {
    const config: MatSnackBarConfig = {
      duration,
      verticalPosition,
    };
    this.snackBar.open(message, 'Close', config);
  }

}