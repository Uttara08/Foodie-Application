import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Food } from '../models/food';
import { FoodService } from '../services/food.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CanComponentDeactivate } from '../services/deactivate.guard';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'app-addfood',
  templateUrl: './addfood.component.html',
  styleUrls: ['./addfood.component.css']
})
export class AddfoodComponent implements OnInit, CanComponentDeactivate {

  food: Food = {
    itemName: '',
    foodDescription: '',
    price: 0
  };
  foodForm!: FormGroup;
  isEditing: boolean = false;
  token: string | null = '';

  constructor(private fb: FormBuilder,
    private foodService: FoodService,
    private router: Router,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.token = sessionStorage.getItem('token');

    if (!this.token) {
      console.error('Session token is missing or expired');
      return;
    }

    this.foodForm = this.fb.group({
      itemName: ['', Validators.required],
      foodDescription: ['', Validators.required],
      price: ['', Validators.required]
    });

    this.route.params.subscribe(params => {
      const foodName = params['name'];
      if (foodName) {
        this.isEditing = true;
        this.getFoodDetailsByName(foodName);
      }
    });
  }

  getFoodDetailsByName(foodName: string): void {
    this.foodService.getFoodDetailsByName(foodName, this.token!).subscribe(
      response => {
        this.food = response;
        this.foodForm.patchValue({
          itemName: response.itemName,
          foodDescription: response.foodDescription,
          price: response.price
        });
      },
      error => {
        console.error('Error retrieving food details by name:', error);
      }
    );
  }

  onSubmit(): void {
    if (this.foodForm.invalid) {
      return;
    }

    if (!this.token) {
      console.error('Session token is missing or expired');
      return;
    }

    const foodData = this.foodForm.value;
    if (this.isEditing) {
      this.foodService.editFoodInRestaurant(foodData, this.token!).subscribe(
        response => {
          console.log('Food edited successfully:', response);
          this.showSnackbar('Food edited successfully!', 3000);

          this.router.navigateByUrl('viewOneRestaurant');
        },
        error => {
          console.error('Error editing food:', error);
          this.showSnackbar('Failed to edit food. Please try again later', 3000);

        }
      );
    } else {
      this.foodService.addFoodToRestaurant(foodData, this.token!).subscribe(
        response => {
          console.log('Food added successfully:', response);
          this.showSnackbar('Food added successfully!', 3000);

          this.router.navigateByUrl('viewOneRestaurant');
        },
        error => {
          console.error('Error adding food:', error);
          this.showSnackbar('Failed to add food. Please try again later', 3000);
        }
      );
    }
  }
  showSnackbar(message: string, duration: number, verticalPosition: 'top' | 'bottom' = 'top') {
    const config: MatSnackBarConfig = {
      duration,
      verticalPosition,
    };

    this.snackBar.open(message, 'Close', config);
  }

  canClose() {
    if (this.foodForm.dirty && this.foodForm.invalid) {
      let response = confirm('Changes you made may not be saved. Are you sure you want to leave?');
      return response;
    }
    else {
      return true;
    }
  }

  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    return this.canClose();
  }
}
