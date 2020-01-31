package com.kopasolar.controllers;

import com.kopasolar.controllers.wrappers.CreateuserWrapper;
import com.kopasolar.controllers.wrappers.ResponseWrapper;
import com.kopasolar.controllers.wrappers.UserPasswordWrapper;
import com.kopasolar.database.models.KopaUsers;
import com.kopasolar.services.UsermanagementService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Class name: WebpagesController
 * Creater: wgicheru
 * Date:1/28/2020
 */
@RestController
public class WebpagesController {
    @Autowired
    UsermanagementService usermanagementService;
    private static final Logger LOGGER = Logger.getLogger(WebpagesController.class);

    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage() {
        ModelAndView mv = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
        mv.setViewName("Login");
        mv.addObject("usermodel", new UserPasswordWrapper());
//        }
        return mv;
    }

    /**
     * handle incorrect login details
     *
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginResult(ModelAndView modelAndView, @ModelAttribute("usermodel") UserPasswordWrapper userPasswordWrapper, BindingResult bindingResult) {
        ResponseWrapper responseWrapper = usermanagementService.checkUserPassword(userPasswordWrapper.getEmail(), userPasswordWrapper.getPassword());
        if (responseWrapper.getStatus().equals("failed")) {
            modelAndView.setViewName("Login");
//            modelAndView.addObject("incorrectdetails", true);
            bindingResult.rejectValue("email", "error.email", responseWrapper.getData().toString());
        } else {
            modelAndView.setViewName("Home");
            modelAndView.addObject("usermodel",userPasswordWrapper);
            List<KopaUsers> usesList = (List) usermanagementService.getAllusers().getData();
            modelAndView.addObject("users",usesList);
        }
        return modelAndView;
    }


    /**
     * retrieve the registration page
     *
     * @param modelAndView
     * @param userModel
     * @return
     */
    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    public ModelAndView getRegistrationPage(ModelAndView modelAndView, CreateuserWrapper userModel) {
        modelAndView.setViewName("Register");
        modelAndView.addObject("user", userModel);
        return modelAndView;
    }

    /**
     * post the registration results to backend
     *
     * @param modelAndView
     * @param usermodel
     * @param bindingResult
     * @return
     */
    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public ModelAndView registernewUser(ModelAndView modelAndView, @ModelAttribute("user") CreateuserWrapper usermodel, BindingResult bindingResult) {
//        LOGGER.info("Sign up object " + usermodel.toString());
        modelAndView.setViewName("Register");
        ResponseWrapper registrationresult = usermanagementService.createUser(usermodel.getUsername(), usermodel.getEmail());
        if (registrationresult.getStatus().equals("failed")) {
            bindingResult.rejectValue("username", "error.email", "Duplicate registration details detected");
        } else {
            modelAndView.addObject("paramsuccess", true);
        }
        return modelAndView;
    }

    /**
     * retrieve the set password page
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(path = "/confirm", method = RequestMethod.GET)
    public ModelAndView getchangePasswordPage(@RequestParam int token, ModelAndView modelAndView,  BindingResult bindingResult) {
        ResponseWrapper responseWrapper = usermanagementService.getUserbyToken(token);
        modelAndView.setViewName("Password");
        UserPasswordWrapper userModel = new UserPasswordWrapper();

        if(responseWrapper.getStatus().equals("failed")){
            modelAndView.addObject("user", userModel);
            //user by token not found
//            bindingResult.rejectValue("email", "error.email", responseWrapper.getData().toString());
            modelAndView.addObject("incorrectdetails", true);
        }else{
           KopaUsers kopaUsers = (KopaUsers)responseWrapper.getData();
           userModel.setEmail(kopaUsers.getEmail());
           modelAndView.addObject("user",userModel);
        }
        return modelAndView;
    }

    /**
     * retrieve the set password page
     *
     * @param modelAndView
     * @param userModel
     * @return
     */
    @RequestMapping(path = "/confirm", method = RequestMethod.POST)
    public ModelAndView getchangePasswordPage(ModelAndView modelAndView, @ModelAttribute("user") UserPasswordWrapper userModel, BindingResult bindingResult) {
        modelAndView.setViewName("Password");
        LOGGER.info(userModel.toString());
        if(!userModel.getPassword().equals(userModel.getConfirmpassword())){
            bindingResult.rejectValue("password","error.password","The passwords do not match");
        }else{
            modelAndView.addObject("paramsuccess", true);
            usermanagementService.activateUser(userModel.getEmail(),userModel.getPassword());
        }
        return modelAndView;
    }

}
