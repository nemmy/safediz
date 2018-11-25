
package com.safediz.ui.filter;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

public class AuthController implements Initiator {

	
	@Override
	public void doInit(final Page page, final Map<String, Object> args) throws Exception {

		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			Executions.sendRedirect("login");
		}

	}

}

