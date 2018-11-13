package com.platzi.profesoresplatzi.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.platzi.profesoresplatzi.dao.SocialMediaDao;
import com.platzi.profesoresplatzi.model.SocialMedia;
import com.platzi.profesoresplatzi.model.TeacherSocialMedia;

@Service("socialMediaService")
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService{
	
	@Autowired
	private SocialMediaDao _socialMediaDao;

	@Override
	public void saveSocialMedia(SocialMedia socialMedia) {
		// TODO Auto-generated method stub
		_socialMediaDao.save(socialMedia);
		
	}

	@Override
	public void deleteSocialMediaById(Long idSocialMedia) {
		// TODO Auto-generated method stub
		_socialMediaDao.deleteById(idSocialMedia);
	}

	@Override
	public void updateSocialMedia(SocialMedia socialMedia) {
		_socialMediaDao.save(socialMedia);
		// TODO Auto-generated method stub
		// _socialMediaDao.(socialMedia);
	}

	@Override
	public List<SocialMedia> findAllSocialMedia() {
		// TODO Auto-generated method stub
		return _socialMediaDao.findAll();
	}

	@Override
	public SocialMedia findById(Long idSocialMedia) {
		// TODO Auto-generated method stub
		return _socialMediaDao.findById(idSocialMedia).orElse(null); // _socialMediaDao.findById(id)(idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		// TODO Auto-generated method stub
		System.out.println(name);
		return _socialMediaDao.findByName(name);
	}

	@Override
	public TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname) {
		// TODO Auto-generated method stub
		return null; //_socialMediaDao.findSocialMediaByIdAndName(idSocialMedia, nickname);
	}

}
