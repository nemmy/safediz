package com.safediz.device.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safediz.device.domain.Device;

import rw.gov.framework.dao.GuidDao;

@Repository(IDeviceDao.NAME)
@Transactional
public class DeviceDao extends GuidDao<Device> implements IDeviceDao {

	@Override
	public List<Device> findAll() {
		return executeNamedQueryMultiple(IDeviceDao.QUERY_NAME.findAll);
	}

	@Override
	public void deleteDevice(Device device) {
		super.remove(device);
	}

}
