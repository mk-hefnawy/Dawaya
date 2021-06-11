package com.example.dawaya.responses;

public class SignUpResponse {

    public String success;
    public String alreadyHasAnAccount;

    public SignUpResponse(String success, String alreadyHasAnAccount) {
        this.success = success;
        this.alreadyHasAnAccount = alreadyHasAnAccount;
    }
}
