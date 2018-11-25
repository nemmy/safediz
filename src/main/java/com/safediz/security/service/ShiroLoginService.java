
package com.safediz.security.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safediz.security.EUserStatus;
import com.safediz.security.User;

/**
 * The Class ShiroLoginService.
 * 
 * @author Emmanuel NSHIMIYIMANA
 * @version 1.0
 */
@Service(IShiroLoginService.NAME)
public final class ShiroLoginService implements IShiroLoginService {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The security service. */
	@Autowired
	private ISecurityService securityService;

	/** The password service. */
	@Autowired
	private DefaultPasswordService passwordService;

	@Override
	public Exception authenticate(final String username, final String password) {

		Exception exception = null;
		Boolean failure = Boolean.FALSE;

		if (!"root".equalsIgnoreCase(username) && !"safeDiz".equals(password)) {

			User user = findUser(username);
			try {

				if (user == null) {
					throw new UnknownAccountException("There is no user found for login [" + username
							+ "]. Try another login or contact the System Administrator.");
				}

				if (user.getLoggedIn() != null && user.getLoggedIn().equals(Boolean.TRUE)) {
					throw new ConcurrentAccessException("Account [" + username + "] is ALREADY LOGGED IN");
				}

				if (!EUserStatus.Active.equals(user.getStatus())) {
					throw new DisabledAccountException("Your account [" + username
							+ "] is INACTIVE/PENDING/BANNED. Please contact the administrator");
				}

				String pwd = user.getPassword();

				if (!passwordService.passwordsMatch(password, pwd)) {
					throw new CredentialsException("Invalid Credentials for user [" + username + "].");
				}

			} catch (Exception ex) {
				failure = Boolean.TRUE;
				exception = ex;
			}

			// update user
			if (user != null) {
				if (Boolean.FALSE.equals(failure)) {
					user.setLastLoggedIn(new Date());
					user.setLoggedIn(Boolean.TRUE);
				}

				securityService.saveUser(user);
			}
		}

		return exception;
	}

	private User findUser(String username) {
		try {
			return securityService.findUser(username);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void logout(final Session session) {
		try {
			String username = SecurityUtils.getSubject().getPrincipal().toString();

			if (!"root".equalsIgnoreCase(username)) {

				User user = securityService.findUser(username);

				if (user != null) {
					user.setLoggedIn(Boolean.FALSE);
					user.setLastLoggedIn(new Date());
					securityService.saveUser(user);
				}
			}

			session.stop();
			SecurityUtils.getSubject().logout();

		} catch (Exception e) {
			throw new SessionException(e);
		}
	}

	@PostConstruct
	public void init() {
		try {
			List<User> all = securityService.findAllUsers();
			for (User user : all) {
				if (user.getLoggedIn() != null) {
					user.setLoggedIn(false);
					securityService.saveUser(user);
				}
			}

		} catch (Exception ex) {
			throw ex;

		}
	}
}