package com.safediz.ui;

import java.util.UUID;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import com.safediz.security.domain.Configuration;
import com.safediz.security.domain.service.ISecurityService;
import com.safediz.util.EINterval;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConfigurationViewModel extends ViewModelHelper {

	private static final long serialVersionUID = 1L;

	@WireVariable(ISecurityService.NAME)
	private ISecurityService securityService;

	private Configuration configuration;

	@Init
	public void init() {
		this.setConfiguration(securityService.readConfiguration());
	}

	public void refreshRowTemplate(final Configuration config) {
		BindUtils.postNotifyChange(null, null, config, "editStatus");
	}

	@Command
	@NotifyChange({ "configuration", "editable" })
	public void confirm() {

		if (configuration != null) {
			configuration.setEditStatus(!configuration.isEditStatus());
			if (configuration.getId() == null) {
				configuration.setId(UUID.randomUUID());
			}
			securityService.saveConfiguration(configuration);
			refreshRowTemplate(configuration);
			setEditable(!isEditable());
		}
	}

	@Command
	@NotifyChange({ "configuration", "editable" })
	public void changeEditableStatus() {
		configuration.setEditStatus(!configuration.isEditStatus());
		refreshRowTemplate(configuration);
		setEditable(!isEditable());
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public ListModelList<EINterval> getIntervals() {
		return new ListModelList<>(EINterval.values());
	}

}
