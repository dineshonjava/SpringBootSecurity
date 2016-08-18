/**
 * 
 */
package com.dineshonjava.sbsecurity.bean;

import org.springframework.security.core.authority.AuthorityUtils;

import com.dineshonjava.sbsecurity.model.User;
import com.dineshonjava.sbsecurity.role.Role;

/**
 * @author Dinesh.Rajput
 *
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
    public Long getId() {
        return user.getUserid();
    }

    public Role getRole() {
        return user.getRole();
    }
    
    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
}
}
