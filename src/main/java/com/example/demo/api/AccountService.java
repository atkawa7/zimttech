package com.example.demo.api;


import com.example.demo.dto.CreateAccount;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.SuccessOrFailure;

public interface AccountService {
    SuccessOrFailure createAccount(CreateAccount createAccount);
    SuccessOrFailure createToken(LoginRequest loginRequest);

}
