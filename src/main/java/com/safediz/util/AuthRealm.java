package com.safediz.util;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.safediz.security.service.IShiroLoginService;

@Transactional
public class AuthRealm extends AuthorizingRealm {

	@Autowired
	private IShiroLoginService shiroLoginService;

	public AuthRealm() {
		super(new MemoryConstrainedCacheManager());
	}

	/**
	 * Retrieves authentication data from an implementation-specific datasource
	 * (RDBMS, LDAP, etc) for the given authentication token.
	 *
	 * <p>
	 * For most datasources, this means just 'pulling' authentication data for an
	 * associated subject/user and nothing more and letting Shiro do the rest. But
	 * in some systems, this method could actually perform EIS specific log-in logic
	 * in addition to just retrieving data - it is up to the Realm implementation.
	 *
	 * <p>
	 * A <tt>null</tt> return value means that no account could be associated with
	 * the specified token.
	 *
	 * @param authcToken
	 *            the authentication token containing the user's principal and
	 *            credentials.
	 * @return an {@link AuthenticationInfo} object containing account data
	 *         resulting from the authentication ONLY if the lookup is successful
	 *         (i.e. account exists and is valid, etc.)
	 * @throws AuthenticationException
	 *             if there is an error acquiring data or performing realm-specific
	 *             authentication logic for the specified <tt>token</tt>
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authcToken) {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		String userName = token.getUsername();

		String credentials = new String(token.getPassword());

		try {

			Exception exception = shiroLoginService.authenticate(userName, credentials);

			boolean userAuthenticationFlag = false;

			if (exception == null) {
				userAuthenticationFlag = true;
			}
			SimpleAuthenticationInfo info = null;

			if (userAuthenticationFlag) {
				info = new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
			} else {
				throw new AuthenticationException(exception.getMessage());
			}

			return info;

		} catch (Exception ex) {
			throw ex;
		}

	}

	/**
	 * Retrieves the AuthorizationInfo for the given principals from the underlying
	 * data store. When returning an instance from this method, you might want to
	 * consider using an instance of
	 * {@link org.apache.shiro.authz.SimpleAuthorizationInfo
	 * SimpleAuthorizationInfo}, as it is suitable in most cases.
	 *
	 * @param principals
	 *            the primary identifying principals of the AuthorizationInfo that
	 *            should be retrieved.
	 * @return the AuthorizationInfo associated with this principals.
	 * @see org.apache.shiro.authz.SimpleAuthorizationInfo
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		return new SimpleAuthorizationInfo();
	}
}
