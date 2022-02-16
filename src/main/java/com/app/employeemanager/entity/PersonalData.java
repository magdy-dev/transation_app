package com.app.employeemanager.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date registrationDate;
    private Date lastEntryDate;
    private Boolean status;
    @OneToOne
    private LoginPassword loginPassword;
//
//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<Role> roles;


    @Override
    public String toString() {

        return "<tr>" +
                "<td><input name='checkbox' type='checkbox' value='" + id + "'></td>" +
                "<td>" + id + "</td>" +
                "<td>" + firstName + "</td>" +
                "<td>" + lastName + "</td>" +
                "<td>" + email + "</td>" +
                "<td>" + registrationDate + "</td>" +
                "<td>" + lastEntryDate + "</td>" +
                "<td>" + status + "</td>" +
                "</tr>";
    }
}
