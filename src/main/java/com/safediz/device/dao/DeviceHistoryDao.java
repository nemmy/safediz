package com.safediz.device.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.safediz.device.domain.DeviceHistory;

import rw.gov.framework.dao.GuidDao;

@Repository(IDeviceHistoryDao.NAME)
@Transactional
public class DeviceHistoryDao extends GuidDao<DeviceHistory> implements IDeviceHistoryDao {

	@Override
	public List<DeviceHistory> findAll() {
		return executeNamedQueryMultiple(IDeviceHistoryDao.QUERY_NAME.findAll);
	}

	@Override
	public void deleteDeviceHistory(DeviceHistory deviceHistory) {
		super.remove(deviceHistory);
	}

}
