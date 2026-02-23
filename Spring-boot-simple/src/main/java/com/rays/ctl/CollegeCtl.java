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
import com.rays.dto.CollegeDTO;
import com.rays.form.CollegeForm;
import com.rays.form.UserForm;
import com.rays.service.CollegeService;

@RestController
@RequestMapping("College")
public class CollegeCtl extends BaseCtl {

	@Autowired
	CollegeService collegeService;

	@PostMapping("save")
	public ORSResponse save(@RequestBody @Valid CollegeForm form, BindingResult bindingResult) {
		ORSResponse res = new ORSResponse();
		res = validate(bindingResult);
		if (!res.isSuccess()) {
			return res;
		}

		CollegeDTO dto = (CollegeDTO) form.getDto();
		long id = collegeService.add(dto);
		res.setSuccess(true);
		res.addMessage("College addedd successfully");
		res.addData(dto);

		return res;
	}

	@PostMapping("update")
	public ORSResponse update(@RequestBody @Valid CollegeForm form, BindingResult bindingResult) {
		ORSResponse res = new ORSResponse();
		res = validate(bindingResult);
		if (!res.isSuccess()) {
			return res;
		}

		CollegeDTO dto = (CollegeDTO) form.getDto();
		dto = (CollegeDTO) form.initDTO(dto);
		collegeService.update(dto);
		res.setSuccess(true);
		res.addMessage("College Updated successfully");

		return res;
	}

	@PostMapping("delete/{ids}")
	public ORSResponse delete(@PathVariable(required = false) long[] ids) {
		ORSResponse res = new ORSResponse();
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				collegeService.delete(id);
				res.addMessage("recored deleted successfully");
				res.setSuccess(true);
			}
		} else {
			res.addMessage("select at least one record");
		}
		return res;
	}

	@GetMapping("get/{id}")
	public ORSResponse findByPk(@PathVariable(required = false) long id) {
		ORSResponse res = new ORSResponse();
		CollegeDTO dto = collegeService.findByPk(id);
		if (dto != null) {
			res.addData(dto);
			res.setSuccess(true);
		}
		return res;
	}
	
	@GetMapping("get/{name}")
	public ORSResponse findByName(@PathVariable(required = false) String name) {
		ORSResponse res = new ORSResponse();
		CollegeDTO dto = collegeService.findByName(name);
		if (dto != null) {
			res.addData(dto);
			res.setSuccess(true);
		}
		return res;
	}
	
	@RequestMapping(value = "/search/{pageNo}", method = { RequestMethod.GET, RequestMethod.POST })
	public ORSResponse search(@RequestBody UserForm form, @PathVariable(required = false) int pageNo) {
		ORSResponse res = new ORSResponse();
		CollegeDTO dto = (CollegeDTO) form.getDto();
		int pageSize = 5;
		List<CollegeDTO> list = collegeService.search(dto, pageNo, pageSize);
		if (list.size() > 0) {
			res.setSuccess(true);
			res.addData(list);
			return res;
		}
		
		return res;
	}
}
