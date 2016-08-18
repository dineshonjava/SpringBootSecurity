/**
 * 
 */
package com.dineshonjava.sbsecurity.service;

import com.dineshonjava.sbsecurity.bean.CurrentUser;

/**
 * @author Dinesh.Rajput
 *
 */
public interface CurrentUserService {
	
	 boolean canAccessUser(CurrentUser currentUser, Long userId);
}
