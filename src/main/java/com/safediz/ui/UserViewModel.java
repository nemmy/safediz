package com.safediz.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.safediz.security.domain.EUserRole;
import com.safediz.security.domain.EUserStatus;
import com.safediz.security.domain.User;
import com.safediz.security.domain.service.ISecurityService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserViewModel extends ViewModelHelper {

	private static final long serialVersionUID = 1L;

	@WireVariable(ISecurityService.NAME)
	private ISecurityService securityService;

	private List<User> records = new ArrayList<>();

	@Init
	public void init() {
		loadUsers();
	}

	private void loadUsers() {
		records = securityService.findAllUsers();

	}

	@Command
	@NotifyChange({ "records" })
	public void onFilter() {
		loadUsers();
		if (getKeyword() != null && !getKeyword().isEmpty()) {
			records = records.parallelStream()
					.filter(e -> StringUtils.containsIgnoreCase(e.getFullname(), getKeyword())
							|| StringUtils.containsIgnoreCase(e.getEmail(), getKeyword())
							|| StringUtils.containsIgnoreCase(e.getRole().toString(), getKeyword())
							|| StringUtils.containsIgnoreCase(e.getUsername(), getKeyword())
							|| StringUtils.containsIgnoreCase(e.getStatus().toString(), getKeyword()))
					.collect(Collectors.toList());
		}
	}

	@Command
	@NotifyChange({ "records", "editable" })
	public void onAddNew() {
		User record = new User();
		record.setRegisterationDate(new Date());
		record.setEditStatus(true);
		records.add(0, record);
		setEditable(!isEditable());
	}

	public void refreshRowTemplate(final User user) {
		BindUtils.postNotifyChange(null, null, user, "editStatus");
	}

	@Command
	@NotifyChange({ "records", "editable" })
	public void confirm(@BindingParam("record") final User user) {

		if (user != null) {
			user.setEditStatus(!user.isEditStatus());
			if (user.getId() == null) {
				user.setId(UUID.randomUUID());
				securityService.saveUser(user, user.getPassword());
			} else {
				securityService.saveUser(user);
			}
			refreshRowTemplate(user);
			setEditable(!isEditable());
		}
	}

	@Command
	@NotifyChange({ "records", "editable" })
	public void onRemove(@BindingParam("record") final User user) {

		if (user != null) {
			securityService.deleteUser(user);
			records.remove(user);
		}
	}

	@Command
	@NotifyChange({ "records", "editable" })
	public void changeEditableStatus(@BindingParam("record") final User user) {
		if (user.getId() == null) {
			records.remove(0);
		} else {
			user.setEditStatus(!user.isEditStatus());
		}
		refreshRowTemplate(user);
		setEditable(!isEditable());
	}
	
	@Command
	public void onChangePassword(@BindingParam("record") User user) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("record", user);
		Executions.createComponents("pages/changeUserPassword.zul", null, map);
	}

	public List<User> getRecords() {
		return records;
	}

	public void setRecords(List<User> records) {
		this.records = records;
	}

	public List<EUserRole> getUserRoles() {
		return Arrays.asList(EUserRole.values());
	}

	public List<EUserStatus> getUserStatuses() {
		return Arrays.asList(EUserStatus.values());
	}
}
