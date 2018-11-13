package com.platzi.profesoresplatzi.controller;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.platzi.profesoresplatzi.model.SocialMedia;
import com.platzi.profesoresplatzi.service.SocialMediaService;

@Controller
@RequestMapping("/v1")
public class SocialMediaController {
	
	@Autowired
	SocialMediaService _socialMediaService;
	
	//GET
	@RequestMapping(value="/socialMedia", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<SocialMedia>> getSocialMedia() {
		System.out.println("Entra");
		List<SocialMedia> socialMedias = new ArrayList<SocialMedia>();
		
		socialMedias = _socialMediaService.findAllSocialMedia();
		
		if(socialMedias.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
		
	}
	
	//GET
		@RequestMapping(value="/socialMedia/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
		public ResponseEntity<SocialMedia> getSocialMedia(@PathVariable("id") Long idSocialMedia) {
		
			
			SocialMedia socialMedia = _socialMediaService.findById(idSocialMedia);
			System.out.println(socialMedia);
			if(socialMedia == null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<SocialMedia>(socialMedia, HttpStatus.OK);
			
		}
	
	//POST
		@RequestMapping(value="/socialMedia", method = RequestMethod.POST, headers = "Accept=application/json")
		public ResponseEntity<?> createSocialMedia(@RequestBody SocialMedia socialMedia, UriComponentsBuilder uriComponentsBuilder) {
			if(socialMedia.getName().equals(null) || socialMedia.getName().isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			
			if(_socialMediaService.findByName(socialMedia.getName()) != null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			
			_socialMediaService.saveSocialMedia(socialMedia);
			/*
			SocialMedia socialMedia2 = _socialMediaService.findByName(socialMedia.getName());
			HttpHeaders headers = new HttpHeaders();
			headers
				.setLocation(uriComponentsBuilder.path("/v1/socialMedia/{id}")
				.buildAndExpand(socialMedia2.getIdSocialMedia()).toUri());
				*/
		    return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		
		//Update
		@RequestMapping(value="/socialMedia/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
		public ResponseEntity<?> updateSocialMedia(@PathVariable("id") Long idSocialMedia, @RequestBody SocialMedia socialMedia) {
			
			SocialMedia socialMediaFound = _socialMediaService.findById(idSocialMedia);
			System.out.println(socialMediaFound);
			if(socialMediaFound == null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			
			socialMediaFound.setName(socialMedia.getName());
			socialMediaFound.setIcon(socialMedia.getIcon());
			
			
			System.out.println(socialMediaFound.getName());
			_socialMediaService.updateSocialMedia(socialMediaFound);
			
		    return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		//DELETE
		@RequestMapping(value="/socialMedia/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
		public ResponseEntity<?> deleteSocialMedia(@PathVariable("id") Long idSocialMedia) {
			
			
			SocialMedia socialMediaFound = _socialMediaService.findById(idSocialMedia);
			
			if(socialMediaFound == null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			
			_socialMediaService.deleteSocialMediaById(idSocialMedia);
			
		    return new ResponseEntity<String>(HttpStatus.OK);
		}
		/*
		public ResponseEntity<List<SocialMedia>> getSocialMedia() {
			System.out.println("Entra");
			List<SocialMedia> socialMedias = new ArrayList<SocialMedia>();
			
			socialMedias = _socialMediaService.findAllSocialMedia();
			
			if(socialMedias.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
			
		}*/
	

}
