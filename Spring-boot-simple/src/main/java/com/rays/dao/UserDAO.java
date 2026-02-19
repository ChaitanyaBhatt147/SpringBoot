package com.rays.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rays.dto.UserDTO;

@Repository
public class UserDAO {

	@PersistenceContext
	public EntityManager entityManager;
	
	public Long add(UserDTO dto) {
		entityManager.persist(dto);
		return dto.getId();
	}
	
	public void Update(UserDTO dto) {
		entityManager.merge(dto);
	}
	
	public void delete(UserDTO dto) {
		entityManager.remove(dto);
	}
	
	public UserDTO findByPk(Long id) {
		UserDTO dto = entityManager.find(UserDTO.class, id);
		return dto;
	}
	
	public List<UserDTO> search(UserDTO dto, int pageNo, int pageSize) {
		List<UserDTO> list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserDTO> cq = builder.createQuery(UserDTO.class);
		Root<UserDTO> qRoot = cq.from(UserDTO.class);
		cq.select(qRoot);
		TypedQuery<UserDTO> tq = entityManager.createQuery(cq);

		if (pageSize > 0) {
			tq.setFirstResult(pageNo * pageSize);
			tq.setMaxResults(pageSize);
		}

		list = tq.getResultList();

		return list;
	}
}
