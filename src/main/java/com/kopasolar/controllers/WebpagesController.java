package com.kopasolar.controllers;

import com.kopasolar.controllers.wrappers.*;
import com.kopasolar.database.models.KopaProducts;
import com.kopasolar.database.models.KopaUsers;
import com.kopasolar.database.models.SalesPayments;
import com.kopasolar.services.ProductsmanagementService;
import com.kopasolar.services.PurchasemanagementService;
import com.kopasolar.services.UsermanagementService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Autowired
    ProductsmanagementService productsmanagementService;
    @Autowired
    PurchasemanagementService purchasemanagementService;

    private static final Logger LOGGER = Logger.getLogger(WebpagesController.class);


    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage( HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        UserPasswordWrapper loggedin = (UserPasswordWrapper) request.getSession().getAttribute("LOGIN_SESSION");
        if(loggedin == null) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
            mv.setViewName("Login");
            mv.addObject("usermodel", new UserPasswordWrapper());
//        }
            return mv;
        }else{
            return new ModelAndView("redirect:/home");
        }
    }



    /**
     * handle incorrect login details
     *
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginResult(HttpServletRequest request,ModelAndView modelAndView, @ModelAttribute("usermodel") UserPasswordWrapper userPasswordWrapper, BindingResult bindingResult) {
        ResponseWrapper responseWrapper = usermanagementService.checkUserPassword(userPasswordWrapper.getEmail(), userPasswordWrapper.getPassword());
        if (responseWrapper.getStatus().equals("failed")) {
            modelAndView.setViewName("Login");
//            modelAndView.addObject("incorrectdetails", true);
            bindingResult.rejectValue("email", "error.email", responseWrapper.getData().toString());
        } else {
            request.getSession().setAttribute("LOGIN_SESSION", userPasswordWrapper);
            return new ModelAndView("redirect:/home");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView destroySession(HttpServletRequest request) {
        //invalidate the session , this will clear the data from configured database (Mysql/redis/hazelcast)
        request.getSession().invalidate();
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView getHomepage(HttpSession session){
        UserPasswordWrapper loggedin =(UserPasswordWrapper) session.getAttribute("LOGIN_SESSION");
        ModelAndView modelAndView = new ModelAndView();
        if(loggedin==null){
            return new ModelAndView("redirect:/login");
        }
        modelAndView.setViewName("Home");
        modelAndView.addObject("usermodel",loggedin);

        //add products model
        List<KopaProducts> productsList = productsmanagementService.getProducts();
        modelAndView.addObject("productslist",productsList);

        //stock model
        List<StocksWrapper> stockList = productsmanagementService.getStock();
        modelAndView.addObject("stocklist",stockList);

        //sales model
        List<SalesWrapper> salesList = purchasemanagementService.getSales();
        modelAndView.addObject("saleslist",salesList);

        //payments model
        List<SalesPayments> paymentsList = purchasemanagementService.getPayments();
        modelAndView.addObject("paymentslist",paymentsList);

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

    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/forgot", method = RequestMethod.GET)
    public ModelAndView getForgotpasswordPage() {
        ModelAndView mv = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
        mv.setViewName("Forgotpassword");
        mv.addObject("useremail", new UseremailWrapper());
//        }
        return mv;
    }

    /**
     * handle incorrect login details
     *
     * @return
     */
    @RequestMapping(path = "/forgot", method = RequestMethod.POST)
    public ModelAndView processLoginResult(ModelAndView modelAndView, @ModelAttribute("useremail") UseremailWrapper useremailWrapper, BindingResult bindingResult) {
        ResponseWrapper responseWrapper = usermanagementService.deactivateUser(useremailWrapper.getEmail());
        modelAndView.setViewName("Forgotpassword");
        if (responseWrapper.getStatus().equals("failed")) {
//            modelAndView.addObject("incorrectdetails", true);
            bindingResult.rejectValue("email", "error.email", responseWrapper.getData().toString());
        } else {
            modelAndView.addObject("paramsuccess", true);
        }
        return modelAndView;
    }


    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/newproduct", method = RequestMethod.GET)
    public ModelAndView getNewproductPage() {
        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
        modelAndView.setViewName("Product");
        modelAndView.addObject("productmodel", new AddproductWrapper());
//        }
        return modelAndView;
//        return "redirect:/login";
    }

    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/newproduct", method = RequestMethod.POST)
    public ModelAndView createNewproductPage(ModelAndView mv, @ModelAttribute("productmodel") AddproductWrapper addproductWrapper) {
//        ModelAndView mv = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
        productsmanagementService.createProduct(addproductWrapper.getProductname(),addproductWrapper.getDescription(),addproductWrapper.getPrice());
        mv.setViewName("Product");
//        mv.addObject("stockmodel", new AddstockWrapper());
        //add products model
        List<KopaProducts> productsList = productsmanagementService.getProducts();
        mv.addObject("productslist",productsList);
//        }
//        return mv;
        return new ModelAndView("redirect:/home");
    }



    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/newsale", method = RequestMethod.GET)
    public ModelAndView getNewsalePage() {
        ModelAndView mv = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
        mv.setViewName("Sales");
        mv.addObject("salesmodel", new AddsalesWrapper());
        //add products model
        List<KopaProducts> productsList = productsmanagementService.getProducts();
        mv.addObject("productslist",productsList);
//        }
        return mv;
    }

    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/newsale", method = RequestMethod.POST)
    public ModelAndView createNewsalePage(ModelAndView mv, @ModelAttribute("salesmodel") AddsalesWrapper addsalesWrapper, BindingResult bindingResult) {
//        ModelAndView mv = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
        ResponseWrapper responseWrapper = purchasemanagementService.makeSale(addsalesWrapper.getProductid(),addsalesWrapper.getQuantity());
        if(responseWrapper.getStatus().equals("failed")) {


            mv.setViewName("Sales");
//        mv.addObject("stockmodel", new AddstockWrapper());
            //add products model
            List<KopaProducts> productsList = productsmanagementService.getProducts();
            mv.addObject("productslist", productsList);
            bindingResult.rejectValue("quantity", "error.quantity", responseWrapper.getData().toString());
            return mv;
        }else {
//        }
//        return mv;
            return new ModelAndView("redirect:/home");
        }
    }


    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/newstock", method = RequestMethod.GET)
    public ModelAndView getNewstockPage() {
        ModelAndView mv = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
        mv.setViewName("Stock");
        mv.addObject("stockmodel", new AddstockWrapper());
        //add products model
        List<KopaProducts> productsList = productsmanagementService.getProducts();
        mv.addObject("productslist",productsList);
//        }
        return mv;
    }

    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/newstock", method = RequestMethod.POST)
    public ModelAndView createNewstockPage(ModelAndView mv, @ModelAttribute("stockmodel") AddstockWrapper addstockWrapper) {
//        ModelAndView mv = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
        productsmanagementService.createStock(addstockWrapper.getProductid(),
                addstockWrapper.getQuantity(),addstockWrapper.getMinquantity());
        mv.setViewName("Stock");
//        mv.addObject("stockmodel", new AddstockWrapper());
        //add products model
        List<KopaProducts> productsList = productsmanagementService.getProducts();
        mv.addObject("productslist",productsList);
//        }
//        return mv;
        return new ModelAndView("redirect:/home");
    }

    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/newpayment", method = RequestMethod.GET)
    public ModelAndView getNewpaymentsPage() {
        ModelAndView mv = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
        mv.setViewName("Payment");
        mv.addObject("paymentmodel", new PaymentsWrapper());
        //add products model
        List<SalesWrapper> salesWrapperList = purchasemanagementService.getSales();
        mv.addObject("saleslist",salesWrapperList);
//        }
        return mv;
    }

    /**
     * retrieve the login page
     *
     * @return
     */
    @RequestMapping(path = "/newpayment", method = RequestMethod.POST)
    public ModelAndView createNewpaymentPage(@ModelAttribute("paymentmodel") PaymentsWrapper paymentsWrapper) {
//        ModelAndView mv = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().isEmpty()){
//            mv.setViewName("Login");
//        }else {
      purchasemanagementService.makePayment(paymentsWrapper.getOrderid(),paymentsWrapper.getAmount());
//        mv.setViewName("Stock");
//        mv.addObject("stockmodel", new AddstockWrapper());
        //add products model
//        List<KopaProducts> productsList = productsmanagementService.getProducts();
//        mv.addObject("productslist",productsList);
//        }
//        return mv;
        return new ModelAndView("redirect:/home");
    }
}
