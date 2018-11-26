package com.safediz.ui;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.safediz.security.domain.EUserRole;
import com.safediz.security.domain.EUserStatus;
import com.safediz.security.domain.User;
import com.safediz.security.domain.service.ISecurityService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RegisterViewModel extends ViewModelHelper {

	private static final long serialVersionUID = 1L;

	@WireVariable(ISecurityService.NAME)
	private ISecurityService securityService;

	private User user;

	private String cpwd;

	@Init
	public void init() {
		user = new User();
		user.setRegisterationDate(new Date());
		user.setEditStatus(true);
		user.setRole(EUserRole.USER);
		user.setStatus(EUserStatus.Pending);
	}

	public void refreshRowTemplate(final User user) {
		BindUtils.postNotifyChange(null, null, user, "editStatus");
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
				securityService.saveUser(user, user.getPassword());
				refreshRowTemplate(user);
				// send welcome email
				message = "Successful Registration. You will be activated soon";
			}

		} catch (Exception ex) {
			message = null;
			error = ex.getMessage();
		}
	}

	private void validateUser() {
		if (user == null) {
			error = "NullPointerExcepton";
		} else if (user.getUsername() == null) {
			error = "Username is required";
		} else if (user.getPassword() == null) {
			error = "Password is required";
		} else if (cpwd == null) {
			error = "Confirm Password";
		} else if (!user.getPassword().equals(cpwd)) {
			error = "Passwords don't match";
		} else if (user.getEmail() == null) {
			error = "Email is required";
		}
	}

	public List<EUserRole> getUserRoles() {
		return Arrays.asList(EUserRole.values());
	}

	public List<EUserStatus> getUserStatuses() {
		return Arrays.asList(EUserStatus.values());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCpwd() {
		return cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}

}
