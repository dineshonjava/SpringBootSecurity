/**
 * 
 */
package com.dineshonjava.sbsecurity.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dineshonjava.sbsecurity.bean.UserBean;
import com.dineshonjava.sbsecurity.bean.validator.UserBeanValidator;
import com.dineshonjava.sbsecurity.service.UserService;

/**
 * @author Dinesh.Rajput
 *
 */
@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserBeanValidator userBeanValidator;
	
	@InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userBeanValidator);
    }
	
	@RequestMapping("/users")
	public ModelAndView getUsersPage() {
		LOGGER.debug("Getting users page");
		return new ModelAndView("users", "users", userService.getAllUsers());
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping("/user/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
		LOGGER.debug("Getting user page for user={}", id);
        return new ModelAndView("user", "user", userService.getUserById(id));
    }
	
	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
    	 LOGGER.debug("Getting user create form");
        return new ModelAndView("user_create", "form", new UserBean());
    }
    
	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form") UserBean form, BindingResult bindingResult) {
    	 LOGGER.debug("Processing user create form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user_create";
        }
        try {
            userService.create(form);
        } catch (DataIntegrityViolationException e) {
        	LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email.exists", "Email already exists");
            return "user_create";
        }
        return "redirect:/users";
    }
}
