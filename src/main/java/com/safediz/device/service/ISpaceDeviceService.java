/**
 * 
 */
package com.safediz.device.service;

import java.util.List;

import com.safediz.device.domain.Device;
import com.safediz.device.domain.DeviceHistory;
import com.safediz.security.domain.User;

public interface ISpaceDeviceService {

	public final String NAME = "SpaceDeviceService";

	void saveDevice(Device device);

	void deleteDevice(Device device);

	void saveDeviceHistory(DeviceHistory deviceHistory);

	void deleteDeviceHistory(DeviceHistory deviceHistory);

	List<Device> findAllDevices();

	List<Device> findUserDevices(User user);

	List<DeviceHistory> findDeviceHistory(Device device);

}
