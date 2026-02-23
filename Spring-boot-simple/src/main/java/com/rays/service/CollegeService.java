package com.rays.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rays.dao.CollegeDAO;
import com.rays.dto.CollegeDTO;

@Service
@Transactional
public class CollegeService {
	@Autowired
	public CollegeDAO collegeDao;

	

	@Transactional(propagation = Propagation.REQUIRED)
	public long add(CollegeDTO dto) {
		long pk = 0;
		CollegeDTO collegeDto = collegeDao.findByName(dto.getName());
		if (collegeDto != null) {
			throw new RuntimeException("College Alrady exist");
		}
		pk = collegeDao.add(dto);
		return pk;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(CollegeDTO dto) {
		CollegeDTO collegeDto = collegeDao.findByName(dto.getName());
		if (collegeDto != null && !(collegeDto.getName().equals(dto.getName()))) {
			throw new RuntimeException("College Alrady exist");
		}
		collegeDao.update(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {
		try {
			CollegeDTO dto = collegeDao.findByPk(id);
			collegeDao.delete(dto);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Transactional(readOnly = true)
	public CollegeDTO findByPk(long id) {
		CollegeDTO dto = collegeDao.findByPk(id);
		return dto;
	}
	
	@Transactional(readOnly = true)
	public CollegeDTO findByName(String name) {
		CollegeDTO dto = collegeDao.findByName(name);
		return dto;
	}
	
	@Transactional(readOnly = true)
	public List<CollegeDTO> search(CollegeDTO dto, int pageNo, int pageSize) {
		List<CollegeDTO> list = collegeDao.search(dto, pageNo, pageSize);
		return list;
	}
}
