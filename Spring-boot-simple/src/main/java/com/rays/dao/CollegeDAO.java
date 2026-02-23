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

import org.springframework.stereotype.Repository;

import com.rays.dto.CollegeDTO;

@Repository
public class CollegeDAO {

	@PersistenceContext
	public EntityManager entityManager;

	public long add(CollegeDTO dto) {
		entityManager.persist(dto);
		return dto.getId();
	}

	public void update(CollegeDTO dto) {
		entityManager.merge(dto);
	}

	public void delete(CollegeDTO dto) {
		entityManager.remove(dto);
	}

	public CollegeDTO findByPk(Long pk) {
		CollegeDTO dto = entityManager.find(CollegeDTO.class, pk);
		return dto;
	}

	public CollegeDTO findByName(String name) {
		CollegeDTO dto = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<CollegeDTO> cq = builder.createQuery(CollegeDTO.class);
			Root<CollegeDTO> qRoot = cq.from(CollegeDTO.class);
			Predicate predicate = builder.equal(qRoot.get("name"), name);
			cq.where(predicate);
			TypedQuery<CollegeDTO> tq = entityManager.createQuery(cq);
			dto = tq.getSingleResult();
			return dto;			
		} catch (NoResultException e) {
			return dto;			
		}
	}

	public List<CollegeDTO> search(CollegeDTO dto, int pageNo, int pageSize) {
		List<CollegeDTO> list = new ArrayList<CollegeDTO>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CollegeDTO> cq = builder.createQuery(CollegeDTO.class);
		Root<CollegeDTO> qRoot = cq.from(CollegeDTO.class);
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (dto != null) {
			if (dto.getName() != null && dto.getName().length() > 0) {
				predicateList.add(builder.like(qRoot.get("name"), dto.getName() + "%"));
			}
			if (dto.getCity() != null && dto.getCity().length() > 0) {
				predicateList.add(builder.like(qRoot.get("city"), dto.getCity() + "%"));
			}
			if (dto.getState() != null && dto.getState().length() > 0) {
				predicateList.add(builder.like(qRoot.get("state"), dto.getState() + "%"));
			}
		}

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<CollegeDTO> tq = entityManager.createQuery(cq);

		if (pageSize > 0) {
			tq.setFirstResult((pageNo - 1) * pageSize);
			tq.setMaxResults(pageSize);
		}

		list = tq.getResultList();
		return list;
	}
}
