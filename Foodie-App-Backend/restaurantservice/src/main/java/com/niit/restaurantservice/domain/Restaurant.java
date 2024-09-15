package com.niit.restaurantservice.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Restaurant {
  @MongoId
    private String emailId;
  @Transient
    private String password;

    private String name;
    private String phNo;

    private String address;
    private String role;
    private List<Food> foodList;

    public Restaurant(String emailId, String password, String name, String phNo, String address, List<Food> foodList) {
        this.emailId = emailId;
        this.password = password;

        this.name = name;
        this.phNo = phNo;
        this.address = address;
        this.role = "Admin";
        this.foodList = foodList;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Restaurant() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +

                ", name='" + name + '\'' +
                ", phNo='" + phNo + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", foodList=" + foodList +
                '}';
    }


}
