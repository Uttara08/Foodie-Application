import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestaurantService } from '../services/restaurant.service';
import { Router } from '@angular/router';
import { CanComponentDeactivate } from '../services/deactivate.guard';
import { Observable } from 'rxjs';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-restaurant',
  templateUrl: './add-restaurant.component.html',
  styleUrl: './add-restaurant.component.css'
})
export class AddRestaurantComponent implements CanComponentDeactivate {
  userForm!: FormGroup;
  selectedImage: string | ArrayBuffer | null | undefined;

  constructor(private fb: FormBuilder, private snackBar: MatSnackBar, private http: HttpClient, private rs: RestaurantService, private router: Router) { }

  ngOnInit() {
    this.userForm = this.fb.group({
      emailId: ['', [Validators.required, Validators.email, this.emailValidator]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['Admin'],
      name: ['', Validators.required],
      address: ['', Validators.required],
      phNo: ['', [Validators.required, Validators.pattern(/^[7-9]\d{9}$/), this.phoneNumberValidator]],
    });
  }

  onSubmit() {
    this.rs.addRestaurant(this.userForm.value).subscribe(
      (data) => {
        this.router.navigateByUrl('home');
        this.showSnackbar('Restaurant added successfully', 3000);
      },
      (error) => {
        if (error && error.error && error.error.error === "Restaurant already exists") {
          this.showSnackbar('Restaurant with the same email ID already exists. Please use a different email ID', 3000);
        } else {
          console.error("Error:", error);
          this.showSnackbar('Failed to add Restaurant. Please try again later', 3000);
        }
      }
    );
  }

  emailValidator(control: AbstractControl) {
    if (control.value && control.value.length < 13) {
      return { 'invalidEmail': true };
    }
    return null;
  }
  phoneNumberValidator(control: AbstractControl) {
    if (control.value && !/^[7-9]\d{9}$/.test(control.value)) {
      return { 'firstDigit': true };
    }
    return null;
  }

  showSnackbar(message: string, duration: number, verticalPosition: 'top' | 'bottom' = 'top') {
    const config: MatSnackBarConfig = {
      duration,
      verticalPosition,
    };
    this.snackBar.open(message, 'Close', config);
  }

  canClose() {
    if (this.userForm.dirty && this.userForm.invalid) {
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

 







