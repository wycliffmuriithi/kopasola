package com.kopasolar.services;

import com.kopasolar.controllers.wrappers.ResponseWrapper;
import com.kopasolar.database.KopausersRepo;
import com.kopasolar.database.models.KopaUsers;
import com.kopasolar.services.engines.SendmailEngine;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Class name: UsermanagementService
 * Creater: wgicheru
 * Date:1/27/2020
 */
@Service
public class UsermanagementService {
    @Autowired
    KopausersRepo kopausersRepo;
    @Autowired
    SendmailEngine sendmailEngine;

    private static Logger LOGGER = Logger.getLogger(UsermanagementService.class);

    public ResponseWrapper createUser(String username, String email) {
        //check if the user exists
        if (kopausersRepo.countByUsernameOrEmail(username, email) > 0) {
            //user exists
            return new ResponseWrapper("failed", "user already exists");
        }

        int token = sendTokenbymail(email);
        KopaUsers kopaUsers = new KopaUsers();
        kopaUsers.setUsername(username);
        kopaUsers.setEmail(email);
        kopaUsers.setActive(false);
        kopaUsers.setActivationtoken(token);

        kopausersRepo.save(kopaUsers);


        return new ResponseWrapper("success", "user created successfully, check mail for activation link");
    }

    public ResponseWrapper getAllusers() {
        return new ResponseWrapper("success", kopausersRepo.findAll());
    }

    /**
     * use this to set a user password
     * @param email
     * @param password
     *
     */
    public ResponseWrapper activateUser(String email,String password) {
        LOGGER.info("Activating user with token "+email+" password "+password);
        List<KopaUsers> kopaUser = kopausersRepo.findByEmail(email);
        kopaUser.stream().forEach((user) -> {
            user.setActive(true);
            user.setPassword(password);
            LOGGER.info("user "+user.getUserid()+" active");
        });
        kopausersRepo.saveAll(kopaUser);
        return new ResponseWrapper("success","password set successfully");
    }

    public ResponseWrapper checkUserPassword(String email,String password){
        List<KopaUsers> kopaUser = kopausersRepo.findByEmail(email);
        if (kopaUser.isEmpty()){
            return new ResponseWrapper("failed", "user does not exist");
        }
        KopaUsers kopauser = kopaUser.get(0);
        if(kopauser.getPassword().equals(password)){
            if(kopauser.isActive()) {
                return new ResponseWrapper("success", kopauser);
            }else{
                return new ResponseWrapper("failed","account has been deactivated, reset password to activate");
            }
        }else{
            return new ResponseWrapper("failed","incorrect credentials");
        }
    }

    public ResponseWrapper deactivateUser(String email) {
        List<KopaUsers> usersList = kopausersRepo.findByEmail(email);
        if (usersList.isEmpty()) {
            //user does not exist
            return new ResponseWrapper("failed", "user does not exist");
        }

        usersList.stream().forEach((user) -> {
            int token = sendTokenbymail(user.getEmail());
            user.setActive(false);
            user.setActivationtoken(token);
        });
        kopausersRepo.saveAll(usersList);

        return new ResponseWrapper("success", "user account has been reset, check email for activation token");
    }

    private int sendTokenbymail(String email) {

        int token = new Random().nextInt(99999) + 100000;
        String activationurl = String.format("http://localhost:8989/confirm?token=%d", token);
//        LOGGER.info("Sending mail )
        sendmailEngine
                .sendEmail("Kopa Solar User Account Activation",
                        String.format("Follow the link to activate your account%n%s", activationurl), email);

        return token;
    }

    public ResponseWrapper getUserbyToken(int token){
        List<KopaUsers> kopaUser = kopausersRepo.findByActivationtokenAndActiveFalse(token);
        if (kopaUser.isEmpty()){
            return new ResponseWrapper("failed", "user does not exist");
        }
        return new ResponseWrapper("success",kopaUser.get(0));
    }
}
