package com.safediz.security.domain;

import java.util.UUID;

import javax.persistence.Transient;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.safediz.util.EINterval;

@SpaceClass
public class Configuration {

	private UUID id;
	
	private EINterval refreshTime = EINterval.OFF;
	
	private String logsUrl;
	
	@Transient
	private boolean editStatus;
	
	@SpaceId
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public EINterval getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(EINterval refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getLogsUrl() {
		return logsUrl;
	}

	public void setLogsUrl(String logsUrl) {
		this.logsUrl = logsUrl;
	}
	
	@SpaceExclude
	public boolean isEditStatus() {
		return editStatus;
	}

	public void setEditStatus(boolean editStatus) {
		this.editStatus = editStatus;
	}
}
