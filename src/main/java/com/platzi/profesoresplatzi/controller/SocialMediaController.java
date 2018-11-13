package com.platzi.profesoresplatzi.controller;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.platzi.profesoresplatzi.model.SocialMedia;
import com.platzi.profesoresplatzi.service.SocialMediaService;
import com.platzi.profesoresplatzi.util.CustomErrorType;

@Controller
@RequestMapping("/v1")
public class SocialMediaController {
	
	@Autowired
	SocialMediaService _socialMediaService;
	
	//GET
	@RequestMapping(value="/socialMedia", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<SocialMedia>> getSocialMedia(@RequestParam(value="name", required=false) String name) {
		
		List<SocialMedia> socialMedias = new ArrayList<SocialMedia>();
		
		if(name == null) {
			socialMedias = _socialMediaService.findAllSocialMedia();
			if(socialMedias.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
		}else {
			System.out.println(name);
			SocialMedia socialMedia = _socialMediaService.findByName(name);
			System.out.println(socialMedia);
			if(socialMedia == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			socialMedias.add(socialMedia);
			return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
		}

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
		
		//Upload icon
		public static final String SOCIAL_MEDIA_UPLOADS_FOLDER = "images/social_media";
		@RequestMapping(
			value="/socialMedia/images", 
			method = RequestMethod.POST, 
			headers = ("content-type=multipart/form-data")
		)
		public ResponseEntity<?> uploadIconSocialMedia(
				@RequestParam("id") Long idSocialMedia,
				@RequestParam("file") MultipartFile multipartFile,
				UriComponentsBuilder uriComponentsBuilder
		) {
			if(idSocialMedia == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			if(multipartFile.isEmpty()) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			
			SocialMedia socialMedia = _socialMediaService.findById(idSocialMedia);
			if(socialMedia  == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			
			if(socialMedia.getIcon().isEmpty() || socialMedia.getIcon() == null) {
				String fileName = socialMedia.getIcon();
				Path path = Paths.get(fileName);
				File f = path.toFile();
				if(f.exists()) {
					f.delete();
				}
			}
			try {
				Date date = new Date();
				SimpleDateFormat dateFortmat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				// String dateName = dateFormat.
				
				String fileName = String.valueOf(idSocialMedia+"-picture."+multipartFile.getContentType().split("/")[1]);
				socialMedia.setIcon(SOCIAL_MEDIA_UPLOADS_FOLDER + fileName);
				
				byte[] bytes = multipartFile.getBytes();
				Path path = Paths.get(SOCIAL_MEDIA_UPLOADS_FOLDER + fileName);
				Files.write(path, bytes);
				_socialMediaService.updateSocialMedia(socialMedia);
				
				 return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
