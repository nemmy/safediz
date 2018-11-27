/**
 * 
 */
package com.safediz.device.domain;

import javax.persistence.MappedSuperclass;

import rw.gov.framework.domain.GuidDomainBo;


@MappedSuperclass
public abstract class AbstractEntity extends GuidDomainBo {

	
	private static final long serialVersionUID = 1L;
	
}
