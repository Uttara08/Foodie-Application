import { Component, EventEmitter, Output, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { LoginService } from '../services/login.service';
import { Restaurant } from '../models/restaurant';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  restaurants: Restaurant[] = [];
  private breakpointObserver = inject(BreakpointObserver);
  constructor(public loginService: LoginService, private router: Router) { }
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  searchText: string = '';
  @Output()
  searchedText: EventEmitter<string> = new EventEmitter<string>();

  search() {
    this.searchedText.emit(this.searchText);
    this.searchText = '';
  }

  logout() {
    let confirmvalue = confirm('Are you sure, Do you want to Logout?');
    if (confirmvalue) {
      sessionStorage.removeItem('token');
      sessionStorage.removeItem('role');
      this.loginService.isLoggedIn = false;
      this.loginService.isAdmin = false;
      this.loginService.isCustomer = false;
      this.router.navigateByUrl('/home');
    }
  }

}
