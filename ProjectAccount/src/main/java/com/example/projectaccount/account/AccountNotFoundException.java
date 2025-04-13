package com.example.projectaccount.account;

public class AccountNotFoundException extends Throwable {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
