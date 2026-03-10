package com.rays.common;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class BaseDTO implements DropDownList{
	@Id
	@GeneratedValue(generator = "ncsPk")
	@GenericGenerator(name = "ncsPk", strategy = "native")
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "created_by", length = 50)
	private String createdBy;
	
	@Column(name = "modifIed_by", length = 50)
	private String modifIedBy;
	
	@Column(name = "created_datetime")
	private Timestamp createdDatetime;
	
	@Column(name = "modified_datetime")
	private Timestamp modifiedDatetime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifIedBy() {
		return modifIedBy;
	}

	public void setModifIedBy(String modifIedBy) {
		this.modifIedBy = modifIedBy;
	}

	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	@Override
	public String getKey() {
		return id+"";
	}
}
