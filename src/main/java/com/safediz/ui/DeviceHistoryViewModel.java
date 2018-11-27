package com.safediz.ui;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;

import com.safediz.device.domain.Device;
import com.safediz.device.domain.DeviceHistory;
import com.safediz.device.service.ISpaceDeviceService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DeviceHistoryViewModel extends ViewModelHelper {

	private static final long serialVersionUID = 1L;

	@WireVariable(ISpaceDeviceService.NAME)
	private ISpaceDeviceService deviceService;

	private Device selectedDevice;
	
	private List<DeviceHistory> deviceHistories = new ArrayList<>();


	@Init
	public void init(@ExecutionArgParam("record") final Device device) {
		this.selectedDevice = device;
		this.deviceHistories = deviceService.findDeviceHistory(selectedDevice);
	}

	@Command
	public void onCancel() {
		Div div = getParentWindow();
		Executions.createComponents("pages/devices.zul", div, null);
	}

	public Device getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(Device selectedDevice) {
		this.selectedDevice = selectedDevice;
	}

	public List<DeviceHistory> getDeviceHistories() {
		return deviceHistories;
	}

	public void setDeviceHistories(List<DeviceHistory> deviceHistories) {
		this.deviceHistories = deviceHistories;
	}
}
