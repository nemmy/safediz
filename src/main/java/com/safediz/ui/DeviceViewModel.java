package com.safediz.ui;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;

import com.safediz.device.domain.Device;
import com.safediz.device.domain.GeoLocation;
import com.safediz.device.service.GeoIPv4;
import com.safediz.device.service.ISpaceDeviceService;
import com.safediz.security.Configuration;
import com.safediz.security.service.ISecurityService;
import com.safediz.util.EINterval;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DeviceViewModel extends ViewModelHelper {

	private static final long serialVersionUID = 1L;

	@WireVariable(ISpaceDeviceService.NAME)
	private ISpaceDeviceService registryService;

	@WireVariable(ISecurityService.NAME)
	private ISecurityService securityService;

	private Configuration configuration;

	private List<Device> records = new ArrayList<>();

	private EINterval interval = EINterval.FIVE;

	private GeoLocation currentLocation;

	private String icon;

	@Init
	public void init() {
		this.configuration = securityService.readConfiguration();
		if (configuration != null) {
			this.interval = configuration.getRefreshTime();
		}

		this.records = registryService.findAllDevices();

		currentLocation = findCurrentLocation("minecofin.gov.rw");
	}

	private GeoLocation findCurrentLocation(String hostname) {
		GeoLocation location = null;
		try {
			InetAddress ip = null;
			if (hostname != null) {
				ip = InetAddress.getByName(hostname);
			} else {
				ip = InetAddress.getLocalHost();
			}
			location = GeoIPv4.getLocation(ip);
		} catch (Exception e) {
			location = findCurrentLocation("gov.rw");
		}
		return location;

	}

	@Command
	@NotifyChange({ "records", "editable" })
	public void onAddNew() {
		Device record = new Device();
		record.setEditStatus(true);
		if (currentLocation != null) {
			record.setLatitude(currentLocation.getLatitude());
			record.setLongitude(currentLocation.getLongitude());
		}
		records.add(0, record);
		setEditable(!isEditable());
	}

	public void refreshRowTemplate(final Device refreshedRow) {
		BindUtils.postNotifyChange(null, null, refreshedRow, "editStatus");
	}

	@Command
	@NotifyChange({ "records", "editable" })
	public void confirm(@BindingParam("record") final Device device) {

		if (device != null) {
			device.setEditStatus(!device.isEditStatus());
			if (device.getId() == null) {
				device.setId(UUID.randomUUID());
			}

			if (icon != null) {
				device.setIcon(icon);
			}

			registryService.saveDevice(device);
			refreshRowTemplate(device);
			setEditable(!isEditable());
		}
	}

	@Command
	@NotifyChange({ "records", "editable" })
	public void onRemove(@BindingParam("record") final Device party) {

		if (party != null) {
			registryService.deleteDevice(party);
			records.remove(party);
		}
	}

	@Command
	@NotifyChange({ "records", "editable" })
	public void changeEditableStatus(@BindingParam("record") final Device party) {
		if (party.getId() == null) {
			records.remove(0);
		} else {
			party.setEditStatus(!party.isEditStatus());
		}
		refreshRowTemplate(party);
		setEditable(!isEditable());
	}

	public void onTimer$timer(Event e) {
		Div div = getParentWindow();
		Executions.createComponents("pages/dashboard.zul", div, null);
	}

	public List<Device> getRecords() {
		return records;
	}

	public void setRecords(List<Device> records) {
		this.records = records;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public EINterval getInterval() {
		return interval;
	}

	public void setInterval(EINterval interval) {
		this.interval = interval;
	}

	public GeoLocation getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(GeoLocation currentLocation) {
		this.currentLocation = currentLocation;
	}

	public List<String> getIcons() {
		return Arrays.asList("ambulance", "big-truck", "bus", "cabriolet", "lorry-green", "lorry", "mini-bus", "pickup",
				"police", "sportcar", "taxi", "truck", "voiture");
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
