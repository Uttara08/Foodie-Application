import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { LoginService } from './login.service';
import { Observable } from 'rxjs';


@Injectable({ providedIn: 'root' })

export class userGuard implements CanActivate {
  constructor(private loginService: LoginService, private router: Router) { }
  canActivate(): boolean {
    if (this.loginService.isAdmin || this.loginService.isCustomer) {
      return true;
    } else {
      this.router.navigateByUrl('/login');
      return false;
    }
  }
}