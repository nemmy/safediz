package com.safediz.ui;

import java.util.UUID;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import com.safediz.security.domain.User;
import com.safediz.security.domain.service.ISecurityService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ChangeUserPasswordViewModel extends ViewModelHelper {

	private static final long serialVersionUID = 1L;

	@WireVariable(ISecurityService.NAME)
	private ISecurityService securityService;
	
	@Wire
	private Window pwdWin;

	private User user;

	private String pwd;

	private String cpwd;

	@Init
	public void init(@ExecutionArgParam("record") final User record) {
		try {
			user = record;
		} catch (Exception e) {

		}
	}

	@Command
	@NotifyChange({ "user", "message", "error" })
	public void onSave() {
		try {
			validateUser();
			if (error != null) {
				message = null;
			} else {
				user.setEditStatus(!user.isEditStatus());
				if (user.getId() == null) {
					user.setId(UUID.randomUUID());
				}
				securityService.saveUser(user, pwd);
				// send welcome email
				message = "Password successful changed. The user can use the new password";

			}

		} catch (Exception ex) {
			message = null;
			error = ex.getMessage();
		}
	}

	private void validateUser() {
		if (pwd == null) {
			error = "Password is required";
		} else if (cpwd == null) {
			error = "Confirm Password";
		} else if (!pwd.equals(cpwd)) {
			error = "Passwords don't match";
		}
	}

	@Command
	public void onCancel() {
		pwdWin.detach();
		Div div = getParentWindow();
		Executions.createComponents("pages/users.zul", div, null);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCpwd() {
		return cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}
}
