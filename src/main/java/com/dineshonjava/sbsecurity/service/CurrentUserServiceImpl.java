/**
 * 
 */
package com.dineshonjava.sbsecurity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dineshonjava.sbsecurity.bean.CurrentUser;
import com.dineshonjava.sbsecurity.role.Role;

/**
 * @author Dinesh.Rajput
 *
 */
@Service
public class CurrentUserServiceImpl implements CurrentUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        LOGGER.debug("Checking if user={} has access to user={}", currentUser, userId);
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
}

}
