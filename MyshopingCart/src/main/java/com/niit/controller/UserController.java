package com.niit.controller;

import java.util.Locale;

//javax.mail
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
//spring-context-support
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niit.dao.CustomerDAO;
import com.niit.dao.UserDao;
import com.niit.domain.BillingAddress;
import com.niit.domain.Customer;
import com.niit.domain.ShippingAddress;
import com.niit.domain.User;

@Controller
public class UserController {
	@Autowired
	MessageSource messageSource = null;

	@Autowired
	JavaMailSender mailsender;

	@Autowired
	User user;

	@Autowired
	UserDao userDao;
	
	@Autowired
	CustomerDAO  cdao;

	//Registration  Before customer+cart Module
    //display registration form
    @RequestMapping(value = "registration", method = RequestMethod.GET)
	public String userRegister(Model model) {

		model.addAttribute("user", user);
		return "register";

	}

	
   //post the registration form
	 @RequestMapping(value = "registration", method = RequestMethod.POST)
	 public String useRegister(@ModelAttribute("user") User user, Model model) {

		userDao.save(user);

		//MimeMessage mimeMessage = mailsender.createMimeMessage();

		//MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);

		try {
/*
			mailMsg.setFrom("dipendra.eng@gmail.com");
			mailMsg.setTo(user.getEmail());
			mailMsg.setSubject("Welcome to Ecommerce Portal!");
			mailMsg.setText(
					"<font color='red' size='7'>Hello User!<br/>This is to welocme you on our shopping Portal!</font>",
					true);
			mailsender.send(mimeMessage);
			System.out.println("Mail is sent*************************************************************");
*/
		}

		catch (Exception e) {

			e.printStackTrace();
		}
		model.addAttribute("registrationsuccess", "Congratulations Registered sucessfully!");
		return "login";

	}
    
    
    //Registration  After customer+cart Module
	//display registrationCustomer form
	@RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
	public ModelAndView getRegistrationForm() {
		Customer customer = new Customer();
		
		
		BillingAddress billingAddress = new BillingAddress();
		ShippingAddress shippingAddress = new ShippingAddress();
       
		//set billingAddress+shippingAddress+user to the customer
		customer.setUser(user);
		customer.setBillingAddress(billingAddress);
		customer.setShippingAddress(shippingAddress);
		return new ModelAndView("registrationCustomer", "customer", customer);

	}
	
	
	//post registrationCustomer form
	@RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
	public String registerCustomer(@Valid @ModelAttribute(value = "customer") Customer customer, BindingResult result,
				Model model) {
			if (result.hasErrors())
				return "registrationCustomer";

			/*List<Customer> customerList = cdao.getAllCustomers();
			for (Customer c : customerList) {
				if (c.getUsers().getUsername().equals(customer.getUsers().getUsername())) {
					model.addAttribute("duplicateUname", "Username already exists");
					return "registrationCustomer";
				}
				if (c.getCustomerEmail().equals(customer.getCustomerEmail())) {
					model.addAttribute("duplicateEmail", "Email Id is invalid or already exists!");
					return "registrationCustomer";
				}
			}
			
			*/
			
			

			cdao.addCustomer(customer);
			model.addAttribute("registrationsuccess", "Registered Successfully. Login using username and password");
			return "login";
		}

    
	

	// Login
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String userLogin(Model model) {

		model.addAttribute("user", user);
		return "login";

	}

	
	//Login-Before security
	/*@RequestMapping(value = "login", method = RequestMethod.POST)
	public String userLogin(@ModelAttribute("user") User user, Model model) {

		boolean flag = userDao.validate(user);
		if (flag) {
			model.addAttribute("uname", user.getName());
			return "home";
		} else {

			String errmsg = messageSource.getMessage("credentials.notfound", null, Locale.getDefault());
			model.addAttribute("error", errmsg);
			return "login";
		}

	}
	*/
	
	//Login -->after Security
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null)
			model.addAttribute("error", "Invalid username and password... Please enter them correctly");
		if (logout != null)
			model.addAttribute("logout", "logout successfully");

		return "login";
	}

	
	//for 403 access denied page
		@RequestMapping(value = "/invalid-access", method = RequestMethod.GET)
		public ModelAndView accesssDenied() {

		  ModelAndView model = new ModelAndView();

		  //check if user is login
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		  }

		  model.setViewName("403");
		  return model;

		}

}
