package com.platzi.profesoresplatzi.dao;

import com.platzi.profesoresplatzi.model.Teacher;
import com.platzi.profesoresplatzi.model.TeacherSocialMedia;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TeacherDaoImpl extends AbstractSession implements TeacherDao {
	

	public void saveTeacher(Teacher teacher) {
		
		getSession().persist(teacher);
	}

	public List<Teacher> findAllTeachers() {
		
		return getSession().createQuery("from Teacher").list();
	}

	public void deleteteacherById(Long idTeacher) {
		Teacher teacher = (Teacher) findById(idTeacher);
		
		if(teacher != null) {
			Iterator <TeacherSocialMedia>  i = teacher.getTeacherSocialMedia().iterator();
			while(i.hasNext()) {
				TeacherSocialMedia teacherSocialMedia = i.next();
				i.remove();
				getSession().delete(teacherSocialMedia);
			}
			teacher.getTeacherSocialMedia().clear();
			getSession().delete(teacher);
		}
		
	}

	public void updateTeacher(Teacher teacher) {
		getSession().update(teacher);
	}

	public Teacher findById(Long idTeacher) {
		// TODO Auto-generated method stub
		return (Teacher) getSession().get(Teacher.class, idTeacher);
	}

	public Teacher findByname(String name) {
		// TODO Auto-generated method stub
		return (Teacher) getSession().createQuery("from Teacher where name = :name").setParameter("name", name).uniqueResult();
	}
	
	public TeacherSocialMedia getTeacherSocialMedia() {
		return null;
	}
	
	

}
