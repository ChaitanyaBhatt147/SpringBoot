package com.rays.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.dto.AttachmentDTO;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;
import com.rays.service.AttachmentService;

@Repository
public class UserDAO {

	@PersistenceContext
	public EntityManager entityManager;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	RoleDAO roleDao;

	public UserDTO populate(UserDTO dto) {
		if (dto.getRoleId() != null && dto.getRoleId() > 0) {
			RoleDTO roleDto = roleDao.findByPk(dto.getRoleId());
			dto.setRoleName(roleDto.getName());
		}
		return dto;
	}

	public Long add(UserDTO dto) {
		dto = populate(dto);
		entityManager.persist(dto);
		return dto.getId();
	}

	public void Update(UserDTO dto) {
		dto = populate(dto);
		entityManager.merge(dto);
	}

	public void delete(UserDTO dto) {

		if (dto.getImageId() != null && dto.getImageId() > 0) {
			AttachmentDTO adto = attachmentService.findById(dto.getImageId());
			if (adto != null) {
				attachmentService.delete(adto.getId());
			}
		}

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

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (dto != null) {
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				predicateList.add(builder.like(qRoot.get("firstName"), dto.getFirstName() + "%"));
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				predicateList.add(builder.like(qRoot.get("lastName"), dto.getLastName() + "%"));
			}
			if (dto.getLogin() != null && dto.getLogin().length() > 0) {
				predicateList.add(builder.like(qRoot.get("login"), dto.getLogin() + "%"));
			}
			if (dto.getRoleName() != null && dto.getRoleName().length() > 0) {
				predicateList.add(builder.like(qRoot.get("roleName"), dto.getRoleName() + "%"));
			}
		}

//		cq.select(qRoot);
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<UserDTO> tq = entityManager.createQuery(cq);

		if (pageSize > 0) {
			tq.setFirstResult(pageNo * pageSize);
			tq.setMaxResults(pageSize);
		}

		list = tq.getResultList();

		return list;
	}

	public UserDTO findByUnique(String attribute, String value) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserDTO> cq = builder.createQuery(UserDTO.class);
		Root<UserDTO> qRoot = cq.from(UserDTO.class);

		Predicate predicate = builder.equal(qRoot.get(attribute), value);

		cq.where(predicate);
		TypedQuery<UserDTO> tq = entityManager.createQuery(cq);

		try {
			UserDTO dto = tq.getSingleResult();
			return dto;
		} catch (NoResultException e) {
			return null;
		}

	}
}
