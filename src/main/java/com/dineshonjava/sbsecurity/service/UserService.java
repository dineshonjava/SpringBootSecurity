/**
 * 
 */
package com.dineshonjava.sbsecurity.service;

import java.util.Collection;

import com.dineshonjava.sbsecurity.bean.UserBean;
import com.dineshonjava.sbsecurity.model.User;

/**
 * @author Dinesh.Rajput
 *
 */
public interface UserService {
	
	User getUserById(long id);

    User getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserBean userBean);
}
