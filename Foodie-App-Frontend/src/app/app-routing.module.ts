import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { SearchComponent } from './search/search.component';
import { AddRestaurantComponent } from './add-restaurant/add-restaurant.component';
import { AddfoodComponent } from './addfood/addfood.component';
import { DisplayFoodComponent } from './display-food/display-food.component';

import { userGuard } from './services/restaurant.guard';
import { DeactivateGuard } from './services/deactivate.guard';
import { ViewFavComponent } from './view-fav/view-fav.component';

const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "home",
    redirectTo: ""
  },
  {
    path: "register",
    component: RegisterComponent,
    canDeactivate: [DeactivateGuard]
  },
  {
    path: "login",
    component: LoginComponent,
    canDeactivate: [DeactivateGuard]
  },
  {
    path: "search",
    component: SearchComponent
  },
  {
    path: "addRestaurant",
    component: AddRestaurantComponent,
    canDeactivate: [DeactivateGuard]
  },
  {
    path: "addFood",
    component: AddfoodComponent,
    canActivate: [userGuard],
    canDeactivate: [DeactivateGuard]
  },
  {
    path: "viewOneRestaurant",
    component: DisplayFoodComponent
  },
  {
    path: "viewOneRestaurant/:name",
    component: DisplayFoodComponent
  },
  {
    path: 'edit/:name',
    canActivate: [userGuard],
    component: AddfoodComponent
  },
  {
    path: "viewFav",
    canActivate: [userGuard],
    component: ViewFavComponent
  },
  {
    path:"deleteItem",
    canActivate: [userGuard],
    component:DisplayFoodComponent
  },
  {
    path: "**",
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
