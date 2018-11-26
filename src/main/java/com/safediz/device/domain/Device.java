package com.safediz.device.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.openspaces.spatial.ShapeFactory;
import org.openspaces.spatial.shapes.Point;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.safediz.device.dao.IDeviceDao;
import com.safediz.security.User;

@SpaceClass(persist = true)
@Entity
@Table(name = "DEVICE", uniqueConstraints = { @UniqueConstraint(columnNames = { "IMEI_NUMBER" }) })
@NamedQueries({ @NamedQuery(name = IDeviceDao.QUERY_NAME.findAll, query = IDeviceDao.QUERY.findAll) })
public class Device extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "IMEI_NUMBER", nullable = false)
	private String imeiNumber;

	@Column(name = "DRIVER", nullable = true)
	private String driver;

	@Column(name = "ICON_NAME", nullable = true)
	private String icon;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "LATITUDE", nullable = true)
	private double latitude;

	@Column(name = "LONGITUDE", nullable = true)
	private double longitude;

	@JoinColumn(name = "OWNER", nullable = false)
	private User owner;

	@Transient
	private Point location;

	@Transient
	private boolean editStatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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
