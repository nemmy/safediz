package com.safediz.security;

import java.util.Date;

import javax.persistence.Transient;

import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.safediz.device.domain.AbstractEntity;

public class User extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String firstname;
	private String lastname;
	private String email;
	private EUserRole role;
	private EUserStatus status;
	private String username;
	private String password;
	private Date registerationDate;
	private Boolean loggedIn;
	private Date lastLoggedIn;

	@Transient
	private boolean editStatus;

	public String getFullname() {
		return lastname + " " + firstname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EUserRole getRole() {
		return role;
	}

	public void setRole(EUserRole role) {
		this.role = role;
	}

	public EUserStatus getStatus() {
		return status;
	}

	public void setStatus(EUserStatus status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegisterationDate() {
		return registerationDate;
	}

	public void setRegisterationDate(Date registerationDate) {
		this.registerationDate = registerationDate;
	}

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Date getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(Date lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	@SpaceExclude
	public boolean isEditStatus() {
		return editStatus;
	}

	public void setEditStatus(boolean editStatus) {
		this.editStatus = editStatus;
	}

	public boolean isAdmin() {
		return role != null && EUserRole.ADMIN == role;
	}

}
