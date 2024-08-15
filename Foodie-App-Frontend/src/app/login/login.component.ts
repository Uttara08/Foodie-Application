import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { RestaurantService } from '../services/restaurant.service';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private fb: FormBuilder, private snackBar: MatSnackBar, private ls: LoginService, private router: Router, private rs: RestaurantService) { }

  userForm!: FormGroup;

  ngOnInit() {
    this.userForm = this.fb.group({
      emailId: [''],
      password: [''],

    });
  }

  onSubmit() {
    this.ls.login(this.userForm.value).subscribe(
      (response) => {
        const token = response.token;
        const role = response.role;
        if (token) {
          sessionStorage.setItem('token', token);
          sessionStorage.setItem('role', role);
          this.ls.isLoggedIn = true;

          if (role == "Admin") {
            this.ls.isAdmin = true;
            this.router.navigateByUrl("viewOneRestaurant")
          }
          else {
            this.ls.isCustomer = true;
            this.router.navigateByUrl('home');
          }
          this.showSnackbar('Login successful!!', 3000);


        }
        else {
          console.error("Token not found in the response body:", response);
        }
      },
      (error) => {
        if (error && error.error && error.error.error === "Customer already exists") {
          this.showSnackbar(
            'Customer with the same email ID already exists. Please use a different email ID.',
            5000
          );
        }
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
