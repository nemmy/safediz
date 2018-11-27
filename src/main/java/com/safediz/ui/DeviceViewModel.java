package com.safediz.ui;

import java.util.UUID;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;

import com.safediz.device.domain.Device;
import com.safediz.device.domain.GeoLocation;
import com.safediz.device.service.ISpaceDeviceService;
import com.safediz.security.domain.service.ISecurityService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DeviceViewModel extends ViewModelHelper {

	private static final long serialVersionUID = 1L;

	@WireVariable(ISpaceDeviceService.NAME)
	private ISpaceDeviceService registryService;

	@WireVariable(ISecurityService.NAME)
	private ISecurityService securityService;

	private Device selectedDevice;

	private GeoLocation currentLocation;

	private String icon;

	@Init
	public void init(@ExecutionArgParam("record") final Device device) {
		this.selectedDevice = device;
		this.icon = selectedDevice == null ? null : selectedDevice.getIcon();
		currentLocation = findCurrentLocation("minecofin.gov.rw");
	}

	public void refreshRowTemplate(final Device refreshedRow) {
		BindUtils.postNotifyChange(null, null, refreshedRow, "editStatus");
	}

	@Command
	public void confirm() {

		if (selectedDevice != null) {
			selectedDevice.setEditStatus(!selectedDevice.isEditStatus());
			if (selectedDevice.getGuid() == null) {
				selectedDevice.setGuid(UUID.randomUUID());
			}

			if (icon != null) {
				selectedDevice.setIcon(icon);
			}

			registryService.saveDevice(selectedDevice);
			refreshRowTemplate(selectedDevice);
			setEditable(!isEditable());
		}

		onCancel();
	}

	@Command
	public void onCancel() {
		Div div = getParentWindow();
		Executions.createComponents("pages/devices.zul", div, null);
	}

	public void onTimer$timer(Event e) {
		Div div = getParentWindow();
		Executions.createComponents("pages/dashboard.zul", div, null);
	}

	public GeoLocation getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(GeoLocation currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Device getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(Device selectedDevice) {
		this.selectedDevice = selectedDevice;
	}

}
