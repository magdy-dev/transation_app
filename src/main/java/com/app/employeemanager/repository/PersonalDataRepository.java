package com.app.employeemanager.repository;

import com.app.employeemanager.entity.LoginPassword;
import com.app.employeemanager.entity.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

    public Optional<PersonalData> findByLoginPassword (LoginPassword loginPassword);
}
