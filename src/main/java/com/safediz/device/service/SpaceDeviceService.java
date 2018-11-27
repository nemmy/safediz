package com.safediz.device.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.j_spaces.core.client.SQLQuery;
import com.safediz.device.dao.IDeviceDao;
import com.safediz.device.dao.IDeviceHistoryDao;
import com.safediz.device.domain.Device;
import com.safediz.device.domain.DeviceHistory;
import com.safediz.security.domain.User;
import com.safediz.security.domain.dao.IUserDao;

@Service(ISpaceDeviceService.NAME)
@Transactional
public class SpaceDeviceService implements ISpaceDeviceService {

	@Autowired
	private GigaSpace dataSpace;

	@Autowired
	private IDeviceDao deviceDao;

	@Autowired
	private IDeviceHistoryDao deviceHistoryDao;

	@Autowired
	private IUserDao userDao;

	@Override
	public void saveDevice(Device device) {

		if (device.isNew()) {
			deviceDao.create(device);
		} else {
			deviceDao.update(device);
		}

		dataSpace.write(device);
	}

	@Override
	public List<Device> findAllDevices() {
		SQLQuery<Device> query = new SQLQuery<>(Device.class, " order by name");

		Device[] records = dataSpace.readMultiple(query);

		return new ArrayList<>(Arrays.asList(records));
	}

	@Override
	public List<Device> findUserDevices(User user) {
		SQLQuery<Device> query = new SQLQuery<>(Device.class, "ownerId = ? order by name");
		query.setParameters(user.getUsername());

		Device[] records = dataSpace.readMultiple(query);

		return new ArrayList<>(Arrays.asList(records));
	}

	@Override
	public void deleteDevice(Device device) {
		deviceDao.deleteDevice(device);
		dataSpace.clear(device);
	}

	@Override
	public void saveDeviceHistory(DeviceHistory deviceHistory) {
		Device device = deviceHistory.getDevice();
		device.setLatitude(deviceHistory.getLatitude());
		device.setLongitude(deviceHistory.getLongitude());
		saveDevice(device);

		deviceHistoryDao.create(deviceHistory);
		deviceHistory.setSpaceId(deviceHistory.getGuid());
		dataSpace.write(deviceHistory);

	}

	@Override
	public void deleteDeviceHistory(DeviceHistory deviceHistory) {
		deviceHistoryDao.deleteDeviceHistory(deviceHistory);
		dataSpace.clear(deviceHistory);

	}

	@Override
	public List<DeviceHistory> findDeviceHistory(Device device) {
		SQLQuery<DeviceHistory> query = new SQLQuery<>(DeviceHistory.class, "imeiNumber = ? ");
		query.setParameters(device.getImeiNumber());

		DeviceHistory[] records = dataSpace.readMultiple(query);

		return new ArrayList<>(Arrays.asList(records));
	}

	@PostConstruct
	public void init() {

		// load data from db into space
		Map<String, User> userMap = new HashMap<>();
		List<User> allUsers = userDao.findAll();
		for (User user : allUsers) {
			userMap.put(user.getUsername(), user);
		}
		if (!allUsers.isEmpty()) {
			dataSpace.writeMultiple(allUsers.toArray());
		}

		Map<String, Device> deviceMap = new HashMap<>();
		List<Device> allDevices = deviceDao.findAll();
		for (Device device : allDevices) {
			User user = userMap.get(device.getOwnerId());
			device.setOwner(user);
			deviceMap.put(device.getImeiNumber(), device);
		}
		if (!allDevices.isEmpty()) {
			dataSpace.writeMultiple(allDevices.toArray());
		}

		List<DeviceHistory> allDeviceHistory = deviceHistoryDao.findAll();
		for (DeviceHistory deviceHistory : allDeviceHistory) {
			Device device = deviceMap.get(deviceHistory.getImeiNumber());
			deviceHistory.setDevice(device);
			deviceHistory.setSpaceId(device.getGuid());
		}
		if (!allDeviceHistory.isEmpty()) {
			dataSpace.writeMultiple(allDeviceHistory.toArray());
		}

	}
}
