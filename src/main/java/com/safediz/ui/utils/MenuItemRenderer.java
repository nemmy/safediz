package com.safediz.ui.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.SmartNotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Div;

import com.safediz.ui.ViewModelHelper;


public class MenuItemRenderer extends ViewModelHelper {

	private static final long serialVersionUID = 1L;

	private static final String windowComponent = "/container";

	private static final String rootPagePath = "pages/";

	private boolean sidebarMinimized = true;

	private String username;

	private String pwd;

	private String errorMessage;

	@Command
	public void onClickMenu(@BindingParam("path") String pagePath) {

		if (Path.getComponent(windowComponent) != null) {
			String zulFilePathName = pagePath + ".zul";
			Div div = (Div) Path.getComponent("/contents");
			div.getChildren().clear();
			Executions.createComponents(rootPagePath + zulFilePathName, div, null);
		}

	}

	@NotifyChange({ "errorMessage" })
	@Command
	public void login() {
		try {

			if (username == null) {
				errorMessage = "Please enter username";
				return;
			}

			if (pwd == null) {
				errorMessage = "Please enter password";
				return;
			}

			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
			subject.login(token);
			Executions.sendRedirect("index");
			
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}

	}

	@Command
	public void onRegister() {
		try {
			Executions.sendRedirect("register");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Div getParentWindow() {

		Div bl = (Div) org.zkoss.zk.ui.Path.getComponent(windowComponent);
		if (bl != null && bl.getChildren() != null) {
			bl.getChildren().clear();
		}
		return bl;

	}

	@SmartNotifyChange("sidebarMinimized")
	@Command
	public void controlSideBar() {
		setSidebarMinimized(!sidebarMinimized);
	}

	public boolean isSidebarMinimized() {
		return sidebarMinimized;
	}

	public void setSidebarMinimized(boolean sidebarMinimized) {
		this.sidebarMinimized = sidebarMinimized;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
