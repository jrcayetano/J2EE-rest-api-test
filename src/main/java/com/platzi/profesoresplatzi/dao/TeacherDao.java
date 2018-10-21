package com.platzi.profesoresplatzi.dao;

import java.util.List;

import com.platzi.profesoresplatzi.model.Teacher;

public interface TeacherDao {
	

	void saveTeacher(Teacher teacher);
	
	List<Teacher> findAllTeachers();
	
	void deleteteacherById(Long idTeacher);
	
	void updateTeacher(Teacher teacher);
	
	Teacher findById(Long idTeacher);
	
	Teacher findByname(String name);

}
