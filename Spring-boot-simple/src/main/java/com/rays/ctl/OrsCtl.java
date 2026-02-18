package com.rays.ctl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.ORSResponse;
import com.rays.dto.TestDto;

@RestController
@RequestMapping(value = "ors")
public class OrsCtl {
	
	@GetMapping
	public ORSResponse display() {
		ORSResponse res = new ORSResponse();
		res.addMessage("data added successfully");
		res.setSuccess(true);
		return res;
	}
	
	
	@GetMapping("display1")
	public ORSResponse display1() {
		ORSResponse res = new ORSResponse();
		res.addInputError("FirstName is required");
		return res;
	}
	
	
	@GetMapping("display2")
	public ORSResponse display2() {
		Map<String, String> errors = new HashMap<String, String>();
		ORSResponse res = new ORSResponse();
		errors.put("firstName", "firstName is required");
		errors.put("lastName", "lastName is required");
		errors.put("login", "login is required");
		errors.put("password", "password is required");
		res.addInputError(errors);
		return res;
	}
	
	
	@GetMapping("display3")
	public ORSResponse display3() {
		List list = new ArrayList();
		TestDto dto = new TestDto();
		ORSResponse res = new ORSResponse();
		list.add(dto);
		res.addData(list);
		return res;
	}
	
	
	@GetMapping("display4")
	public ORSResponse display4() {
		List roleList = new ArrayList();
		ORSResponse res = new ORSResponse();
		
		roleList.add("admin");
		roleList.add("student");
		roleList.add("college");
		roleList.add("kiosk");
		
		res.addResult("roleList", roleList);
		return res;
	}
	
}
