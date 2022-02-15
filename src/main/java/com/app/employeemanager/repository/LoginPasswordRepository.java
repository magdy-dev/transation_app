package com.app.employeemanager.repository;

import com.app.employeemanager.entity.LoginPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginPasswordRepository extends JpaRepository<LoginPassword, Long> {

    public Optional<LoginPassword> findByLoginAndPassword(String login, String password);
}
