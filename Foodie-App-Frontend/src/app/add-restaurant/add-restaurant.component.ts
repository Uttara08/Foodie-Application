import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder,ValidatorFn, FormGroup, Validators ,ValidationErrors} from '@angular/forms';
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
      name: ['', [Validators.required, this.noStartingSpaceValidator(), this.alphabetAndSpaceValidator()]],
      emailId: ['', [Validators.required, Validators.email, this.customEmailValidator]],
      password: ['', [Validators.required, Validators.minLength(6), this.passwordValidator]],
      role: ['Admin'],
      
      address: ['', Validators.required],
      phNo: ['', [Validators.required, Validators.pattern(/^[7-9]\d{9}$/), this.phoneNumberValidator]],
    });
  }
  noStartingSpaceValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (value && value.trimStart().length !== value.length) {
        return { noStartingSpace: true };
      }
      return null;
    };
  }
  alphabetAndSpaceValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (value && !/^[A-Za-z\s]+$/.test(value)) {
        return { alphabetAndSpaceOnly: true };
      }
      return null;
    };
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
  customEmailValidator(control: AbstractControl): ValidationErrors | null {
    const email = control.value;
    if (email && email.indexOf('@') > 0) {
      const localPart = email.split('@')[0];
      const valid = /^[A-Za-z]+/.test(localPart);  // Check if it starts with alphabetic characters
      if (!valid) {
        return { invalidEmail: true };
      }
    }
    return null;
  }

  alphabeticalValidator(control: AbstractControl): ValidationErrors | null {
    const valid = /^[A-Za-z]+$/.test(control.value);
    return valid ? null : { alphabetOnly: true };
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
  }passwordValidator(control: AbstractControl): ValidationErrors | null {
    const value = control.value;

    if (!value) {
      return null;
    }

    const errors: ValidationErrors = {};

    if (/\s/.test(value)) {
      errors['noSpaces'] = true;
    }

    if (value.length < 6) {
      errors['minLength'] = true;
    }

    if (!/[A-Z]/.test(value)) {
      errors['uppercase'] = true;
    }

    if (!/[a-z]/.test(value)) {
      errors['lowercase'] = true;
    }

    if (!/\d/.test(value)) {
      errors['number'] = true;
    }

    if (!/[!@#$%^&*(),.?":{}|<>]/.test(value)) {
      errors['specialChar'] = true;
    }

    return Object.keys(errors).length ? errors : null;
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

 







