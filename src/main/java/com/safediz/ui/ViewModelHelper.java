package com.safediz.ui;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;

import com.safediz.security.User;
import com.safediz.security.service.ISecurityService;
import com.safediz.ui.utils.AbstractVM;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ViewModelHelper extends AbstractVM {

	private static final long serialVersionUID = 1L;

	@WireVariable(ISecurityService.NAME)
	private ISecurityService securityService;

	private String theme;

	private boolean spaceRepository = true;

	private boolean editable = true;

	private String keyword;

	private User currentUser;

	protected String error;

	protected String message;

	@Init
	public void init() {
		try {
			Subject currentSubject = SecurityUtils.getSubject();
			String username = currentSubject.getPrincipal().toString();
			if (currentSubject.isAuthenticated() && !"root".equals(username)) {
				currentUser = securityService.findUser(username);
			}
		} catch (Exception e) {
		}
	}

	protected Div getParentWindow() {
		Div div = (Div) Path.getComponent("/contents");
		div.getChildren().clear();
		return div;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isSpaceRepository() {
		return spaceRepository;
	}

	public void setSpaceRepository(boolean spaceRepository) {
		this.spaceRepository = spaceRepository;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public User getCurrentUser() {
		init();
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}