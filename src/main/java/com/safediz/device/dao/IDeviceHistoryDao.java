package com.safediz.device.dao;

import java.util.List;

import com.safediz.device.domain.DeviceHistory;

import rw.gov.framework.dao.IGuidDao;

public interface IDeviceHistoryDao extends IGuidDao<DeviceHistory> {

	public static final String NAME = "DeviceHistoryDao";

	public static class QUERY {

		public static final String findAll = "select a from DeviceHistory a";
		
	}

	public static class QUERY_NAME {

		public static final String findAll = "DeviceHistory.findAll";
	}

	public abstract List<DeviceHistory> findAll();

	public abstract void deleteDeviceHistory(DeviceHistory DeviceHistory);

}

