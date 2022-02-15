package com.app.employeemanager.service;

import com.app.employeemanager.entity.LoginPassword;
import com.app.employeemanager.entity.PersonalData;
import com.app.employeemanager.repository.LoginPasswordRepository;
import com.app.employeemanager.repository.PersonalDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalDataService {

    private final PersonalDataRepository personalDataRepository;
    private final LoginPasswordRepository loginPasswordRepository;

    public void add(String firstName, String lastName, String email, LoginPassword loginPassword){
        PersonalData personalData = new PersonalData();
        personalData.setFirstName(firstName);
        personalData.setLastName(lastName);
        personalData.setEmail(email);
        personalData.setLoginPassword(loginPassword);
        Date date = new Date();
        personalData.setRegistrationDate(date);
        personalData.setStatus(true);
        personalDataRepository.save(personalData);
    }

    public Boolean getStatus(LoginPassword loginPassword){
        return personalDataRepository.findByLoginPassword(loginPassword).get().getStatus();
    }

    public Long getUser(LoginPassword loginPassword){
        return personalDataRepository.findByLoginPassword(loginPassword).get().getId();
    }

    public Boolean check(Long id){
        Boolean check = false;
        if (personalDataRepository.findById(id).isPresent() && personalDataRepository.findById(id).get().getStatus()){
            check = true;
        }
        return check;
    }

    public void update(LoginPassword loginPassword){
        Date date = new Date();
        PersonalData personalData = personalDataRepository.findByLoginPassword(loginPassword).get();
        personalData.setLastEntryDate(date);
        personalDataRepository.save(personalData);

    }

    public void block(String [] listId){

        for (String id: listId) {
            PersonalData personalData = personalDataRepository.findById(Long.parseLong(id)).get();
            personalData.setStatus(false);
            personalDataRepository.save(personalData);
        }
    }

    public void unblock(String [] listId){
        for (String id: listId) {
            PersonalData personalData = personalDataRepository.findById(Long.parseLong(id)).get();
            personalData.setStatus(true);
            personalDataRepository.save(personalData);
        }
    }

    public void delete(String [] listId){
        for (String id: listId) {
            if (personalDataRepository.findById(Long.parseLong(id)).isPresent()) {
                loginPasswordRepository.deleteById(personalDataRepository.findById(Long.parseLong(id)).get().getLoginPassword().getId());
                personalDataRepository.deleteById(Long.parseLong(id));
            }
        }
    }
    
    public List<PersonalData> getAll(){
        return personalDataRepository.findAll();
    }
}
