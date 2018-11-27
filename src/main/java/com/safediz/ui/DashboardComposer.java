package com.safediz.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;

import com.safediz.device.domain.Device;
import com.safediz.device.domain.DeviceHistory;
import com.safediz.device.service.ISpaceDeviceService;
import com.safediz.security.domain.Configuration;
import com.safediz.security.domain.User;
import com.safediz.security.domain.service.ISecurityService;
import com.safediz.ui.utils.EINterval;
import com.safediz.ui.utils.MessageUtil;

import rw.gov.spring.SpringService;
import rw.gov.util.DateTimeUtil;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DashboardComposer extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable(ISecurityService.NAME)
	private ISecurityService securityService;

	@WireVariable(ISpaceDeviceService.NAME)
	private ISpaceDeviceService deviceService;

	private Configuration configuration;

	private List<Device> records = new ArrayList<>();

	private List<Device> selectedDevices = new ArrayList<>();

	private EINterval interval = EINterval.FIVE;

	@Init
	public void init() {
		this.configuration = securityService.readConfiguration();
		if (configuration != null) {
			this.interval = configuration.getRefreshTime();
		}

		User user = getLoggedUser();
		if (user != null) {
			this.records = deviceService.findUserDevices(user);
		}

		this.selectedDevices = records;
	}

	public User getLoggedUser() {
		User user = null;
		try {
			Subject currentSubject = SecurityUtils.getSubject();
			String username = currentSubject.getPrincipal().toString();
			if (currentSubject.isAuthenticated() && !"root".equals(username)) {
				user = securityService.findUser(username);
			}
		} catch (Exception e) {
		}
		return user;
	}

	@NotifyChange("selectedDevices")
	@Command
	public void onSelectDevice() {
		MessageUtil.display(selectedDevices.size() + " selected");
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	public void onTimer$timer(Event e) {
		// record history for all devices
		deviceService = SpringService.getInstance().getObjectForExceptionService(ISpaceDeviceService.class);
		List<Device> allDevices = deviceService.findAllDevices();
		for (Device device : allDevices) {
			DeviceHistory dh = new DeviceHistory();
			dh.setTime(DateTimeUtil.currentTimestamp());
			dh.setDevice(device);
			dh.setLatitude(device.getLatitude());
			dh.setLongitude(device.getLongitude());

			deviceService.saveDeviceHistory(dh);
		}

		Div div = (Div) Path.getComponent("/contents");
		div.getChildren().clear();
		Executions.createComponents("pages/dashboard.zul", div, null);
	}

	@NotifyChange("interval")
	@Command
	public void changeInterval() {
		this.configuration.setRefreshTime(interval);
		securityService.saveConfiguration(configuration);
		onTimer$timer(null);
	}

	public ListModelList<EINterval> getIntervals() {
		return new ListModelList<>(EINterval.values());
	}

	public EINterval getInterval() {
		return interval;
	}

	public void setInterval(EINterval interval) {
		this.interval = interval;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public List<Device> getRecords() {
		return records;
	}

	public void setRecords(List<Device> records) {
		this.records = records;
	}

	public List<Device> getSelectedDevices() {
		return selectedDevices;
	}

	public void setSelectedDevices(List<Device> selectedDevices) {
		this.selectedDevices = selectedDevices;
	}

}
