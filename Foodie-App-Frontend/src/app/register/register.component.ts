import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators, ValidationErrors, ValidatorFn } from '@angular/forms';
import { CustomerService } from '../services/customer.service';
import { Router } from '@angular/router';
import { CanComponentDeactivate } from '../services/deactivate.guard';
import { Observable } from 'rxjs';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements CanComponentDeactivate {
  userForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private http: HttpClient,
    private customerService: CustomerService,
    private router: Router
  ) {}

  ngOnInit() {
    this.userForm = this.formBuilder.group({
      name: ['', [Validators.required, this.noStartingSpaceValidator(), this.alphabetAndSpaceValidator()]],
      emailId: ['', [Validators.required, Validators.email, this.customEmailValidator]],
      password: ['', [Validators.required, Validators.minLength(6), this.passwordValidator]],
      address: ['', Validators.required],
      phNo: ['', [Validators.required, Validators.pattern(/^[7-9]\d{9}$/), this.phoneNumberValidator]],
      role: ['Customer']
    });
  }

  // Validator to ensure name does not start with a space
  noStartingSpaceValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (value && value.trimStart().length !== value.length) {
        return { noStartingSpace: true };
      }
      return null;
    };
  }

  // Validator to ensure the name contains only alphabetic characters and spaces
  alphabetAndSpaceValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (value && !/^[A-Za-z\s]+$/.test(value)) {
        return { alphabetAndSpaceOnly: true };
      }
      return null;
    };
  }

  // Custom validator to ensure email starts with alphabetic characters
  customEmailValidator(control: AbstractControl): ValidationErrors | null {
    const email = control.value;
    if (email && email.indexOf('@') > 0) {
      const localPart = email.split('@')[0];
      const valid = /^[A-Za-z]+/.test(localPart);
      if (!valid) {
        return { invalidEmail: true };
      }
    }
    return null;
  }

  // Validator for phone number starting with 7-9
  phoneNumberValidator(control: AbstractControl): ValidationErrors | null {
    if (control.value && !/^[7-9]\d{9}$/.test(control.value)) {
      return { firstDigit: true };
    }
    return null;
  }

  // Validator for password complexity
  passwordValidator(control: AbstractControl): ValidationErrors | null {
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

  onSubmit() {
    this.customerService.registerCustomer(this.userForm.value).subscribe(
      (data) => {
        this.router.navigateByUrl("home");
        this.showSnackbar('Customer added successfully', 3000);
      },
      (error) => {
        if (error && error.error && error.error.error === "customer already exists") {
          this.showSnackbar('Customer with the same email ID already exists. Please use a different email ID.', 3000);
        } else {
          console.error("Error:", error);
          this.showSnackbar('Failed to add customer. Please try again later.', 3000);
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

  canClose() {
    if (this.userForm.dirty && this.userForm.invalid) {
      let response = confirm('Changes you made may not be saved. Are you sure you want to leave?');
      return response;
    }
    return true;
  }

  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    return this.canClose();
  }
}
