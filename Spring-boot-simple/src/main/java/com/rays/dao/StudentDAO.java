package com.rays.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.dto.CollegeDTO;
import com.rays.dto.StudentDTO;

@Repository
public class StudentDAO {

	@PersistenceContext
	public EntityManager entityManager;

	@Autowired
	CollegeDAO collegDao;

	public StudentDTO populate(StudentDTO dto) {
		if (dto.getCollegeId() != null && dto.getCollegeId() > 0) {
			CollegeDTO collegeDto = collegDao.findByPk(dto.getCollegeId());
			dto.setCollegeName(collegeDto.getName());
		}
		return dto;
	}

	public long add(StudentDTO dto) {
		dto = populate(dto);
		entityManager.persist(dto);
		return dto.getId();
	}

	public void update(StudentDTO dto) {
		dto = populate(dto);
		entityManager.merge(dto);
	}

	public void delete(StudentDTO dto) {
		entityManager.remove(dto);
	}

	public StudentDTO findByPk(Long id) {
		StudentDTO dto = entityManager.find(StudentDTO.class, id);
		return dto;
	}

	public StudentDTO findByLogin(String login) {
		StudentDTO dto = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<StudentDTO> cq = builder.createQuery(StudentDTO.class);
			Root<StudentDTO> qRoot = cq.from(StudentDTO.class);
			Predicate predicate = builder.equal(qRoot.get("email"), login);
			TypedQuery<StudentDTO> tq = entityManager.createQuery(cq);
			dto = tq.getSingleResult();
			return dto;
		} catch (Exception e) {
			return dto;
		}
	}

	public List<StudentDTO> search(StudentDTO dto, int pageNo, int pageSize) {
		List<StudentDTO> list = new ArrayList<StudentDTO>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentDTO> cq = builder.createQuery(StudentDTO.class);
		Root<StudentDTO> qRoot = cq.from(StudentDTO.class);
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (dto != null) {
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				predicateList.add(builder.like(qRoot.get("firstName"), dto.getFirstName() + "%"));
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				predicateList.add(builder.like(qRoot.get("lasstName"), dto.getLastName() + "%"));
			}
			if (dto.getEmail() != null && dto.getEmail().length() > 0) {
				predicateList.add(builder.like(qRoot.get("email"), dto.getEmail() + "%"));
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				predicateList.add(builder.like(qRoot.get("mobileNo"), dto.getMobileNo() + "%"));
			}
			if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
				predicateList.add(builder.like(qRoot.get("collegeName"), dto.getCollegeName() + "%"));
			}
			if (dto.getCollegeId() != null && dto.getCollegeId() > 0) {
				predicateList.add(builder.equal(qRoot.get("collegeId"), dto.getCollegeId()));
			}
		}
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<StudentDTO> tq = entityManager.createQuery(cq);
		if (pageSize > 0) {
			tq.setFirstResult((pageNo - 1) * pageSize);
			tq.setMaxResults(pageSize);
		}
		list = tq.getResultList();
		return list;
	}
}
