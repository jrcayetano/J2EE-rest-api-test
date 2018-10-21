package com.platzi.profesoresplatzi.dao;

import java.util.List;

import com.platzi.profesoresplatzi.model.Course;
import com.platzi.profesoresplatzi.model.SocialMedia;
import com.platzi.profesoresplatzi.model.TeacherSocialMedia;

public class SocialMediaDaoImpl extends AbstractSession implements SocialMediaDao {

	@Override
	public void saveSocialMedia(SocialMedia socialMedia) {
		getSession().persist(socialMedia);
		
	}

	@Override
	public List<SocialMedia> findAllSocialMedia() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from SocialMedia").list();
	}

	@Override
	public void deleteSocialMediaById(Long idSocialMedia) {
		// TODO Auto-generated method stub
		SocialMedia socialMedia = findById(idSocialMedia);
		if(socialMedia != null) {
			getSession().delete(socialMedia);
		}
		
	}

	@Override
	public void updateCourse(SocialMedia socialMedia) {
		getSession().update(socialMedia);
		
	}

	@Override
	public SocialMedia findById(Long idSocialMedia) {
		// TODO Auto-generated method stub
		return (SocialMedia) getSession().get(SocialMedia.class, idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		// TODO Auto-generated method stub
		return (SocialMedia) getSession()
				.createQuery("from SocialMedia where name = :name")
				.setParameter("name", name)
				.uniqueResult();
	}

	@Override
	public TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname) {
		// TODO Auto-generated method stub
		List<Object[]> objects = getSession()
				.createQuery("from TeacherSocialMedia tsm join tsm.socialMedia sm "
						+ "where sm.idSocialMedia = :socialMedia AND tsm.nickname = :nickname")
				.setParameter("idSocialMedia", idSocialMedia)
				.setParameter("nickname", nickname).list();
	   if(objects.size() > 0) {
		   for(Object[] objects2: objects) {
			   for(Object object : objects2) {
				   if(object instanceof TeacherSocialMedia) {
					   return (TeacherSocialMedia) object;
				   }
			   }
		   }
	   }
	   
	   return null;
	}

}
