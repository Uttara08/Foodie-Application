import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomerService } from '../services/customer.service';
import { Router } from '@angular/router';
import { CanComponentDeactivate } from '../services/deactivate.guard';
import { Observable } from 'rxjs';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements CanComponentDeactivate {
  userForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private snackBar: MatSnackBar, private http: HttpClient, private customerService: CustomerService, private router: Router) { }


  ngOnInit() {
    this.userForm = this.formBuilder.group({
      emailId: ['', [Validators.required, Validators.email, this.emailValidator]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['Customer'],
      name: ['', Validators.required],
      address: ['', Validators.required],
      phNo: ['', [Validators.required, Validators.pattern(/^[7-9]\d{9}$/), this.phoneNumberValidator]],
    });
  }



  onSubmit() {
    this.customerService.registerCustomer(this.userForm.value).subscribe(
      (data) => {
        this.router.navigateByUrl("home");
        this.showSnackbar('Customer added successfully', 3000);
      },
      (error) => {
        if (error && error.error && error.error.error === "customer already exists") {
          this.showSnackbar('customer with the same email ID already exists. Please use a different email ID', 3000);

        } else {
          console.error("Error:", error);
          this.showSnackbar('Failed to add customer. Please try again later', 3000);
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
