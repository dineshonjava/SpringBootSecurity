/**
 * 
 */
package com.dineshonjava.sbsecurity.bean.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.dineshonjava.sbsecurity.bean.UserBean;
import com.dineshonjava.sbsecurity.service.UserService;

/**
 * @author Dinesh.Rajput
 *
 */
@Component
public class UserBeanValidator implements Validator{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserBeanValidator.class);
	
	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserBean.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.debug("Validating {}", target);
		UserBean bean = (UserBean) target;
        validatePasswords(errors, bean);
        validateEmail(errors, bean);
	}
	private void validatePasswords(Errors errors, UserBean bean) {
        if (!bean.getPassword().equals(bean.getPasswordRepeated())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserBean bean) {
        if (userService.getUserByEmail(bean.getEmail()) != null) {
            errors.reject("email.exists", "User with this email already exists");
        }
    }
}
