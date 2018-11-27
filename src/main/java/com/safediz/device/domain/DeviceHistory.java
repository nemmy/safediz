package com.safediz.device.domain;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openspaces.spatial.ShapeFactory;
import org.openspaces.spatial.shapes.Point;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.safediz.device.dao.IDeviceHistoryDao;

@SpaceClass(persist = true)
@Entity
@Table(name = "DEVICE_HISTORY")
@NamedQueries({ @NamedQuery(name = IDeviceHistoryDao.QUERY_NAME.findAll, query = IDeviceHistoryDao.QUERY.findAll) })
public class DeviceHistory extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "TIME", nullable = false)
	private Timestamp time;

	@Column(name = "IMEI_NUMBER", nullable = false)
	private String imeiNumber;

	@Column(name = "LATITUDE", nullable = true)
	private double latitude;

	@Column(name = "LONGITUDE", nullable = true)
	private double longitude;
	
	@Transient
	private Point location;

	@Transient
	private Device device;

	@Transient
	private boolean editStatus;

	@Transient
	private UUID spaceId;
	
	@SpaceId(autoGenerate = false)
	public UUID getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(final UUID spaceId) {
		this.spaceId = spaceId;
	}
	
	@SpaceIndex
	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		if (device != null) {
			this.imeiNumber = device.getImeiNumber();
		}
		this.device = device;
	}

	public Point getLocation() {
		location = ShapeFactory.point(latitude, longitude);
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	@SpaceExclude
	public boolean isEditStatus() {
		return editStatus;
	}

	public void setEditStatus(boolean editStatus) {
		this.editStatus = editStatus;
	}
}
