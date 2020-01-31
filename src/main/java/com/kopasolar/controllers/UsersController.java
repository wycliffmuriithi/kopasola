package com.kopasolar.controllers;

import com.kopasolar.controllers.wrappers.CreateuserWrapper;
import com.kopasolar.controllers.wrappers.ResponseWrapper;
import com.kopasolar.controllers.wrappers.UserPasswordWrapper;
import com.kopasolar.controllers.wrappers.UseremailWrapper;
import com.kopasolar.services.UsermanagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Class name: UsersController
 * Creater: wgicheru
 * Date:1/27/2020
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsermanagementService usermanagementService;


    @GetMapping("/list")
    public ResponseWrapper getusersList(){
       return usermanagementService.getAllusers();
    }

//    @PostMapping("/new")
//    public ResponseWrapper createUser(@RequestBody CreateuserWrapper createuserwrapper){
//        return usermanagementService
//                .createUser(createuserwrapper.getUsername(),createuserwrapper.getEmail());
//    }

    //confirm the token against a user and redirect to set password page
    @GetMapping("/confirm")
    public ResponseWrapper confirmUser(@RequestParam int token){
        return usermanagementService.getUserbyToken(token);
    }

    @PostMapping("/passwordreset")
    public ResponseWrapper forgotPassword(@RequestBody UseremailWrapper useremailWrapper){
        return usermanagementService.deactivateUser(useremailWrapper.getEmail());
    }

}
