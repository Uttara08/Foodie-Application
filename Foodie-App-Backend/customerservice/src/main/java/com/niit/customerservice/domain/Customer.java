package com.niit.customerservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Customer {
    @Id
    private String emailId;
    @Transient
    private String password;
    private String name;
    private String address;

    private String phNo;
    private String role;

    private String favItem;
    private List<Restaurant> RestaurantList;
    private List<Food> favList;

    public Customer(String emailId, String password, String name, String address, String phNo, String favItem, List<Restaurant> restaurantList, List<Food> favList) {
        this.emailId = emailId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phNo = phNo;
        this.role="Customer";
        this.favList = favList;

    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Restaurant> getRestaurantList() {
        return RestaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        RestaurantList = restaurantList;
    }



    public List<Food> getFavList() {
        return favList;
    }

    public void setFavList(List<Food> favList) {
        this.favList = favList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public Customer() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavItem() {
        return favItem;
    }

    public void setFavItem(String favItem) {
        this.favItem = favItem;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phNo='" + phNo + '\'' +
                ", role='" + role + '\'' +
                ", favItem='" + favItem + '\'' +
                ", RestaurantList=" + RestaurantList +
                ", favList=" + favList +
                '}';
    }
}
