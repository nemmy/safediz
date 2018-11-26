package com.safediz.device.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.j_spaces.core.client.SQLQuery;
import com.safediz.device.dao.IDeviceDao;
import com.safediz.device.domain.Device;
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
	public void deleteDevice(Device device) {
		deviceDao.deleteDevice(device);
		dataSpace.clear(device);
	}

	@PostConstruct
	public void init() {
		
		// load data from db into space
		List<User> allUsers = userDao.findAll();
		if (!allUsers.isEmpty()) {
			dataSpace.writeMultiple(allUsers.toArray());
		}

		List<Device> allDevices = deviceDao.findAll();
		if (!allDevices.isEmpty()) {
			dataSpace.writeMultiple(allDevices.toArray());
		}

	}
}
