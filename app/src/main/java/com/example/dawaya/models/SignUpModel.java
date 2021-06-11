package com.example.dawaya.models;

import java.sql.Date;

public class SignUpModel {
    String userId;
    String firstName;
    String lastName;
    String email;
    String password;
    String confirmPassword;
    String phoneNumber;
    String gender;

    //Sql Date
    String dateOfBirth;

    public SignUpModel() {
        this.userId = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.phoneNumber = "";
        this.gender = "";
        this.dateOfBirth = "";
    }

    public SignUpModel(String userId, String firstName, String lastName, String email, String password, String phoneNumber, String gender, String dateOfBirth) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserId() {
        return userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getGender() {
        return gender;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }




    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
