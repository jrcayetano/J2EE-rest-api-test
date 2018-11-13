package com.platzi.profesoresplatzi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.platzi.profesoresplatzi.model.SocialMedia;
import com.platzi.profesoresplatzi.model.TeacherSocialMedia;

@Repository
public interface SocialMediaDao extends JpaRepository<SocialMedia, Long> {
	
	@Query("FROM SocialMedia sm WHERE sm.name = ?1")
    SocialMedia findByName(String name);
	/*
	void saveSocialMedia(SocialMedia socialMedia);
	
	void deleteSocialMediaById(Long idSocialMedia);
	
	void updateCourse(SocialMedia socialMedia);
	
	List<SocialMedia> findAllSocialMedia();
	
	SocialMedia findById(Long idSocialMedia);
	
	SocialMedia findByName(String name);
	
	TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname); */

}
