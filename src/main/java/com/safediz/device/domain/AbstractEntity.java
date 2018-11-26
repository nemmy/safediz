/**
 * 
 */
package com.safediz.device.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import com.gigaspaces.annotation.pojo.SpaceId;

import net.sf.oval.constraint.NotNull;
import rw.gov.framework.domain.GuidDomainBo;


@MappedSuperclass
public abstract class AbstractEntity extends GuidDomainBo {

	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Id
	@Column(name = "ID", nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;

 
	@SpaceId
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
}
