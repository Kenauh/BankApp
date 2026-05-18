package com.example.bankapp;

class LoginRequest {
    String accountNumber;
    String password;

    LoginRequest(String accountNumber, String password) {
        this.accountNumber = accountNumber;
        this.password = password;
    }
}

class LoginResponse {
    boolean success;
    String message;
    UsuarioResponse user;
}

class UsuarioResponse {
    String accountNumber;
    String name;
    double currentBalance;
    double creditBalance;
    double creditLimit;
}

class SaldoResponse {
    String accountNumber;
    double currentBalance;
    double creditBalance;
    double creditLimit;
    double availableCredit;
}

class ContactoResponse {
    String name;
    String bank;
    String type;
    String destinationAccount;
}

class TransferRequest {
    String fromAccount;
    String toAccount;
    double amount;

    TransferRequest(String fromAccount, String toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }
}

class TransferResponse {
    boolean success;
    String message;
}
