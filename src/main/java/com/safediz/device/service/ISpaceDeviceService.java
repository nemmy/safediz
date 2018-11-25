/**
 * 
 */
package com.safediz.device.service;

import java.util.List;

import com.safediz.device.domain.Device;

public interface ISpaceDeviceService {

	public final String NAME = "SpaceDeviceService";

	void saveDevice(Device party);
	
	void deleteDevice(Device party);
	
	List<Device> findAllDevices();
 
}
