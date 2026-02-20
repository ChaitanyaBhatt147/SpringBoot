package com.rays.form;

import javax.validation.constraints.NotEmpty;

import com.rays.common.BaseDTO;
import com.rays.common.BaseForm;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;

public class RoleForm extends BaseForm{
	
	
	@NotEmpty(message = "Role name is Required")
	private String name;
	@NotEmpty(message = "Dscription is Required")
	private String description;

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
	
	@Override
	public BaseDTO getDto() {
		RoleDTO dto = (RoleDTO) initDTO(new RoleDTO());
		dto.setName(name);
		dto.setDescription(description);

		return dto;
	}
}
