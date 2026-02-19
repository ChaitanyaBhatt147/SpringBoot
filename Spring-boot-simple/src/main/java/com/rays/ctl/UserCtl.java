package com.rays.ctl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.UserDTO;
import com.rays.form.UserForm;
import com.rays.service.UserService;

@RestController
@RequestMapping("User")
public class UserCtl extends BaseCtl{

	@Autowired
	UserService userService;

	@PostMapping("save")
	public ORSResponse save(@RequestBody @Valid UserForm form, BindingResult bindingResult) {
		ORSResponse res = new ORSResponse();
		res = validate(bindingResult);
		if (!res.isSuccess()) {
			return res;
		}

		UserDTO dto = new UserDTO();
		dto.setFirstName(form.getFirstName());
		dto.setLastName(form.getLastName());
		dto.setLogin(form.getLoginId());
		dto.setPassword(form.getPassword());
		dto.setRoleId(form.getRoleId());
		dto.setDob(form.getDob());

		long id = userService.add(dto);

		res.setSuccess(true);
		res.addMessage("role addedd successfully");
		res.addData(dto);
		return res;
	}
	@PostMapping("update")
	public ORSResponse update(@RequestBody @Valid UserForm form, BindingResult bindingResult) {

		ORSResponse res = new ORSResponse();
		
		res = validate(bindingResult);
		if (!res.isSuccess()) {
			return res;
		}
		
		UserDTO dto = new UserDTO();
		dto.setId(form.getId());
		dto.setFirstName(form.getFirstName());
		dto.setLastName(form.getLastName());
		dto.setLogin(form.getLoginId());
		dto.setPassword(form.getPassword());
		dto.setRoleId(form.getRoleId());
		dto.setDob(form.getDob());	

		userService.update(dto);

		res.setSuccess(true);
		res.addMessage("role update successfully");

		return res;

	}

	@PostMapping("delete/{ids}")
	public ORSResponse delete(@PathVariable(required = false) long[] ids) {

		ORSResponse res = new ORSResponse();

		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				userService.delete(id);
				res.addMessage("recored deleted successfully");
				res.setSuccess(true);
			}
		} else {
			res.addMessage("select at least one record");
		}
		return res;
	}

	@GetMapping("get/{id}")
	public ORSResponse get(@PathVariable(required = false) long id) {

		ORSResponse res = new ORSResponse();

		UserDTO dto = userService.findByPk(id);

		if (dto != null) {
			res.addData(dto);
			res.setSuccess(true);
		}

		return res;

	}

	@RequestMapping(value = "/search/{pageNo}", method = { RequestMethod.GET, RequestMethod.POST })
	public ORSResponse search(@RequestBody UserForm form, @PathVariable(required = false) int pageNo) {

		ORSResponse res = new ORSResponse();

		int pageSize = 5;

		List<UserDTO> list = userService.search(null, pageNo, pageSize);

		if (list.size() > 0) {
			res.setSuccess(true);
			res.addData(list);
			return res;
		}

		return res;

	}
}
