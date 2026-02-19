package com.rays.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rays.dao.UserDAO;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;

@Service
@Transactional
public class UserService {
	
	@Autowired
	public UserDAO userDao;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public long add(UserDTO dto) {
		return userDao.add(dto);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(UserDTO dto) {
		userDao.Update(dto);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		try {
			UserDTO dto = findByPk(id);
			userDao.delete(dto);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Transactional(readOnly = true)
	public UserDTO findByPk(Long id) {
		return userDao.findByPk(id);
	}
	
	
	@Transactional(readOnly = true)
	public List<UserDTO> search(UserDTO dto, int pageNo, int pageSize) {
		return userDao.search(dto, pageNo, pageSize);
	}
	
}
