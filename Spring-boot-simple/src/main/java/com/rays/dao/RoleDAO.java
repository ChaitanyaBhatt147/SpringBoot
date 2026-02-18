package com.rays.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.rays.dto.RoleDTO;

@Repository
public class RoleDAO {
	@PersistenceContext
	public EntityManager entityManager;

	public long add(RoleDTO dto) {
		entityManager.persist(dto);
		return dto.getId();
	}

	public void update(RoleDTO dto) {
		entityManager.merge(dto);
	}

	public void delete(RoleDTO dto) {
		entityManager.remove(dto);
	}

	public RoleDTO findByPk(long pk) {
		RoleDTO dto = entityManager.find(RoleDTO.class, pk);
		return dto;
	}
}
