import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerService } from '../services/customer.service';
import { favorites } from '../models/fav';

@Component({
  selector: 'app-view-fav',
  templateUrl: './view-fav.component.html',
  styleUrl: './view-fav.component.css'
})
export class ViewFavComponent {
  favorites: favorites[] = [];

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {

    this.customerService.viewFavoriteList().subscribe(
      (data) => {
        this.favorites = data;
      },
      (error) => {
        console.error('Error fetching favorites:', error);
      }
    );
  }



}
