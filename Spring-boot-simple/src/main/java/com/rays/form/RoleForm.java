package com.rays.form;

import javax.validation.constraints.NotEmpty;

public class RoleForm {
	
	private Long id = null;
	@NotEmpty(message = "Role name is Required")
	private String name;
	@NotEmpty(message = "Dscription is Required")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
