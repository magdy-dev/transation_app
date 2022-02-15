package com.app.employeemanager.service;

import com.app.employeemanager.entity.LoginPassword;
import com.app.employeemanager.repository.LoginPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginPasswordService {

    private final LoginPasswordRepository loginPasswordRepository;

    public LoginPassword add(String login, String password){
        LoginPassword loginPassword = new LoginPassword();
        loginPassword.setLogin(login);
        loginPassword.setPassword(password);
        loginPasswordRepository.save(loginPassword);
        return loginPasswordRepository.findByLoginAndPassword(login, password).get();
    }

    public LoginPassword check(String login, String password){

        return loginPasswordRepository.findByLoginAndPassword(login, password).get();
    }

    public boolean checkLoginPassword(String login, String password){
        return loginPasswordRepository.findByLoginAndPassword(login, password).isPresent();
    }

}
