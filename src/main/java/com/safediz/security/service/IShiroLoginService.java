
package com.safediz.security.service;

import java.io.Serializable;

import org.apache.shiro.session.Session;

/**
 * The Interface IShiroLoginService.
 * 
 * @author Emmanuel NSHIMIYIMANA
 * @version 1.0
 */
public interface IShiroLoginService extends Serializable {

	/** The Constant NAME. */
	public static final String NAME = "ShiroLoginService";

	/**
	 * Authenticate.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the exception
	 */
	public Exception authenticate(final String username, final String password);

	/**
	 * Logout.
	 *
	 * @param session
	 *            the session
	 */
	public void logout(final Session session);

}
