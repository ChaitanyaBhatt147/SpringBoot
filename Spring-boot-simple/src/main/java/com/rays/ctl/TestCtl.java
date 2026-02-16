package com.rays.ctl;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.dto.TestDto;

@RestController
@RequestMapping("Test")
public class TestCtl {
	@GetMapping("display")
	public String display() {
		return "display Method.............!";
	}

	@PostMapping("submit")
	public String submit() {
		return "submit Method.............!";
	}

	@GetMapping("getDto")
	public TestDto getDto() {
		TestDto dto = new TestDto();
		dto.setId(1);
		dto.setFirstName("Chaitanya");
		dto.setLastName("Bhatt");
		dto.setLogin("bhattchaitanya43@gmail.com");
		dto.setPassword("Chetan2001@");
		dto.setAddress("Udaipur");
		dto.setDob(new Date());
		return dto;
	}
}
