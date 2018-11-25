package com.safediz.device.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safediz.device.domain.Device;

import rw.gov.framework.dao.GuidDao;

@Repository(IDeviceDao.NAME)
public class DeviceDao extends GuidDao<Device> implements IDeviceDao {

	@Override
	public List<Device> findAll() {
		return executeNamedQueryMultiple(IDeviceDao.QUERY_NAME.findAll);
	}

}
