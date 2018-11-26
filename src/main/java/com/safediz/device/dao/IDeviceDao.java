package com.safediz.device.dao;

import java.util.List;

import com.safediz.device.domain.Device;

import rw.gov.framework.dao.IGuidDao;

public interface IDeviceDao extends IGuidDao<Device> {

	public static final String NAME = "DeviceDao";

	public static class QUERY {

		public static final String findAll = "select a from Device a";
		
	}

	public static class QUERY_NAME {

		public static final String findAll = "Device.findAll";
	}

	public abstract List<Device> findAll();

	public abstract void deleteDevice(Device device);

}

