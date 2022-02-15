package com.app.employeemanager.controller;

import com.app.employeemanager.config.Security;
import com.app.employeemanager.entity.LoginPassword;
import com.app.employeemanager.service.LoginPasswordService;
import com.app.employeemanager.service.PersonalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Map;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final PersonalDataService personalDataService;
    private final LoginPasswordService loginPasswordService;

    @GetMapping
    public ModelAndView home(
            @RequestParam(required = false) Long security,
            Map<String, Object> model){
            if (!(security == null)) {
                Security.setRole(security);
            }
        return new ModelAndView("home", model);
    }

    @GetMapping("/registrationForm")
    public ModelAndView registrationForm(Map<String, Object> model){

        return new ModelAndView("registration", model);
    }

    @PostMapping("/registration")
    public ModelAndView registration(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String login,
            @RequestParam String password,
            Map<String, Object> model
            ){

        if (loginPasswordService.checkLoginPassword(login, password)){
            model.put("some", "Такой логин и пароль уже есть!");
        } else {
            LoginPassword loginPassword = new LoginPassword();
            loginPassword = loginPasswordService.add(login, password);
            personalDataService.add(firstName, lastName, email, loginPassword);
            model.put("some", "Вы успешно зарегистрировались");
        }
        return new ModelAndView("success", model);
    }

    @PostMapping("/check")
    public ModelAndView check(
            @RequestParam String login,
            @RequestParam String password,
            Map<String, Object> model
    ){
        String line = "";
        boolean flag = loginPasswordService.checkLoginPassword(login, password);
        boolean status = false;
        if (flag) {
            status = personalDataService.getStatus(loginPasswordService.check(login, password));
        } else {
            line = "notSuccess";
        }
        if (flag && status){
            line = "authorizationSuccess";
            personalDataService.update(loginPasswordService.check(login, password));
            Security.setRole(personalDataService.getUser(loginPasswordService.check(login, password)));
        } else if (flag && !status) {
            line = "notSuccess2";
        }

        return new ModelAndView(line, model);
    }

    @GetMapping("/table")
    public ModelAndView table(
            @RequestParam(defaultValue = "delete") String delete,
            @RequestParam(defaultValue = "unblock") String unblock,
            @RequestParam(defaultValue = "block") String block,
            Map<String, Object> model){
        String line = "failure";
        if (personalDataService.check(Security.getRole())) {
            line = "table";
            if (!delete.equals("delete")) {
                String[] listId = delete.split(",");
                personalDataService.delete(listId);
            }
            if (!unblock.equals("unblock")) {
                String[] listId = unblock.split(",");
                personalDataService.unblock(listId);
            }
            if (!block.equals("block")) {
                String[] listId = block.split(",");
                personalDataService.block(listId);
            }
            model.put("some", personalDataService.getAll().toString().replace("[", "").replace(",", "").replace("]", ""));
        } else {
            Security.setRole(0L);
            line = "failure";
        }
        return new ModelAndView(line, model);
    }

    @GetMapping("/result")
    public String result(@RequestParam String delete){

        return "Привет " + delete;
    }

}
